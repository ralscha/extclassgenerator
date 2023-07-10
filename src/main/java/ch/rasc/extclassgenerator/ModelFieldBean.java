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

import java.util.List;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;

import ch.rasc.extclassgenerator.validation.AbstractValidation;

/**
 * Represents one field in a {@link ModelBean}
 */
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(value = { "name", "type", "defaultValue", "dateFormat", "allowNull",
		"useNull", "persist", "mapping", "convert", "depends", "calculate" })
public class ModelFieldBean {
	private String name;

	@JsonIgnore
	private ModelType modelType;

	private String type;

	@JsonRawValue
	private Object defaultValue;

	private String dateFormat;

	@JsonView(JsonViews.ExtJS4.class)
	private Boolean useNull;

	@JsonView({ JsonViews.ExtJS5.class, JsonViews.Touch2.class })
	private Boolean allowNull;

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

	@JsonView(JsonViews.ExtJS5.class)
	private Object reference;

	@JsonView(JsonViews.ExtJS5.class)
	private Boolean allowBlank;

	@JsonView(JsonViews.ExtJS5.class)
	private Boolean unique;

	/**
	 * Returns true if only the name property is set
	 */
	public boolean hasOnlyName(OutputConfig outputConfig) {
		if (StringUtils.hasText(this.name) && !StringUtils.hasText(this.type)
				&& this.defaultValue == null && !StringUtils.hasText(this.dateFormat)
				&& !StringUtils.hasText(this.mapping) && this.persist == null
				&& !StringUtils.hasText(this.convert)) {
			if (outputConfig.getOutputFormat() == OutputFormat.EXTJS4) {
				return this.useNull == null;
			}
			else if (outputConfig.getOutputFormat() == OutputFormat.TOUCH2) {
				return this.allowNull == null;
			}
			else if (outputConfig.getOutputFormat() == OutputFormat.EXTJS5) {
				return this.allowNull == null && this.critical == null
						&& !StringUtils.hasText(this.calculate)
						&& (this.validators == null || this.validators.isEmpty())
						&& (this.depends == null || this.depends.isEmpty())
						&& this.reference == null && this.allowBlank == null
						&& this.unique == null;
			}
		}

		return false;
	}

	/**
	 * Creates a new ModelFieldBean with name and type
	 *
	 * @param name name of the field
	 * @param type type of the field
	 */
	public ModelFieldBean(String name, ModelType type) {
		this.name = name;

		if (type != null && type != ModelType.NOT_SPECIFIED) {
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
		if (!"auto".equals(type)) {
			this.type = type;
		}
		else {
			this.type = null;
		}
	}

	public String getName() {
		return this.name;
	}

	/**
	 * Name of the field. Property '
	 * <a href="http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.Field-cfg-name" >name
	 * </a>' in JS.
	 *
	 * @param name new name for the field
	 */
	public void setName(String name) {
		this.name = name;
	}

	public ModelType getModelType() {
		return this.modelType;
	}

	public String getType() {
		return this.type;
	}

	/**
	 * Type of the field. Property '
	 * <a href="http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.Field-cfg-type" >type
	 * </a>' in JS.
	 *
	 * @param type new type for the field
	 */
	public void setModelType(ModelType type) {
		this.modelType = type;

		if (type != ModelType.AUTO) {
			this.type = type.getJsName();
		}
		else {
			this.type = null;
		}
	}

	public Object getDefaultValue() {
		return this.defaultValue;
	}

	/**
	 * The default value. Property '
	 * <a href= "http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.Field-cfg-defaultValue"
	 * >defaultValue</a>' in JS.
	 *
	 * @param defaultValue new defaultValue
	 */
	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDateFormat() {
		return this.dateFormat;
	}

	/**
	 * Specifies format of date. Property '
	 * <a href= "http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.Field-cfg-dateFormat" >
	 * dateFormat</a>' in JS.<br>
	 * For a list of all supported formats see Sencha Doc:
	 * <a href="http://docs.sencha.com/ext-js/4-2/#!/api/Ext.Date">Ext.Date</a>
	 * <p>
	 * Will be ignored if the field is not a {@link ModelType#DATE} field.
	 *
	 * @param dateFormat new dateFormat String
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Boolean getUseNull() {
		return this.useNull;
	}

	/**
	 * If true null value is used if value cannot be parsed. If false default values are
	 * used (0 for integer and float, "" for string and false for boolean).<br>
	 * Property '
	 * <a href= "http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.Field-cfg-useNull" >
	 * useNull</a>' in JS.<br>
	 * <p>
	 * Only used if type of field is {@link ModelType#INTEGER}, {@link ModelType#FLOAT},
	 * {@link ModelType#NUMBER}, {@link ModelType#STRING} or {@link ModelType#BOOLEAN}.
	 *
	 * @param useNull new value for useNull
	 */
	public void setUseNull(Boolean useNull) {
		this.useNull = useNull;
		this.allowNull = useNull;
	}

	public String getMapping() {
		return this.mapping;
	}

	/**
	 * Typical use for a virtual field to extract field data from the model object <br>
	 * Property '
	 * <a href= "http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.Field-cfg-mapping" >
	 * mapping</a>' in JS.<br>
	 * <p>
	 *
	 * @param mapping A path expression
	 */
	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public Boolean getPersist() {
		return this.persist;
	}

	/**
	 * Prevent the value of this field to be serialized or written with
	 * Ext.data.writer.Writer <br>
	 * Typical use for a virtual field <br>
	 * Property '
	 * <a href= "http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.Field-cfg-persist" >
	 * persist</a>' in JS.<br>
	 * <p>
	 *
	 * @param persist defaults to true, only a false value will be generated
	 */
	public void setPersist(Boolean persist) {
		this.persist = persist;
	}

	public Boolean getCritical() {
		return this.critical;
	}

	/**
	 * A critical field is a field that must always be sent to the server even if it has
	 * not changed.
	 * <p>
	 * See <a href=
	 * "http://docs.sencha.com/extjs/5.0/apidocs/#!/api/Ext.data.field.Field-cfg-critical"
	 * >Ext.data.field.FieldView#critical</a>
	 * <p>
	 * Defaults to false
	 */
	public void setCritical(Boolean critical) {
		this.critical = critical;
	}

	public String getConvert() {
		return this.convert;
	}

	/**
	 * Function which coerces string values in raw data into the field's type <br>
	 * Typical use for a virtual field <br>
	 * http://localhost/ext4.1/docs/index.html#!/api/Ext.data.Field-cfg-convert Property '
	 * <a href= "http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.Field-cfg-convert" >
	 * Ext.data.Field.convert</a>' in JS.<br>
	 *
	 * @param convert A function. JavaScript Syntax example: function(v, record) { return
	 * ... ; }
	 */
	public void setConvert(String convert) {
		this.convert = convert;
	}

	public String getCalculate() {
		return this.calculate;
	}

	/**
	 * This config defines a simple field calculation function. A calculate method only
	 * has access to the record data and should return the value of the calculated field.
	 * When provided in this way, the depends config is automatically determined by
	 * parsing the calculate function.
	 *
	 * See <a href=
	 * "http://docs.sencha.com/extjs/5.0/apidocs/#!/api/Ext.data.field.Field-cfg-calculate"
	 * >Ext.data.Field.calculate</a>
	 */
	public void setCalculate(String calculate) {
		this.calculate = calculate;
	}

	public List<AbstractValidation> getValidators() {
		return this.validators;
	}

	public void setValidators(List<AbstractValidation> validators) {
		this.validators = validators;
	}

	public List<String> getDepends() {
		return this.depends;
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
	 * "http://docs.sencha.com/extjs/5.0/apidocs/#!/api/Ext.data.field.Field-cfg-depends"
	 * >Ext.data.field.FieldView#depends</a>
	 */
	public void setDepends(List<String> depends) {
		this.depends = depends;
	}

	public Object getReference() {
		return this.reference;
	}

	/**
	 * Defines a relationship to another model. Either a string or an instace of type
	 * {@link ReferenceBean}
	 * <p>
	 * See <a href=
	 * "http://docs.sencha.com/extjs/5.0/apidocs/#!/api/Ext.data.field.Field-cfg-reference"
	 * >Ext.data.Field#reference</a>
	 */
	public void setReference(Object reference) {
		this.reference = reference;
	}

	public Boolean getAllowNull() {
		return this.allowNull;
	}

	/**
	 * Use when converting received data into a <code>integer</code>,
	 * <code>float/number</code>, <code>boolean</code> or <code>string</code> type. If the
	 * value cannot be parsed, null will be used if allowNull is true, otherwise a default
	 * value for that type will be used (0 for integer and float/number, "" for string and
	 * false for boolean)
	 * <p>
	 * See <a href=
	 * "http://docs.sencha.com/extjs/5.0/apidocs/#!/api/Ext.data.field.Field-cfg-allowNull"
	 * >Ext.data.Field#allowNull</a>
	 * <p>
	 * Defaults to false
	 * <p>
	 * Only used if type of field is {@link ModelType#INTEGER}, {@link ModelType#FLOAT},
	 * {@link ModelType#NUMBER}, {@link ModelType#STRING} or {@link ModelType#BOOLEAN}.
	 * <p>
	 * <strong>This is another name for {@link #setUseNull(Boolean)}. Both properties
	 * behave exactly the same. Use only one.</strong>
	 */
	public void setAllowNull(Boolean allowNull) {
		this.allowNull = allowNull;
		this.useNull = allowNull;
	}

	public Boolean getAllowBlank() {
		return this.allowBlank;
	}

	/**
	 * Used for validating a model.
	 * <p>
	 * Set <a href=
	 * "http://docs.sencha.com/extjs/5.0/apidocs/#!/api/Ext.data.field.Field-cfg-allowBlank"
	 * >Ext.data.Field#allowBlank</a>
	 * <p>
	 * Defaults to true
	 */
	public void setAllowBlank(Boolean allowBlank) {
		this.allowBlank = allowBlank;
	}

	public Boolean getUnique() {
		return this.unique;
	}

	/**
	 * true if the value of this field is unique amongst all instances. When used with a
	 * reference this describes a "one-to-one" relationship
	 * <p>
	 * See <a href=
	 * "http://docs.sencha.com/extjs/5.0/apidocs/#!/api/Ext.data.field.Field-cfg-unique" >
	 * Ext.data.Field#unique</a>
	 * <p>
	 * Defaults to false
	 */
	public void setUnique(Boolean unique) {
		this.unique = unique;
	}

	void updateTypes(OutputConfig outputConfig) {
		if (outputConfig.getOutputFormat() == OutputFormat.EXTJS5) {
			if ("int".equals(this.type)) {
				this.type = "integer";
			}
			else if ("float".equals(this.type)) {
				this.type = "number";
			}
		}
	}

}
