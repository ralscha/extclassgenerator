/*
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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import ch.rasc.extclassgenerator.association.AbstractAssociation;
import ch.rasc.extclassgenerator.validation.AbstractValidation;

/**
 * Represents a model. This object can be used to create JS code with
 * {@link ModelGenerator#writeModel(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, ModelBean, OutputFormat, boolean)}
 * or {@link ModelGenerator#generateJavascript(ModelBean, OutputFormat, boolean)}.
 */
public class ModelBean {

	private boolean autodetectTypes = true;

	private String name;

	private String extend = "Ext.data.Model";

	private String idProperty;

	private String versionProperty;

	private String clientIdProperty;

	private boolean clientIdPropertyAddToWriter;

	private Map<String, ModelFieldBean> fields = new LinkedHashMap<>();

	private List<AbstractValidation> validations = new ArrayList<>();

	private List<AbstractAssociation> associations = new ArrayList<>();

	private String[] hasMany;

	private boolean paging;

	private boolean disablePagingParameters;

	private String readMethod;

	private String createMethod;

	private String updateMethod;

	private String destroyMethod;

	private String messageProperty;

	private String successProperty;

	private String totalProperty;

	private String rootProperty;

	private String writer = "json";

	private String reader = "json";

	private Boolean writeAllFields;

	private String identifier;

	private AllDataOptionsBean allDataOptions;

	private PartialDataOptionsBean partialDataOptions;

	public boolean isAutodetectTypes() {
		return this.autodetectTypes;
	}

	public void setAutodetectTypes(boolean autodetectTypes) {
		this.autodetectTypes = autodetectTypes;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * "Classname" of the model. See
	 * <a href="http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.Model" >Ext.data.Model
	 * </a>.
	 *
	 * @param name new name for the model object
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getExtend() {
		return this.extend;
	}

	/**
	 * "Superclass" of this model.
	 * <p>
	 * Defaults to "Ext.data.Model"
	 */
	public void setExtend(String extend) {
		this.extend = extend;
	}

	public String getIdProperty() {
		return this.idProperty;
	}

	/**
	 * Name of the id property. See
	 * <a href= "http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.Model-cfg-idProperty" >
	 * Ext.data.Model#idProperty</a>.
	 *
	 * @param idProperty new value for the idProperty config option
	 */
	public void setIdProperty(String idProperty) {
		this.idProperty = idProperty;
	}

	public String getVersionProperty() {
		return this.versionProperty;
	}

	/**
	 * If specified, this is the name of the property that contains the entity "version".
	 * The version property is used to manage a long-running transaction and allows the
	 * detection of simultaneous modification.
	 *
	 * See <a href=
	 * "http://docs.sencha.com/extjs/5.0/apidocs/#!/api/Ext.data.Model-cfg-versionProperty"
	 * >Ext.data.Model#versionProperty</a>
	 *
	 * <p>
	 * Defaults to null
	 */
	public void setVersionProperty(String versionProperty) {
		this.versionProperty = versionProperty;
	}

	public Map<String, ModelFieldBean> getFields() {
		return this.fields;
	}

	/**
	 * Overwrites all field definitions with the provided map.
	 *
	 * @param fields new collection of {@link ModelFieldBean}
	 */
	public void setFields(Map<String, ModelFieldBean> fields) {
		this.fields = fields;
	}

	public List<AbstractValidation> getValidations() {
		return this.validations;
	}

	public void setValidations(List<AbstractValidation> validations) {
		this.validations = validations;
	}

	public List<AbstractAssociation> getAssociations() {
		return this.associations;
	}

	public void setAssociations(List<AbstractAssociation> associations) {
		this.associations = associations;
	}

	/**
	 * Add all provided fields to the collection of fields
	 *
	 * @param modelFields collection of {@link ModelFieldBean}
	 */
	public void addFields(List<ModelFieldBean> modelFields) {
		Assert.notNull(modelFields, "modelFields must not be null");

		for (ModelFieldBean bean : modelFields) {
			this.fields.put(bean.getName(), bean);
		}
	}

	public void addValidations(List<AbstractValidation> fieldValidations) {
		Assert.notNull(fieldValidations, "fieldValidations must not be null");

		this.validations.addAll(fieldValidations);
	}

	public void addAssociations(List<AbstractAssociation> associationsList) {
		Assert.notNull(associationsList, "associations must not be null");

		this.associations.addAll(associationsList);
	}

	/**
	 * Looks for the {@link ModelFieldBean} with the provided name and returns it.
	 *
	 * @param fieldName name of a field
	 * @return a {@link ModelFieldBean} or null if not found
	 */
	public ModelFieldBean getField(String fieldName) {
		return this.fields.get(fieldName);
	}

	/**
	 * Adds one instance of {@link ModelFieldBean} to the internal collection of fields
	 *
	 * @param bean instance of {@link ModelFieldBean}
	 */
	public void addField(ModelFieldBean bean) {
		Assert.notNull(bean, "ModelFieldBean must not be null");

		this.fields.put(bean.getName(), bean);
	}

	/**
	 * Adds one instance of one of the subclasses of {@link AbstractValidation} to the
	 * internal collection of validations
	 *
	 * @param bean instance of subclass of {@link AbstractValidation}
	 */
	public void addValidation(AbstractValidation bean) {
		Assert.notNull(bean, "ModelFieldValidationBean must not be null");

		this.validations.add(bean);
	}

	/**
	 * Adds one instance of {@link AbstractAssociation} to the internal collection of
	 * associations
	 *
	 * @param bean instance of {@link AbstractAssociation}
	 */
	public void addAssociation(AbstractAssociation bean) {
		Assert.notNull(bean, "AbstractAssociation must not be null");

		this.associations.add(bean);
	}

	public boolean isPaging() {
		return this.paging;
	}

	/**
	 * If true a reader config with root : 'records' will be added to the model object.
	 * This configuration is needed it the STORE_READ method return an instance of
	 * {@link ExtDirectStoreResult}
	 *
	 * <pre>
	 * reader : {
	 *   root : 'records'
	 * }
	 * </pre>
	 *
	 * @param paging new value for paging
	 */
	public void setPaging(boolean paging) {
		this.paging = paging;
	}

	public boolean isDisablePagingParameters() {
		return this.disablePagingParameters;
	}

	/**
	 * If set to true the pageParam, startParam and limitParam option of the proxy will be
	 * set to undefined. This prevents the proxy of sending the page, start and limit
	 * parameter to the server.
	 *
	 * <pre>
	 *   proxy: {
	 *     type: 'direct',
	 *     pageParam: undefined,
	 *     startParam: undefined,
	 *     limitParam: undefined,
	 *   }
	 * </pre>
	 *
	 * Default value is false
	 */
	public void setDisablePagingParameters(boolean disablePagingParameters) {
		this.disablePagingParameters = disablePagingParameters;
	}

	public String getReadMethod() {
		return this.readMethod;
	}

	/**
	 * Specifies the read method. This is a ExtDirect reference in the form
	 * action.methodName. See
	 * <a href= "http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.proxy.Direct-cfg-api" >
	 * Ext.data.proxy.Direct#api</a>.
	 * <p>
	 * If only the readMethod is specified generator will write property <a href=
	 * "http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.proxy.Direct-cfg-directFn" >
	 * directFn</a> instead.
	 *
	 * @param readMethod new value for read method
	 */
	public void setReadMethod(String readMethod) {
		this.readMethod = readMethod;
	}

	public String getCreateMethod() {
		return this.createMethod;
	}

	/**
	 * Specifies the create method. This is a ExtDirect reference in the form
	 * action.methodName. See
	 * <a href= "http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.proxy.Direct-cfg-api" >
	 * Ext.data.proxy.Direct#api</a>.
	 *
	 * @param createMethod new value for create method
	 */
	public void setCreateMethod(String createMethod) {
		this.createMethod = createMethod;
	}

	public String getUpdateMethod() {
		return this.updateMethod;
	}

	/**
	 * Specifies the update method. This is a ExtDirect reference in the form
	 * action.methodName. See
	 * <a href= "http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.proxy.Direct-cfg-api" >
	 * Ext.data.proxy.Direct#api</a>.
	 *
	 * @param updateMethod new value for update method
	 */
	public void setUpdateMethod(String updateMethod) {
		this.updateMethod = updateMethod;
	}

	public String getDestroyMethod() {
		return this.destroyMethod;
	}

	/**
	 * Specifies the destroy method. This is a ExtDirect reference in the form
	 * action.methodName. See
	 * <a href= "http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.proxy.Direct-cfg-api" >
	 * Ext.data.proxy.Direct#api</a>.
	 *
	 * @param destroyMethod new value for destroy method
	 */
	public void setDestroyMethod(String destroyMethod) {
		this.destroyMethod = destroyMethod;
	}

	/**
	 * @return the messageProperty
	 */
	public String getMessageProperty() {
		return this.messageProperty;
	}

	/**
	 * if set add to reader
	 *
	 * <pre>
	 * reader : {
	 *   messageProperty : 'your property name'
	 * }
	 * </pre>
	 *
	 * It is useful to add a customized message in case of error See <a href=
	 * "http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.reader.Reader-cfg-messageProperty"
	 * >Ext.data.reader.Reader#messageProperty</a>
	 *
	 * @param messageProperty the messageProperty to set
	 */
	public void setMessageProperty(String messageProperty) {
		this.messageProperty = messageProperty;
	}

	public String getWriter() {
		return this.writer;
	}

	/**
	 * If set adds a writer config to the proxy object.
	 *
	 * <pre>
	 *   proxy: {
	 *     type: 'direct',
	 *     writer: {
	 *       type: 'mywriter'
	 *     }
	 *   }
	 * </pre>
	 *
	 * See
	 * <a href= "http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.proxy.Proxy-cfg-writer"
	 * >Ext.data.proxy.Proxy#writer</a>
	 * <p>
	 * Defaults to "json"
	 */
	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getReader() {
		return this.reader;
	}

	/**
	 * If set adds a reader config to the proxy object.
	 *
	 * <pre>
	 *   proxy: {
	 *     type: 'direct',
	 *     reader: {
	 *       type: 'myreader'
	 *     }
	 *   }
	 * </pre>
	 *
	 * See <a href=
	 * "http://docs.sencha.com/extjs/5.0/apidocs/#!/api/Ext.data.proxy.Proxy-cfg-reader" >
	 * Ext.data.proxy.Proxy#reader</a>
	 * <p>
	 * Defaults to "json"
	 */
	public void setReader(String reader) {
		this.reader = reader;
	}

	public String getSuccessProperty() {
		return this.successProperty;
	}

	/**
	 * If set add to reader
	 *
	 * <pre>
	 * reader : {
	 *   successProperty : 'success'
	 * }
	 * </pre>
	 *
	 * See <a href=
	 * "http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.reader.Reader-cfg-successProperty"
	 * >Ext.data.reader.Reader#successProperty</a>
	 * <p>
	 * If not present default value 'success' is used.
	 */
	public void setSuccessProperty(String successProperty) {
		this.successProperty = successProperty;
	}

	public String getTotalProperty() {
		return this.totalProperty;
	}

	/**
	 * If set add to reader
	 *
	 * <pre>
	 * reader : {
	 *   totalProperty : 'total'
	 * }
	 * </pre>
	 *
	 * See <a href=
	 * "http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.reader.Reader-cfg-totalProperty"
	 * >Ext.data.reader.Reader#totalProperty</a>
	 * <p>
	 * If not present default value 'total' is used.
	 */
	public void setTotalProperty(String totalProperty) {
		this.totalProperty = totalProperty;
	}

	public String getRootProperty() {
		return this.rootProperty;
	}

	/**
	 * If set a reader config with root : 'rootProperty' (Ext JS 4) or rootProperty :
	 * 'rootProperty' (Sencha Touch 2 and Ext JS 5) will be added to the model object.
	 *
	 * <pre>
	 * reader : {
	 *   rootProperty : 'rootProperty'
	 * }
	 * </pre>
	 *
	 * If {@link #paging()} and {@link #rootProperty()} are present
	 * {@link #rootProperty()} has precedence.
	 * <p>
	 * See
	 * <a href= "http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.reader.Reader-cfg-root"
	 * >Ext.data.reader.Reader#root</a>
	 */
	public void setRootProperty(String rootProperty) {
		this.rootProperty = rootProperty;
	}

	public Boolean getWriteAllFields() {
		return this.writeAllFields;
	}

	/**
	 * If specified the generator adds a writer config object to the proxy with
	 * writeAllFields.
	 *
	 * <pre>
	 *   proxy: {
	 *     type: 'direct',
	 *     writer: {
	 *       writeAllFields: true
	 *     }
	 *   }
	 * </pre>
	 *
	 * See <a href=
	 * "http://docs.sencha.com/extjs/5.0/apidocs/#!/api/Ext.data.writer.Writer-cfg-writeAllFields"
	 * >Ext.data.writer.WriterView#writeAllFields</a>
	 * <p>
	 * Defaults to true
	 */
	public void setWriteAllFields(Boolean writeAllFields) {
		this.writeAllFields = writeAllFields;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	/**
	 * The id generator to use for this model.
	 * <p>
	 * See <a href=
	 * "http://docs.sencha.com/extjs/5.0/apidocs/#!/api/Ext.data.Model-cfg-identifier" >
	 * Ext.data.Model#identifier</a>
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getClientIdProperty() {
		return this.clientIdProperty;
	}

	/**
	 * The name of the property a server will use to send back a client-generated id in a
	 * create or update operation.
	 * <p>
	 * See <a href=
	 * "http://docs.sencha.com/extjs/5.0/apidocs/#!/api/Ext.data.Model-cfg-clientIdProperty"
	 * >Ext.data.Model#clientIdProperty</a>
	 */
	public void setClientIdProperty(String clientIdProperty) {
		this.clientIdProperty = clientIdProperty;
	}

	public boolean isClientIdPropertyAddToWriter() {
		return this.clientIdPropertyAddToWriter;
	}

	/**
	 * If true configures the clientIdProperty on the proxy's writer config. Uses the
	 * value of {@link #clientIdProperty}. If {@link #clientIdProperty} is not set nothing
	 * happens.
	 */
	public void setClientIdPropertyAddToWriter(boolean clientIdPropertyAddToWriter) {
		this.clientIdPropertyAddToWriter = clientIdPropertyAddToWriter;
	}

	public AllDataOptionsBean getAllDataOptions() {
		return this.allDataOptions;
	}

	/**
	 * Configuration for the writer
	 * <p>
	 * This object contains the options passed to Ext.data.Model.getData when writing
	 * Ext.data.Model.phantom records or when writeAllFields is set to true.
	 * <p>
	 * See <a href=
	 * "http://docs.sencha.com/extjs/6.0/6.0.2-classic/#!/api/Ext.data.writer.Writer-cfg-allDataOptions"
	 * > Ext.data.Model#allDataOptions</a>
	 */
	public void setAllDataOptions(AllDataOptionsBean allDataOptions) {
		this.allDataOptions = allDataOptions;
	}

	public PartialDataOptionsBean getPartialDataOptions() {
		return this.partialDataOptions;
	}

	/**
	 * Configuration for the writer
	 * <p>
	 * This object contains the options passed to Ext.data.Model.getData when writing non
	 * Ext.data.Model.phantom records or when writeAllFields is set to false.
	 * <p>
	 * See <a href=
	 * "http://docs.sencha.com/extjs/6.0/6.0.2-classic/#!/api/Ext.data.writer.Writer-cfg-partialDataOptions"
	 * > Ext.data.Model#partialDataOptions</a>
	 */
	public void setPartialDataOptions(PartialDataOptionsBean partialDataOptions) {
		this.partialDataOptions = partialDataOptions;
	}

	public String[] getHasMany() {
		return hasMany;
	}

	/**
	 * Configures One-to-Many associations without foreign keys
	 * <p>
	 * See <a href=
	 * "http://docs.sencha.com/extjs/6.0/6.0.2-classic/#!/api/Ext.data.Model-cfg-hasMany">Ext.data.Model-cfg-hasMany</a>
	 */
	public void setHasMany(String[] hasMany) {
		this.hasMany = hasMany;
	}

}
