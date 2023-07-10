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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ch.rasc.extclassgenerator.validation.AbstractValidation;
import ch.rasc.extclassgenerator.validation.EmailValidation;
import ch.rasc.extclassgenerator.validation.FormatValidation;
import ch.rasc.extclassgenerator.validation.GenericValidation;
import ch.rasc.extclassgenerator.validation.LengthValidation;
import ch.rasc.extclassgenerator.validation.PresenceValidation;

public class ModelGeneratorWithValidationTest {

	@Before
	public void clearCaches() {
		ModelGenerator.clearCaches();
	}

	@Test
	public void testFromModelBeanExtJS1() {
		ModelBean model = new ModelBean();
		model.setName("App.User");
		model.addField(new ModelFieldBean("id", ModelType.INTEGER));
		model.addField(new ModelFieldBean("name", ModelType.STRING));
		model.addValidation(new PresenceValidation("id"));
		model.addValidation(new LengthValidation("name", 2, null));

		String code = ModelGenerator.generateJavascript(model, OutputFormat.EXTJS4,
				false);

		assertThat(code).isEqualTo(
				"Ext.define(\"App.User\",{extend:\"Ext.data.Model\",fields:[{name:\"id\",type:\"int\"},{name:\"name\",type:\"string\"}],validations:[{type:\"presence\",field:\"id\"},{type:\"length\",field:\"name\",min:2}]});");
	}

	@Test
	public void testFromModelBeanExtJS2() {
		ModelBean model = new ModelBean();
		model.setName("App.User");
		model.addField(new ModelFieldBean("id", ModelType.INTEGER));
		model.addField(new ModelFieldBean("email", ModelType.STRING));
		ModelFieldBean field = new ModelFieldBean("salary", ModelType.FLOAT);
		field.setUseNull(Boolean.TRUE);
		model.addField(field);

		List<AbstractValidation> validations = new ArrayList<>();
		validations.add(new PresenceValidation("id"));
		validations.add(new EmailValidation("email"));
		model.setValidations(validations);
		model.addValidation(new FormatValidation("salary", "[0-9]*\\.[0-9]*"));

		model.addValidation(new GenericValidation("myOwnValidator1", "name", null));

		Map<String, Object> options = new LinkedHashMap<>();
		options.put("o1", "s");
		options.put("o2", 10);
		options.put("o3", Boolean.TRUE);
		model.addValidation(new GenericValidation("myOwnValidator2", "id", options));

		String code = ModelGenerator.generateJavascript(model, OutputFormat.EXTJS4,
				false);
		assertThat(code).isEqualTo(
				"Ext.define(\"App.User\",{extend:\"Ext.data.Model\",fields:[{name:\"id\",type:\"int\"},{name:\"email\",type:\"string\"},{name:\"salary\",type:\"float\",useNull:true}],validations:[{type:\"presence\",field:\"id\"},{type:\"email\",field:\"email\"},{type:\"format\",field:\"salary\",matcher:/[0-9]*\\.[0-9]*/},{type:\"myOwnValidator1\",field:\"name\"},{type:\"myOwnValidator2\",field:\"id\",o1:\"s\",o2:10,o3:true}]});");

		code = ModelGenerator.generateJavascript(model, OutputFormat.EXTJS4, false);
		assertThat(code).isEqualTo(
				"Ext.define(\"App.User\",{extend:\"Ext.data.Model\",fields:[{name:\"id\",type:\"int\"},{name:\"email\",type:\"string\"},{name:\"salary\",type:\"float\",useNull:true}],validations:[{type:\"presence\",field:\"id\"},{type:\"email\",field:\"email\"},{type:\"format\",field:\"salary\",matcher:/[0-9]*\\.[0-9]*/},{type:\"myOwnValidator1\",field:\"name\"},{type:\"myOwnValidator2\",field:\"id\",o1:\"s\",o2:10,o3:true}]});");
	}

	@Test
	public void testFromModelBeanTouch1() {
		ModelBean model = new ModelBean();
		model.setName("App.User");

		List<ModelFieldBean> fields = new ArrayList<>();
		fields.add(new ModelFieldBean("id", ModelType.INTEGER));
		fields.add(new ModelFieldBean("name", ModelType.STRING));
		model.addFields(fields);

		model.addValidation(new PresenceValidation("id"));
		model.addValidation(new LengthValidation("name", 2, null));
		model.addValidation(new GenericValidation("myOwnValidator1", "name", null));

		Map<String, Object> options = new LinkedHashMap<>();
		options.put("o1", "s");
		options.put("o2", 10);
		options.put("o3", Boolean.TRUE);
		model.addValidation(new GenericValidation("myOwnValidator2", "id", options));

		String code = ModelGenerator.generateJavascript(model, OutputFormat.TOUCH2,
				false);
		assertThat(code).isEqualTo(
				"Ext.define(\"App.User\",{extend:\"Ext.data.Model\",config:{fields:[{name:\"id\",type:\"int\"},{name:\"name\",type:\"string\"}],validations:[{type:\"presence\",field:\"id\"},{type:\"length\",field:\"name\",min:2},{type:\"myOwnValidator1\",field:\"name\"},{type:\"myOwnValidator2\",field:\"id\",o1:\"s\",o2:10,o3:true}]}});");

	}

	@Test
	public void testFromModelBeanTouch2() {
		ModelBean model = new ModelBean();
		model.setName("App.User");
		model.addField(new ModelFieldBean("id", ModelType.INTEGER));
		ModelFieldBean field = new ModelFieldBean("salary", ModelType.FLOAT);
		field.setUseNull(Boolean.TRUE);
		model.addField(field);

		model.addValidation(new PresenceValidation("id"));
		model.addValidations(Collections
				.<AbstractValidation>singletonList(new EmailValidation("email")));
		model.addValidation(new FormatValidation("salary", "[0-9]*\\.[0-9]*"));

		String code = ModelGenerator.generateJavascript(model, OutputFormat.TOUCH2,
				false);
		assertThat(code).isEqualTo(
				"Ext.define(\"App.User\",{extend:\"Ext.data.Model\",config:{fields:[{name:\"id\",type:\"int\"},{name:\"salary\",type:\"float\",allowNull:true}],validations:[{type:\"presence\",field:\"id\"},{type:\"email\",field:\"email\"},{type:\"format\",field:\"salary\",matcher:/[0-9]*\\.[0-9]*/}]}});");

		code = ModelGenerator.generateJavascript(model, OutputFormat.TOUCH2, false);
		assertThat(code).isEqualTo(
				"Ext.define(\"App.User\",{extend:\"Ext.data.Model\",config:{fields:[{name:\"id\",type:\"int\"},{name:\"salary\",type:\"float\",allowNull:true}],validations:[{type:\"presence\",field:\"id\"},{type:\"email\",field:\"email\"},{type:\"format\",field:\"salary\",matcher:/[0-9]*\\.[0-9]*/}]}});");
	}

}
