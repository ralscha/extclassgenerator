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
package ch.rasc.extclassgenerator.bean;

import java.util.ArrayList;
import java.util.List;

import ch.rasc.extclassgenerator.ModelFieldBean;
import ch.rasc.extclassgenerator.ModelType;
import ch.rasc.extclassgenerator.ModelValidation;
import ch.rasc.extclassgenerator.ModelValidationParameter;
import ch.rasc.extclassgenerator.ModelValidationType;
import ch.rasc.extclassgenerator.ModelValidations;

@ModelValidations({ @ModelValidation(propertyName = "singleton",
		value = ModelValidationType.PRESENCE) })
public class BeanWithGenericValidation {

	@ModelValidation(value = ModelValidationType.GENERIC,
			parameters = { @ModelValidationParameter(name = "type", value = "notUnique"),
					@ModelValidationParameter(name = "update", value = "true") })
	public String singleton;

	@ModelValidation(value = ModelValidationType.EXCLUSION,
			exclusionOrInclusionList = { "_" })
	public String excluded;

	@ModelValidation(value = ModelValidationType.EXCLUSION,
			exclusionOrInclusionList = { "_", "*" })
	public String excluded2;

	@ModelValidation(value = ModelValidationType.INCLUSION,
			exclusionOrInclusionList = { "fish", "fruit" })
	public String included;

	@ModelValidation(value = ModelValidationType.EXCLUSION,
			parameters = @ModelValidationParameter(name = "list", value = "[\"_\"]"))
	public String excludedV1;

	@ModelValidation(value = ModelValidationType.INCLUSION,
			parameters = @ModelValidationParameter(name = "list",
					value = "[\"fish\",\"fruit\"]"))
	public String includedV1;

	public static List<ModelFieldBean> expectedFields = new ArrayList<>();

	static {

		ModelFieldBean field = new ModelFieldBean("singleton", ModelType.STRING);
		expectedFields.add(field);

		field = new ModelFieldBean("excluded", ModelType.STRING);
		expectedFields.add(field);

		field = new ModelFieldBean("excluded2", ModelType.STRING);
		expectedFields.add(field);

		field = new ModelFieldBean("included", ModelType.STRING);
		expectedFields.add(field);

		field = new ModelFieldBean("excludedV1", ModelType.STRING);
		expectedFields.add(field);

		field = new ModelFieldBean("includedV1", ModelType.STRING);
		expectedFields.add(field);

	}
}
