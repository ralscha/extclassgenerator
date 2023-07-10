/**
 * Copyright the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.rasc.extclassgenerator;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.ref.SoftReference;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ReflectionUtils.MethodCallback;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import ch.rasc.extclassgenerator.association.AbstractAssociation;
import ch.rasc.extclassgenerator.validation.AbstractValidation;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Generator for creating ExtJS and Touch Model objects (JS code) based on a provided
 * class or {@link ModelBean}.
 */
public abstract class ModelGenerator {

	public static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

	private static final Map<JsCacheKey, SoftReference<String>> jsCache = new ConcurrentHashMap<>();

	private static final Map<ModelCacheKey, SoftReference<ModelBean>> modelCache = new ConcurrentHashMap<>();

	/**
	 * Instrospects the provided class, creates a model object (JS code) and writes it
	 * into the response. Creates compressed JS code. Method ignores any validation
	 * annotations.
	 *
	 * @param request the http servlet request
	 * @param response the http servlet response
	 * @param clazz class that the generator should introspect
	 * @param format specifies which code (ExtJS or Touch) the generator should create.
	 * @throws IOException
	 *
	 * @see #writeModel(HttpServletRequest, HttpServletResponse, Class, OutputFormat,
	 * boolean)
	 */
	public static void writeModel(HttpServletRequest request,
			HttpServletResponse response, Class<?> clazz, OutputFormat format)
			throws IOException {
		writeModel(request, response, clazz, format, IncludeValidation.NONE, false);
	}

	/**
	 * Instrospects the provided class, creates a model object (JS code) and writes it
	 * into the response. Method ignores any validation annotations.
	 *
	 * @param request the http servlet request
	 * @param response the http servlet response
	 * @param clazz class that the generator should introspect
	 * @param format specifies which code (ExtJS or Touch) the generator should create
	 * @param debug if true the generator creates the output in pretty format, false the
	 * output is compressed
	 * @throws IOException
	 */
	public static void writeModel(HttpServletRequest request,
			HttpServletResponse response, Class<?> clazz, OutputFormat format,
			boolean debug) throws IOException {
		writeModel(request, response, clazz, format, IncludeValidation.NONE, debug);
	}

	/**
	 * Instrospects the provided class, creates a model object (JS code) and writes it
	 * into the response.
	 *
	 * @param request the http servlet request
	 * @param response the http servlet response
	 * @param clazz class that the generator should introspect
	 * @param format specifies which code (ExtJS or Touch) the generator should create
	 * @param includeValidation specifies if any validation configurations should be added
	 * to the model code
	 * @param debug if true the generator creates the output in pretty format, false the
	 * output is compressed
	 * @throws IOException
	 */
	public static void writeModel(HttpServletRequest request,
			HttpServletResponse response, Class<?> clazz, OutputFormat format,
			IncludeValidation includeValidation, boolean debug) throws IOException {
		OutputConfig outputConfig = new OutputConfig();
		outputConfig.setIncludeValidation(includeValidation);
		outputConfig.setOutputFormat(format);
		outputConfig.setDebug(debug);
		ModelBean model = createModel(clazz, outputConfig);
		writeModel(request, response, model, outputConfig);
	}

	public static void writeModel(HttpServletRequest request,
			HttpServletResponse response, Class<?> clazz, OutputConfig outputConfig)
			throws IOException {
		ModelBean model = createModel(clazz, outputConfig);
		writeModel(request, response, model, outputConfig);
	}

	/**
	 * Creates a model object (JS code) based on the provided {@link ModelBean} and writes
	 * it into the response. Creates compressed JS code.
	 *
	 * @param request the http servlet request
	 * @param response the http servlet response
	 * @param model {@link ModelBean} describing the model to be generated
	 * @param format specifies which code (ExtJS or Touch) the generator should create.
	 * @throws IOException
	 */
	public static void writeModel(HttpServletRequest request,
			HttpServletResponse response, ModelBean model, OutputFormat format)
			throws IOException {
		writeModel(request, response, model, format, false);
	}

	/**
	 * Creates a model object (JS code) based on the provided ModelBean and writes it into
	 * the response.
	 *
	 * @param request the http servlet request
	 * @param response the http servlet response
	 * @param model {@link ModelBean} describing the model to be generated
	 * @param format specifies which code (ExtJS or Touch) the generator should create.
	 * @param debug if true the generator creates the output in pretty format, false the
	 * output is compressed
	 * @throws IOException
	 */
	public static void writeModel(HttpServletRequest request,
			HttpServletResponse response, ModelBean model, OutputFormat format,
			boolean debug) throws IOException {
		OutputConfig outputConfig = new OutputConfig();
		outputConfig.setDebug(debug);
		outputConfig.setOutputFormat(format);
		writeModel(request, response, model, outputConfig);
	}

	/**
	 * Instrospects the provided class and creates a {@link ModelBean} instance. A program
	 * could customize this and call
	 * {@link #generateJavascript(ModelBean, OutputFormat, boolean)} or
	 * {@link #writeModel(HttpServletRequest, HttpServletResponse, ModelBean, OutputFormat)}
	 * to create the JS code. Calling this method does not add any validation
	 * configuration.
	 *
	 * @param clazz the model will be created based on this class.
	 * @return a instance of {@link ModelBean} that describes the provided class and can
	 * be used for Javascript generation.
	 */
	public static ModelBean createModel(Class<?> clazz) {
		return createModel(clazz, IncludeValidation.NONE);
	}

	/**
	 * Instrospects the provided class and creates a {@link ModelBean} instance. A program
	 * could customize this and call
	 * {@link #generateJavascript(ModelBean, OutputFormat, boolean)} or
	 * {@link #writeModel(HttpServletRequest, HttpServletResponse, ModelBean, OutputFormat)}
	 * to create the JS code. Models are being cached. A second call with the same
	 * parameters will return the model from the cache.
	 *
	 * @param clazz the model will be created based on this class.
	 * @param includeValidation specifies what validation configuration should be added
	 * @return a instance of {@link ModelBean} that describes the provided class and can
	 * be used for Javascript generation.
	 */
	public static ModelBean createModel(Class<?> clazz,
			IncludeValidation includeValidation) {
		OutputConfig outputConfig = new OutputConfig();
		outputConfig.setIncludeValidation(includeValidation);
		return createModel(clazz, outputConfig);
	}

	/**
	 * Instrospects the provided class, creates a model object (JS code) and returns it.
	 * This method does not add any validation configuration.
	 *
	 * @param clazz class that the generator should introspect
	 * @param format specifies which code (ExtJS or Touch) the generator should create
	 * @param debug if true the generator creates the output in pretty format, false the
	 * output is compressed
	 * @return the generated model object (JS code)
	 */
	public static String generateJavascript(Class<?> clazz, OutputFormat format,
			boolean debug) {
		OutputConfig outputConfig = new OutputConfig();
		outputConfig.setIncludeValidation(IncludeValidation.NONE);
		outputConfig.setOutputFormat(format);
		outputConfig.setDebug(debug);

		ModelBean model = createModel(clazz, outputConfig);
		return generateJavascript(model, outputConfig);
	}

	public static String generateJavascript(Class<?> clazz, OutputConfig outputConfig) {
		ModelBean model = createModel(clazz, outputConfig);
		return generateJavascript(model, outputConfig);
	}

	/**
	 * Instrospects the provided class, creates a model object (JS code) and returns it.
	 *
	 * @param clazz class that the generator should introspect
	 * @param format specifies which code (ExtJS or Touch) the generator should create
	 * @param includeValidation specifies what validation configuration should be added to
	 * the mode code
	 * @param debug if true the generator creates the output in pretty format, false the
	 * output is compressed
	 * @return the generated model object (JS code)
	 */
	public static String generateJavascript(Class<?> clazz, OutputFormat format,
			IncludeValidation includeValidation, boolean debug) {
		OutputConfig outputConfig = new OutputConfig();
		outputConfig.setIncludeValidation(includeValidation);
		outputConfig.setOutputFormat(format);
		outputConfig.setDebug(debug);

		ModelBean model = createModel(clazz, outputConfig);
		return generateJavascript(model, outputConfig);
	}

	/**
	 * Creates JS code based on the provided {@link ModelBean} in the specified
	 * {@link OutputFormat}. Code can be generated in pretty or compressed format. The
	 * generated code is cached unless debug is true. A second call to this method with
	 * the same model name and format will return the code from the cache.
	 *
	 * @param model generate code based on this {@link ModelBean}
	 * @param format specifies which code (ExtJS or Touch) the generator should create
	 * @param debug if true the generator creates the output in pretty format, false the
	 * output is compressed
	 * @return the generated model object (JS code)
	 */
	public static String generateJavascript(ModelBean model, OutputFormat format,
			boolean debug) {
		OutputConfig outputConfig = new OutputConfig();
		outputConfig.setOutputFormat(format);
		outputConfig.setDebug(debug);
		return generateJavascript(model, outputConfig);
	}

	public static void writeModel(HttpServletRequest request,
			HttpServletResponse response, ModelBean model, OutputConfig outputConfig)
			throws IOException {

		byte[] data = generateJavascript(model, outputConfig).getBytes(UTF8_CHARSET);
		String ifNoneMatch = request.getHeader("If-None-Match");
		String etag = "\"0" + DigestUtils.md5DigestAsHex(data) + "\"";

		response.setHeader("ETag", etag);

		if (etag.equals(ifNoneMatch)) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return;
		}

		response.setContentType("application/javascript");
		response.setCharacterEncoding(UTF8_CHARSET.name());
		response.setContentLength(data.length);

		@SuppressWarnings("resource")
		ServletOutputStream out = response.getOutputStream();
		out.write(data);
		out.flush();
	}

	public static ModelBean createModel(final Class<?> clazz,
			final OutputConfig outputConfig) {

		Assert.notNull(clazz, "clazz must not be null");
		Assert.notNull(outputConfig.getIncludeValidation(),
				"includeValidation must not be null");

		ModelCacheKey key = new ModelCacheKey(clazz.getName(), outputConfig);
		SoftReference<ModelBean> modelReference = modelCache.get(key);
		if (modelReference != null && modelReference.get() != null) {
			return modelReference.get();
		}

		Model modelAnnotation = clazz.getAnnotation(Model.class);

		final ModelBean model = new ModelBean();

		if (modelAnnotation != null && StringUtils.hasText(modelAnnotation.value())) {
			model.setName(modelAnnotation.value());
		}
		else {
			model.setName(clazz.getName());
		}

		if (modelAnnotation != null) {
			model.setAutodetectTypes(modelAnnotation.autodetectTypes());

			model.setExtend(modelAnnotation.extend());
			model.setIdProperty(modelAnnotation.idProperty());
			model.setVersionProperty(trimToNull(modelAnnotation.versionProperty()));
			model.setPaging(modelAnnotation.paging());
			model.setDisablePagingParameters(modelAnnotation.disablePagingParameters());
			model.setCreateMethod(trimToNull(modelAnnotation.createMethod()));
			model.setReadMethod(trimToNull(modelAnnotation.readMethod()));
			model.setUpdateMethod(trimToNull(modelAnnotation.updateMethod()));
			model.setDestroyMethod(trimToNull(modelAnnotation.destroyMethod()));
			model.setMessageProperty(trimToNull(modelAnnotation.messageProperty()));
			model.setWriter(trimToNull(modelAnnotation.writer()));
			model.setReader(trimToNull(modelAnnotation.reader()));
			model.setSuccessProperty(trimToNull(modelAnnotation.successProperty()));
			model.setTotalProperty(trimToNull(modelAnnotation.totalProperty()));
			model.setRootProperty(trimToNull(modelAnnotation.rootProperty()));
			model.setWriteAllFields(modelAnnotation.writeAllFields());
			model.setAllDataOptions(
					new AllDataOptionsBean(modelAnnotation.allDataOptions()));
			model.setPartialDataOptions(
					new PartialDataOptionsBean(modelAnnotation.partialDataOptions()));
			model.setIdentifier(trimToNull(modelAnnotation.identifier()));
			String clientIdProperty = trimToNull(modelAnnotation.clientIdProperty());
			if (StringUtils.hasText(clientIdProperty)) {
				model.setClientIdProperty(clientIdProperty);
				model.setClientIdPropertyAddToWriter(true);
			}
			else {
				model.setClientIdProperty(null);
				model.setClientIdPropertyAddToWriter(false);
			}

			if (modelAnnotation.hasMany() != null && modelAnnotation.hasMany().length > 0
					&& StringUtils.hasText(modelAnnotation.hasMany()[0])) {
				model.setHasMany(modelAnnotation.hasMany());
			}
		}

		final Set<String> readMethods = new HashSet<>();

		BeanInfo bi;
		try {
			bi = Introspector.getBeanInfo(clazz);
		}
		catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}

		for (PropertyDescriptor pd : bi.getPropertyDescriptors()) {
			if (pd.getReadMethod() != null
					&& pd.getReadMethod().getAnnotation(JsonIgnore.class) == null) {
				readMethods.add(pd.getName());
			}
		}

		if (clazz.isInterface()) {
			final List<Method> methods = new ArrayList<>();

			ReflectionUtils.doWithMethods(clazz, new MethodCallback() {
				@Override
				public void doWith(Method method)
						throws IllegalArgumentException, IllegalAccessException {
					methods.add(method);
				}
			});

			Collections.sort(methods, new Comparator<Method>() {
				@Override
				public int compare(Method o1, Method o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});

			for (Method method : methods) {
				createModelBean(model, method, outputConfig);
			}
		}
		else {

			final Set<String> fields = new HashSet<>();

			Set<ModelField> modelFieldsOnType = AnnotationUtils
					.getRepeatableAnnotations(clazz, ModelField.class, null);
			for (ModelField modelField : modelFieldsOnType) {
				if (StringUtils.hasText(modelField.value())) {
					ModelFieldBean modelFieldBean;

					if (StringUtils.hasText(modelField.customType())) {
						modelFieldBean = new ModelFieldBean(modelField.value(),
								modelField.customType());
					}
					else {
						modelFieldBean = new ModelFieldBean(modelField.value(),
								modelField.type());
					}

					updateModelFieldBean(modelFieldBean, modelField);
					model.addField(modelFieldBean);
				}
			}

			Set<ModelAssociation> modelAssociationsOnType = AnnotationUtils
					.getRepeatableAnnotations(clazz, ModelAssociation.class, null);
			for (ModelAssociation modelAssociationAnnotation : modelAssociationsOnType) {
				AbstractAssociation modelAssociation = AbstractAssociation
						.createAssociation(modelAssociationAnnotation);
				if (modelAssociation != null) {
					model.addAssociation(modelAssociation);
				}
			}

			Set<ModelValidation> modelValidationsOnType = AnnotationUtils
					.getRepeatableAnnotations(clazz, ModelValidation.class, null);
			for (ModelValidation modelValidationAnnotation : modelValidationsOnType) {
				AbstractValidation modelValidation = AbstractValidation.createValidation(
						modelValidationAnnotation.propertyName(),
						modelValidationAnnotation, outputConfig.getIncludeValidation());
				if (modelValidation != null) {
					model.addValidation(modelValidation);
				}
			}

			ReflectionUtils.doWithFields(clazz, new FieldCallback() {

				@Override
				public void doWith(Field field)
						throws IllegalArgumentException, IllegalAccessException {
					if (!fields.contains(field.getName()) && (field
							.getAnnotation(ModelField.class) != null
							|| field.getAnnotation(ModelAssociation.class) != null
							|| (Modifier.isPublic(field.getModifiers())
									|| readMethods.contains(field.getName()))
									&& field.getAnnotation(JsonIgnore.class) == null)) {

						// ignore superclass declarations of fields already
						// found in a subclass
						fields.add(field.getName());
						createModelBean(model, field, outputConfig);

					}
				}

			});

			final List<Method> candidateMethods = new ArrayList<>();
			ReflectionUtils.doWithMethods(clazz, new MethodCallback() {
				@Override
				public void doWith(Method method)
						throws IllegalArgumentException, IllegalAccessException {

					if ((method.getAnnotation(ModelField.class) != null
							|| method.getAnnotation(ModelAssociation.class) != null)
							&& method.getAnnotation(JsonIgnore.class) == null) {
						candidateMethods.add(method);
					}
				}
			});

			Collections.sort(candidateMethods, new Comparator<Method>() {
				@Override
				public int compare(Method o1, Method o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});

			for (Method method : candidateMethods) {
				createModelBean(model, method, outputConfig);
			}

		}

		modelCache.put(key, new SoftReference<>(model));
		return model;
	}

	private static void createModelBean(ModelBean model,
			AccessibleObject accessibleObject, OutputConfig outputConfig) {
		Class<?> javaType = null;
		String name = null;
		Class<?> declaringClass = null;

		if (accessibleObject instanceof Field) {
			Field field = (Field) accessibleObject;
			javaType = field.getType();
			name = field.getName();
			declaringClass = field.getDeclaringClass();
		}
		else if (accessibleObject instanceof Method) {
			Method method = (Method) accessibleObject;

			javaType = method.getReturnType();
			if (javaType.equals(Void.TYPE)) {
				return;
			}

			if (method.getName().startsWith("get")) {
				name = StringUtils.uncapitalize(method.getName().substring(3));
			}
			else if (method.getName().startsWith("is")) {
				name = StringUtils.uncapitalize(method.getName().substring(2));
			}
			else {
				name = method.getName();
			}

			declaringClass = method.getDeclaringClass();
		}

		ModelType modelType = null;
		if (model.isAutodetectTypes()) {
			for (ModelType mt : ModelType.values()) {
				if (mt.supports(javaType)) {
					modelType = mt;
					break;
				}
			}
		}
		else {
			modelType = ModelType.AUTO;
		}

		ModelFieldBean modelFieldBean = null;

		ModelField modelFieldAnnotation = accessibleObject
				.getAnnotation(ModelField.class);
		if (modelFieldAnnotation != null) {

			if (StringUtils.hasText(modelFieldAnnotation.value())) {
				name = modelFieldAnnotation.value();
			}

			if (StringUtils.hasText(modelFieldAnnotation.customType())) {
				modelFieldBean = new ModelFieldBean(name,
						modelFieldAnnotation.customType());
			}
			else {
				ModelType type = null;
				if (modelFieldAnnotation.type() != ModelType.NOT_SPECIFIED) {
					type = modelFieldAnnotation.type();
				}
				else {
					type = modelType;
				}

				modelFieldBean = new ModelFieldBean(name, type);
			}

			updateModelFieldBean(modelFieldBean, modelFieldAnnotation);
			model.addField(modelFieldBean);
		}
		else {
			if (modelType != null) {
				modelFieldBean = new ModelFieldBean(name, modelType);
				model.addField(modelFieldBean);
			}
		}

		ModelId modelIdAnnotation = accessibleObject.getAnnotation(ModelId.class);
		if (modelIdAnnotation != null) {
			model.setIdProperty(name);
		}

		ModelClientId modelClientId = accessibleObject.getAnnotation(ModelClientId.class);
		if (modelClientId != null) {
			model.setClientIdProperty(name);
			model.setClientIdPropertyAddToWriter(modelClientId.configureWriter());
		}

		ModelVersion modelVersion = accessibleObject.getAnnotation(ModelVersion.class);
		if (modelVersion != null) {
			model.setVersionProperty(name);
		}

		ModelAssociation modelAssociationAnnotation = accessibleObject
				.getAnnotation(ModelAssociation.class);
		if (modelAssociationAnnotation != null) {
			model.addAssociation(AbstractAssociation.createAssociation(
					modelAssociationAnnotation, model, javaType, declaringClass, name));
		}

		if (modelFieldBean != null
				&& outputConfig.getIncludeValidation() != IncludeValidation.NONE) {

			Set<ModelValidation> modelValidationAnnotations = AnnotationUtils
					.getRepeatableAnnotations(accessibleObject, ModelValidation.class,
							null);
			if (!modelValidationAnnotations.isEmpty()) {
				for (ModelValidation modelValidationAnnotation : modelValidationAnnotations) {
					AbstractValidation modelValidation = AbstractValidation
							.createValidation(name, modelValidationAnnotation,
									outputConfig.getIncludeValidation());
					if (modelValidation != null) {
						model.addValidation(modelValidation);
					}
				}
			}
			else {
				Annotation[] fieldAnnotations = accessibleObject.getAnnotations();

				for (Annotation fieldAnnotation : fieldAnnotations) {
					AbstractValidation.addValidationToModel(model, modelFieldBean,
							fieldAnnotation, outputConfig);
				}

				if (accessibleObject instanceof Field) {
					PropertyDescriptor pd = BeanUtils
							.getPropertyDescriptor(declaringClass, name);
					if (pd != null) {
						if (pd.getReadMethod() != null) {
							for (Annotation readMethodAnnotation : pd.getReadMethod()
									.getAnnotations()) {
								AbstractValidation.addValidationToModel(model,
										modelFieldBean, readMethodAnnotation,
										outputConfig);
							}
						}

						if (pd.getWriteMethod() != null) {
							for (Annotation writeMethodAnnotation : pd.getWriteMethod()
									.getAnnotations()) {
								AbstractValidation.addValidationToModel(model,
										modelFieldBean, writeMethodAnnotation,
										outputConfig);
							}
						}
					}
				}
			}
		}

	}

	private static void updateModelFieldBean(ModelFieldBean modelFieldBean,
			ModelField modelFieldAnnotation) {

		ModelType type = modelFieldBean.getModelType();

		if (StringUtils.hasText(modelFieldAnnotation.dateFormat())
				&& type == ModelType.DATE) {
			modelFieldBean.setDateFormat(modelFieldAnnotation.dateFormat());
		}

		String defaultValue = modelFieldAnnotation.defaultValue();
		if (StringUtils.hasText(defaultValue)) {
			if (ModelField.DEFAULTVALUE_UNDEFINED.equals(defaultValue)) {
				modelFieldBean.setDefaultValue(ModelField.DEFAULTVALUE_UNDEFINED);
			}
			else {
				if (type == ModelType.BOOLEAN) {
					modelFieldBean.setDefaultValue(Boolean.valueOf(defaultValue));
				}
				else if (type == ModelType.INTEGER) {
					modelFieldBean.setDefaultValue(Long.valueOf(defaultValue));
				}
				else if (type == ModelType.FLOAT || type == ModelType.NUMBER) {
					modelFieldBean.setDefaultValue(Double.valueOf(defaultValue));
				}
				else {
					modelFieldBean.setDefaultValue("\"" + defaultValue + "\"");
				}
			}
		}

		if ((modelFieldAnnotation.useNull() || modelFieldAnnotation.allowNull())
				&& (type == ModelType.INTEGER || type == ModelType.FLOAT
						|| type == ModelType.NUMBER || type == ModelType.STRING
						|| type == ModelType.BOOLEAN)) {
			modelFieldBean.setAllowNull(Boolean.TRUE);
		}

		if (!modelFieldAnnotation.allowBlank()) {
			modelFieldBean.setAllowBlank(Boolean.FALSE);
		}

		if (modelFieldAnnotation.unique()) {
			modelFieldBean.setUnique(Boolean.TRUE);
		}

		modelFieldBean.setMapping(trimToNull(modelFieldAnnotation.mapping()));

		if (!modelFieldAnnotation.persist()) {
			modelFieldBean.setPersist(Boolean.FALSE);
		}

		if (modelFieldAnnotation.critical()) {
			modelFieldBean.setCritical(Boolean.TRUE);
		}

		modelFieldBean.setConvert(trimToNull(modelFieldAnnotation.convert()));

		modelFieldBean.setCalculate(trimToNull(modelFieldAnnotation.calculate()));

		List<String> depends = Arrays.asList(modelFieldAnnotation.depends());
		if (!depends.isEmpty()) {
			modelFieldBean.setDepends(depends);
		}
		else {
			modelFieldBean.setDepends(null);
		}

		ReferenceBean reference = new ReferenceBean(modelFieldAnnotation.reference());
		if (reference.hasAnyProperties()) {
			if (reference.typeOnly()) {
				modelFieldBean.setReference(reference.getType());
			}
			else {
				modelFieldBean.setReference(reference);
			}
		}
	}

	public static String generateJavascript(ModelBean model, OutputConfig outputConfig) {

		if (!outputConfig.isDebug()) {
			JsCacheKey key = new JsCacheKey(model, outputConfig);

			SoftReference<String> jsReference = jsCache.get(key);
			if (jsReference != null && jsReference.get() != null) {
				return jsReference.get();
			}
		}

		JsonMapper.Builder mapperBuilder = JsonMapper.builder()
				.configure(JsonWriteFeature.QUOTE_FIELD_NAMES, false);

		if (!outputConfig.isSurroundApiWithQuotes()) {
			if (outputConfig.getOutputFormat() == OutputFormat.EXTJS5) {
				mapperBuilder.addMixIn(ProxyObject.class,
						ProxyObjectWithoutApiQuotesExtJs5Mixin.class);
			}
			else {
				mapperBuilder.addMixIn(ProxyObject.class,
						ProxyObjectWithoutApiQuotesMixin.class);
			}
			mapperBuilder.addMixIn(ApiObject.class, ApiObjectMixin.class);
		}
		else {
			if (outputConfig.getOutputFormat() != OutputFormat.EXTJS5) {
				mapperBuilder.addMixIn(ProxyObject.class,
						ProxyObjectWithApiQuotesMixin.class);
			}
		}

		Map<String, Object> modelObject = new LinkedHashMap<>();
		modelObject.put("extend", model.getExtend());

		if (!model.getAssociations().isEmpty()) {
			Set<String> usesClasses = new HashSet<>();
			for (AbstractAssociation association : model.getAssociations()) {
				usesClasses.add(association.getModel());
			}

			usesClasses.remove(model.getName());

			if (!usesClasses.isEmpty()) {
				modelObject.put("uses", usesClasses);
			}
		}

		Map<String, Object> configObject = new LinkedHashMap<>();
		ProxyObject proxyObject = new ProxyObject(model, outputConfig);

		Map<String, ModelFieldBean> fields = model.getFields();
		Set<String> requires = new HashSet<>();

		if (!model.getValidations().isEmpty()
				&& outputConfig.getOutputFormat() == OutputFormat.EXTJS5) {
			requires = addValidatorsToField(fields, model.getValidations());
		}

		if (proxyObject.hasContent()
				&& outputConfig.getOutputFormat() == OutputFormat.EXTJS5) {
			requires.add("Ext.data.proxy.Direct");
		}

		if (StringUtils.hasText(model.getIdentifier())
				&& outputConfig.getOutputFormat() == OutputFormat.EXTJS5) {
			if ("sequential".equals(model.getIdentifier())) {
				requires.add("Ext.data.identifier.Sequential");
			}
			else if ("uuid".equals(model.getIdentifier())) {
				requires.add("Ext.data.identifier.Uuid");
			}
			else if ("negative".equals(model.getIdentifier())) {
				requires.add("Ext.data.identifier.Negative");
			}
		}

		if (requires != null && !requires.isEmpty()) {
			configObject.put("requires", requires);
		}

		if (StringUtils.hasText(model.getIdentifier())) {
			if (outputConfig.getOutputFormat() == OutputFormat.EXTJS5
					|| outputConfig.getOutputFormat() == OutputFormat.TOUCH2) {
				configObject.put("identifier", model.getIdentifier());
			}
			else {
				configObject.put("idgen", model.getIdentifier());
			}
		}

		if (StringUtils.hasText(model.getIdProperty())
				&& !model.getIdProperty().equals("id")) {
			configObject.put("idProperty", model.getIdProperty());
		}

		if (outputConfig.getOutputFormat() == OutputFormat.EXTJS5
				&& StringUtils.hasText(model.getVersionProperty())) {
			configObject.put("versionProperty", model.getVersionProperty());
		}

		if (StringUtils.hasText(model.getClientIdProperty())) {

			if (outputConfig.getOutputFormat() == OutputFormat.EXTJS5
					|| outputConfig.getOutputFormat() == OutputFormat.EXTJS4) {
				configObject.put("clientIdProperty", model.getClientIdProperty());
			}
			else if (outputConfig.getOutputFormat() == OutputFormat.TOUCH2
					&& !"clientId".equals(model.getClientIdProperty())) {
				configObject.put("clientIdProperty", model.getClientIdProperty());
			}
		}

		for (ModelFieldBean field : fields.values()) {
			field.updateTypes(outputConfig);
		}

		List<Object> fieldConfigObjects = new ArrayList<>();
		for (ModelFieldBean field : fields.values()) {
			if (field.hasOnlyName(outputConfig)) {
				fieldConfigObjects.add(field.getName());
			}
			else {
				fieldConfigObjects.add(field);
			}
		}
		configObject.put("fields", fieldConfigObjects);

		if (model.getHasMany() != null) {
			configObject.put("hasMany", model.getHasMany());
		}

		if (!model.getAssociations().isEmpty()) {
			configObject.put("associations", model.getAssociations());
		}

		if (!model.getValidations().isEmpty()
				&& !(outputConfig.getOutputFormat() == OutputFormat.EXTJS5)) {
			configObject.put("validations", model.getValidations());
		}

		if (proxyObject.hasContent()) {
			configObject.put("proxy", proxyObject);
		}

		if (outputConfig.getOutputFormat() == OutputFormat.EXTJS4
				|| outputConfig.getOutputFormat() == OutputFormat.EXTJS5) {
			modelObject.putAll(configObject);
		}
		else {
			modelObject.put("config", configObject);
		}

		StringBuilder sb = new StringBuilder();
		sb.append("Ext.define(\"").append(model.getName()).append("\",");
		if (outputConfig.isDebug()) {
			sb.append("\r\n");
		}

		String configObjectString;
		Class<?> jsonView = JsonViews.ExtJS4.class;
		if (outputConfig.getOutputFormat() == OutputFormat.TOUCH2) {
			jsonView = JsonViews.Touch2.class;
		}
		else if (outputConfig.getOutputFormat() == OutputFormat.EXTJS5) {
			jsonView = JsonViews.ExtJS5.class;
		}

		try {
			ObjectMapper mapper = mapperBuilder.build();
			if (outputConfig.isDebug()) {
				configObjectString = mapper.writerWithDefaultPrettyPrinter()
						.withView(jsonView).writeValueAsString(modelObject);
			}
			else {
				configObjectString = mapper.writerWithView(jsonView)
						.writeValueAsString(modelObject);
			}

		}
		catch (JsonGenerationException e) {
			throw new RuntimeException(e);
		}
		catch (JsonMappingException e) {
			throw new RuntimeException(e);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}

		sb.append(configObjectString);
		sb.append(");");

		String result = sb.toString();

		if (outputConfig.isUseSingleQuotes()) {
			result = result.replace('"', '\'');
		}

		if (outputConfig.getLineEnding() == LineEnding.CRLF) {
			result = result.replaceAll("\r?\n", "\r\n");
		}
		else if (outputConfig.getLineEnding() == LineEnding.LF) {
			result = result.replaceAll("\r?\n", "\n");
		}
		else if (outputConfig.getLineEnding() == LineEnding.SYSTEM) {
			String lineSeparator = System.getProperty("line.separator");
			result = result.replaceAll("\r?\n", lineSeparator);
		}

		if (!outputConfig.isDebug()) {
			jsCache.put(new JsCacheKey(model, outputConfig), new SoftReference<>(result));
		}
		return result;
	}

	private static Set<String> addValidatorsToField(Map<String, ModelFieldBean> fields,
			List<AbstractValidation> validations) {

		Set<String> requires = new TreeSet<>();

		for (ModelFieldBean field : fields.values()) {
			for (AbstractValidation validation : validations) {
				if (field.getName().equals(validation.getField())) {
					List<AbstractValidation> validators = field.getValidators();
					if (validators == null) {
						validators = new ArrayList<>();
						field.setValidators(validators);
					}

					String validatorClass = getValidatorClass(validation.getType());
					if (validatorClass != null) {
						requires.add(validatorClass);
					}

					boolean alreadyExists = false;
					for (AbstractValidation validator : validators) {
						if (validation.getType().equals(validator.getType())) {
							alreadyExists = true;
							break;
						}
					}

					if (!alreadyExists) {
						validators.add(validation);
					}
				}
			}
		}

		return requires;
	}

	private static String getValidatorClass(String type) {
		if (type.equals("email")) {
			return "Ext.data.validator.Email";
		}
		else if (type.equals("exclusion")) {
			return "Ext.data.validator.Exclusion";
		}
		else if (type.equals("format")) {
			return "Ext.data.validator.Format";
		}
		else if (type.equals("inclusion")) {
			return "Ext.data.validator.Inclusion";
		}
		else if (type.equals("length")) {
			return "Ext.data.validator.Length";
		}
		else if (type.equals("presence")) {
			return "Ext.data.validator.Presence";
		}
		else if (type.equals("range")) {
			return "Ext.data.validator.Range";
		}
		return null;
	}

	static String trimToNull(String str) {
		if (str != null) {
			String trimmedStr = str.strip();
			if (StringUtils.hasLength(trimmedStr)) {
				return trimmedStr;
			}
		}
		return null;
	}

	/**
	 * Clears the model and Javascript code caches
	 */
	public static void clearCaches() {
		modelCache.clear();
		jsCache.clear();
	}

}