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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

@SupportedAnnotationTypes({ "ch.rasc.extclassgenerator.Model" })
@SupportedOptions({ "outputFormat", "debug", "includeValidation" })
public class ModelAnnotationProcessor extends AbstractProcessor {

	private static final boolean ALLOW_OTHER_PROCESSORS_TO_CLAIM_ANNOTATIONS = false;

	private static final String OPTION_OUTPUTFORMAT = "outputFormat";

	private static final String OPTION_DEBUG = "debug";

	private static final String OPTION_INCLUDEVALIDATION = "includeValidation";

	private static final String OPTION_CREATEBASEANDSUBCLASS = "createBaseAndSubclass";

	private static final String OPTION_USESINGLEQUOTES = "useSingleQuotes";

	private static final String OPTION_SURROUNDAPIWITHQUOTES = "surroundApiWithQuotes";

	private static final String OPTION_LINEENDING = "lineEnding";

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {
		this.processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
				"Running " + getClass().getSimpleName());

		if (roundEnv.processingOver() || annotations.size() == 0) {
			return ALLOW_OTHER_PROCESSORS_TO_CLAIM_ANNOTATIONS;
		}

		if (roundEnv.getRootElements() == null || roundEnv.getRootElements().isEmpty()) {
			this.processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
					"No sources to process");
			return ALLOW_OTHER_PROCESSORS_TO_CLAIM_ANNOTATIONS;
		}

		OutputConfig outputConfig = new OutputConfig();

		outputConfig.setDebug(
				!"false".equals(this.processingEnv.getOptions().get(OPTION_DEBUG)));
		boolean createBaseAndSubclass = "true".equals(
				this.processingEnv.getOptions().get(OPTION_CREATEBASEANDSUBCLASS));

		String outputFormatString = this.processingEnv.getOptions()
				.get(OPTION_OUTPUTFORMAT);
		outputConfig.setOutputFormat(OutputFormat.EXTJS4);
		if (StringUtils.hasText(outputFormatString)) {
			if (OutputFormat.TOUCH2.name().equalsIgnoreCase(outputFormatString)) {
				outputConfig.setOutputFormat(OutputFormat.TOUCH2);
			}
			else if (OutputFormat.EXTJS5.name().equalsIgnoreCase(outputFormatString)) {
				outputConfig.setOutputFormat(OutputFormat.EXTJS5);
			}
		}

		String includeValidationString = this.processingEnv.getOptions()
				.get(OPTION_INCLUDEVALIDATION);
		outputConfig.setIncludeValidation(IncludeValidation.NONE);
		if (StringUtils.hasText(includeValidationString)) {
			if (IncludeValidation.ALL.name().equalsIgnoreCase(includeValidationString)) {
				outputConfig.setIncludeValidation(IncludeValidation.ALL);
			}
			else if (IncludeValidation.BUILTIN.name()
					.equalsIgnoreCase(includeValidationString)) {
				outputConfig.setIncludeValidation(IncludeValidation.BUILTIN);
			}
		}

		outputConfig.setUseSingleQuotes("true"
				.equals(this.processingEnv.getOptions().get(OPTION_USESINGLEQUOTES)));
		outputConfig.setSurroundApiWithQuotes("true".equals(
				this.processingEnv.getOptions().get(OPTION_SURROUNDAPIWITHQUOTES)));

		outputConfig.setLineEnding(LineEnding.SYSTEM);
		String lineEndingOption = this.processingEnv.getOptions().get(OPTION_LINEENDING);
		if (lineEndingOption != null) {
			try {
				LineEnding lineEnding = LineEnding
						.valueOf(lineEndingOption.toUpperCase());
				outputConfig.setLineEnding(lineEnding);
			}
			catch (Exception e) {
				// ignore an invalid value
			}
		}

		for (TypeElement annotation : annotations) {
			Set<? extends Element> elements = roundEnv
					.getElementsAnnotatedWith(annotation);
			for (Element element : elements) {

				try {
					TypeElement typeElement = (TypeElement) element;

					String qualifiedName = typeElement.getQualifiedName().toString();
					Class<?> modelClass = Class.forName(qualifiedName);

					String code = ModelGenerator.generateJavascript(modelClass,
							outputConfig);

					Model modelAnnotation = element.getAnnotation(Model.class);
					String modelName = modelAnnotation.value();
					String fileName;
					String packageName = "";
					if (StringUtils.hasText(modelName)) {
						int lastDot = modelName.lastIndexOf('.');
						if (lastDot != -1) {
							fileName = modelName.substring(lastDot + 1);
							int firstDot = modelName.indexOf('.');
							if (firstDot < lastDot) {
								packageName = modelName.substring(firstDot + 1, lastDot);
							}
						}
						else {
							fileName = modelName;
						}
					}
					else {
						fileName = typeElement.getSimpleName().toString();
					}

					if (createBaseAndSubclass) {
						code = code.replaceFirst("(Ext.define\\([\"'].+?)([\"'],)",
								"$1Base$2");
						FileObject fo = this.processingEnv.getFiler().createResource(
								StandardLocation.SOURCE_OUTPUT, packageName,
								fileName + "Base.js");
						try (OutputStream os = fo.openOutputStream()) {
							os.write(code.getBytes(ModelGenerator.UTF8_CHARSET));
						}

						try {
							fo = this.processingEnv.getFiler().getResource(
									StandardLocation.SOURCE_OUTPUT, packageName,
									fileName + ".js");
							try (InputStream is = fo.openInputStream()) {
								/* nothing here */}
						}
						catch (FileNotFoundException e) {
							String subClassCode = generateSubclassCode(modelClass,
									outputConfig);
							fo = this.processingEnv.getFiler().createResource(
									StandardLocation.SOURCE_OUTPUT, packageName,
									fileName + ".js");
							try (OutputStream os = fo.openOutputStream()) {
								os.write(subClassCode
										.getBytes(ModelGenerator.UTF8_CHARSET));
							}
						}

					}
					else {
						FileObject fo = this.processingEnv.getFiler().createResource(
								StandardLocation.SOURCE_OUTPUT, packageName,
								fileName + ".js");
						try (OutputStream os = fo.openOutputStream()) {
							os.write(code.getBytes(ModelGenerator.UTF8_CHARSET));
						}
					}

				}
				catch (ClassNotFoundException e) {
					this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
							e.getMessage());
				}
				catch (IOException e) {
					this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
							e.getMessage());
				}

			}
		}

		return ALLOW_OTHER_PROCESSORS_TO_CLAIM_ANNOTATIONS;
	}

	private static String generateSubclassCode(Class<?> clazz,
			OutputConfig outputConfig) {
		Model modelAnnotation = clazz.getAnnotation(Model.class);

		String name;
		if (modelAnnotation != null && StringUtils.hasText(modelAnnotation.value())) {
			name = modelAnnotation.value();
		}
		else {
			name = clazz.getName();
		}

		Map<String, Object> modelObject = new LinkedHashMap<>();
		modelObject.put("extend", name + "Base");

		StringBuilder sb = new StringBuilder(100);
		sb.append("Ext.define(\"").append(name).append("\",");
		if (outputConfig.isDebug()) {
			sb.append("\n");
		}

		String configObjectString;
		try {

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

			ObjectMapper mapper = mapperBuilder.build();
			if (outputConfig.isDebug()) {
				configObjectString = mapper.writerWithDefaultPrettyPrinter()
						.writeValueAsString(modelObject);
			}
			else {
				configObjectString = mapper.writeValueAsString(modelObject);
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

		if (outputConfig.isUseSingleQuotes()) {
			return sb.toString().replace('"', '\'');
		}

		return sb.toString();

	}
}
