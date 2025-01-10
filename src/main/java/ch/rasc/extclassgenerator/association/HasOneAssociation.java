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
package ch.rasc.extclassgenerator.association;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ch.rasc.extclassgenerator.Model;

/**
 * A hasOne association
 */
@JsonInclude(Include.NON_NULL)
public class HasOneAssociation extends AbstractAssociation {

	private String setterName;

	private String getterName;

	private String name;

	/**
	 * Creates an instance of a hasOne association. Sets {@link #model} to the provided
	 * parameters.
	 *
	 * @param model The name of the model that is being associated with.
	 */
	public HasOneAssociation(String model) {
		super("hasOne", model);
	}

	/**
	 * Creates an instance of a hasOne association. Sets {@link #model} to the full
	 * qualified name of the model class or the string from {@link Model#value()} if
	 * present on the class.
	 *
	 * @param model The class of the model that is being associated with.
	 */
	public HasOneAssociation(Class<?> model) {
		this(getModelName(model));
	}

	public String getSetterName() {
		return this.setterName;
	}

	/**
	 * The name of the setter function that will be added to the local model's prototype.
	 * Defaults to 'set' + the name of the foreign model, e.g. setCategory.
	 * <p>
	 * Corresponds to the <a href=
	 * "http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.association.HasOne-cfg-setterName"
	 * >setterName</a> config property.
	 *
	 * @param setterName the new name for the getterName
	 */
	public void setSetterName(String setterName) {
		this.setterName = setterName;
	}

	public String getGetterName() {
		return this.getterName;
	}

	/**
	 * The name of the getter function that will be added to the local model's prototype.
	 * Defaults to 'get' + the name of the foreign model, e.g. getCategory.
	 * <p>
	 * Corresponds to the <a href=
	 * "http://docs.sencha.com/ext-js/4-2/#!/api/Ext.data.association.HasOne-cfg-getterName"
	 * >getterName</a> config property.
	 *
	 * @param getterName the new name for the getterName
	 */
	public void setGetterName(String getterName) {
		this.getterName = getterName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
