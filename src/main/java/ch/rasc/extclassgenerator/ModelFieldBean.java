/**
 * Copyright 2013-2014 Ralph Schaer <ralphschaer@gmail.com>
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

import java.util.List;

import ch.rasc.extclassgenerator.validation.AbstractValidation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * Represents one field in a {@link ModelBean}
 */
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(value = { "name", "type", "defaultValue", "dateFormat", "useNull",
		"mapping" })
public class ModelFieldBean {
	private String name;

	@JsonIgnore
	private ModelType modelType;

	private String type;

	@JsonRawValue
	private Object defaultValue;

	private String dateFormat;

	private Boolean useNull;

	private String mapping;

	// only a false value will be generated
	private Boolean persist = null;

	// only a true value will be generated
	@JsonView(JsonViews.ExtJS5.class)
	private Boolean critical = null;

	@JsonRawValue
	private String convert;

	@JsonRawValue
	@JsonView(JsonViews.ExtJS5.class)
	private String calculate;

	@JsonView(JsonViews.ExtJS5.class)
	private List<AbstractValidation> validators;

	@JsonView(JsonViews.ExtJS5.class)
	private List<String> depends;

	/**
	 * Creates a new ModelFieldBean with name and type
	 *
	 * @param name name of the field
	 * @param type type of the field
	 */
	public ModelFieldBean(String name, ModelType type) {
		this.name = name;

		if (type != null) {
			setModelType(type);
		}
		else {
			setModelType(ModelType.AUTO);
		}
	}

	/**
	 * Creates a new ModelFieldBean with name and type
	 *
	 * @param name name of the field
	 * @param type type of the field
	 */
	public ModelFieldBean(String name, String type) {
		this.name = name;
		this.modelType = null;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	/**
	 * Name of the field. Property '<a
	 * href="http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.Field-cfg-name" >name</a>'
	 * in JS.
	 *
	 * @param name new name for the field
	 */
	public void setName(String name) {
		this.name = name;
	}

	public ModelType getModelType() {
		return modelType;
	}

	public String getType() {
		return type;
	}

	/**
	 * Type of the field. Property '<a
	 * href="http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.Field-cfg-type" >type</a>'
	 * in JS.
	 *
	 * @param type new type for the field
	 */
	public void setModelType(ModelType type) {
		this.modelType = type;
		this.type = type.getJsName();
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	/**
	 * The default value. Property '<a href=
	 * "http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.Field-cfg-defaultValue"
	 * >defaultValue</a>' in JS.
	 *
	 * @param defaultValue new defaultValue
	 */
	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	/**
	 * Specifies format of date. Property '<a href=
	 * "http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.Field-cfg-dateFormat"
	 * >dateFormat</a>' in JS.<br>
	 * For a list of all supported formats see Sencha Doc: <a
	 * href="http://docs.sencha.com/ext-js/4-2/#!/api/Ext.Date">Ext.Date</a>
	 * <p>
	 * Will be ignored if the field is not a {@link ModelType#DATE} field.
	 *
	 * @param dateFormat new dateFormat String
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Boolean getUseNull() {
		return useNull;
	}

	/**
	 * If true null value is used if value cannot be parsed. If false default values are
	 * used (0 for integer and float, "" for string and false for boolean).<br>
	 * Property '<a href=
	 * "http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.Field-cfg-useNull" >useNull</a>'
	 * in JS.<br>
	 * <p>
	 * Only used if type of field is {@link ModelType#INTEGER}, {@link ModelType#FLOAT},
	 * {@link ModelType#STRING} or {@link ModelType#BOOLEAN}.
	 *
	 * @param useNull new value for useNull
	 */
	public void setUseNull(Boolean useNull) {
		this.useNull = useNull;
	}

	public String getMapping() {
		return mapping;
	}

	/**
	 * Typical use for a virtual field to extract field data from the model object <br>
	 * Property '<a href=
	 * "http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.Field-cfg-mapping" >mapping</a>'
	 * in JS.<br>
	 * <p>
	 *
	 * @param mapping A path expression
	 */
	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public Boolean getPersist() {
		return persist;
	}

	/**
	 * Prevent the value of this field to be serialized or written with
	 * Ext.data.writer.Writer <br>
	 * Typical use for a virtual field <br>
	 * Property '<a href=
	 * "http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.Field-cfg-persist" >persist</a>'
	 * in JS.<br>
	 * <p>
	 *
	 * @param persist defaults to true, only a false value will be generated
	 */
	public void setPersist(Boolean persist) {
		this.persist = persist;
	}

	public Boolean getCritical() {
		return critical;
	}

	/**
	 * A critical field is a field that must always be sent to the server even if it has
	 * not changed.
	 * <p>
	 * See <a href=
	 * "http://docs.sencha.com/ext/5.0.0/apidocs/#!/api/Ext.data.field.Field-cfg-critical"
	 * >Ext.data.field.FieldView#critical</a>
	 * <p>
	 * Defaults to false
	 */
	public void setCritical(Boolean critical) {
		this.critical = critical;
	}

	public String getConvert() {
		return convert;
	}

	/**
	 * Function which coerces string values in raw data into the field's type <br>
	 * Typical use for a virtual field <br>
	 * http://localhost/ext4.1/docs/index.html#!/api/Ext.data.Field-cfg-convert Property
	 * '<a href= "http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.Field-cfg-convert" >
	 * Ext.data.Field.convert</a>' in JS.<br>
	 *
	 * @param convert A function. JavaScript Syntax example: function(v, record) { return
	 * ... ; }
	 */
	public void setConvert(String convert) {
		this.convert = convert;
	}

	public String getCalculate() {
		return calculate;
	}

	/**
	 * This config defines a simple field calculation function. A calculate method only
	 * has access to the record data and should return the value of the calculated field.
	 * When provided in this way, the depends config is automatically determined by
	 * parsing the calculate function.
	 * 
	 * See <a href=
	 * "http://docs.sencha.com/ext/5.0.0/apidocs/#!/api/Ext.data.field.Field-cfg-calculate"
	 * >Ext.data.Field.calculate</a>
	 */
	public void setCalculate(String calculate) {
		this.calculate = calculate;
	}

	public List<AbstractValidation> getValidators() {
		return validators;
	}

	public void setValidators(List<AbstractValidation> validators) {
		this.validators = validators;
	}

	public List<String> getDepends() {
		return depends;
	}

	/**
	 * The field name or names within the Model on which the value of this field depends,
	 * and from which a new value may be calculated. These values are the values used by
	 * the {@link #convert()} method. If you do not have a {@link #convert()} method then
	 * this config should not be specified. Before using this config you should consider
	 * if using a {@link #calculate()} method instead of a {@link #convert()} method would
	 * be simpler.
	 * <p>
	 * See <a href=
	 * "http://docs.sencha.com/ext/5.0.0/apidocs/#!/api/Ext.data.field.Field-cfg-depends"
	 * >Ext.data.field.FieldView#depends</a>
	 */
	public void setDepends(List<String> depends) {
		this.depends = depends;
	}

}
