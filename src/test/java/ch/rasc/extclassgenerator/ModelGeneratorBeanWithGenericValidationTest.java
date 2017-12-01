/**
 * Copyright 2013-2017 the original author or authors.
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

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import ch.rasc.extclassgenerator.bean.BeanWithGenericValidation;
import ch.rasc.extclassgenerator.validation.ExclusionValidation;
import ch.rasc.extclassgenerator.validation.ExclusionValidationArray;
import ch.rasc.extclassgenerator.validation.GenericValidation;
import ch.rasc.extclassgenerator.validation.InclusionValidation;
import ch.rasc.extclassgenerator.validation.InclusionValidationArray;
import ch.rasc.extclassgenerator.validation.LengthValidation;
import ch.rasc.extclassgenerator.validation.PresenceValidation;
import ch.rasc.extclassgenerator.validation.RangeValidation;

public class ModelGeneratorBeanWithGenericValidationTest {

	@Before
	public void clearCaches() {
		ModelGenerator.clearCaches();
	}

	@Test
	public void testWriteModelHttpServletRequestHttpServletResponseClassOfQOutputFormatBoolean()
			throws IOException {
		MockHttpServletResponse response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response,
				BeanWithGenericValidation.class, OutputFormat.EXTJS4,
				IncludeValidation.ALL, true);
		GeneratorTestUtil.compareExtJs4Code("BeanWithGenericValidation",
				response.getContentAsString(), true, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response,
				BeanWithGenericValidation.class, OutputFormat.TOUCH2,
				IncludeValidation.ALL, false);
		GeneratorTestUtil.compareTouch2Code("BeanWithGenericValidation",
				response.getContentAsString(), false, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response,
				BeanWithGenericValidation.class, OutputFormat.EXTJS4,
				IncludeValidation.ALL, true);
		GeneratorTestUtil.compareExtJs4Code("BeanWithGenericValidation",
				response.getContentAsString(), true, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response,
				BeanWithGenericValidation.class, OutputFormat.TOUCH2,
				IncludeValidation.ALL, true);
		GeneratorTestUtil.compareTouch2Code("BeanWithGenericValidation",
				response.getContentAsString(), true, false);
	}

	@Test
	public void testWriteModelHttpServletRequestHttpServletResponseModelBeanOutputFormat()
			throws IOException {
		MockHttpServletResponse response = new MockHttpServletResponse();
		ModelBean model = ModelGenerator.createModel(BeanWithGenericValidation.class,
				IncludeValidation.ALL);
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, model,
				OutputFormat.EXTJS4);
		GeneratorTestUtil.compareExtJs4Code("BeanWithGenericValidation",
				response.getContentAsString(), false, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, model,
				OutputFormat.TOUCH2);
		GeneratorTestUtil.compareTouch2Code("BeanWithGenericValidation",
				response.getContentAsString(), false, false);
	}

	@Test
	public void testWriteModelHttpServletRequestHttpServletResponseModelBeanOutputFormatBoolean()
			throws IOException {
		MockHttpServletResponse response = new MockHttpServletResponse();
		ModelBean model = ModelGenerator.createModel(BeanWithGenericValidation.class,
				IncludeValidation.ALL);
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, model,
				OutputFormat.EXTJS4, false);
		GeneratorTestUtil.compareExtJs4Code("BeanWithGenericValidation",
				response.getContentAsString(), false, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, model,
				OutputFormat.TOUCH2, false);
		GeneratorTestUtil.compareTouch2Code("BeanWithGenericValidation",
				response.getContentAsString(), false, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, model,
				OutputFormat.EXTJS4, true);
		GeneratorTestUtil.compareExtJs4Code("BeanWithGenericValidation",
				response.getContentAsString(), true, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response, model,
				OutputFormat.TOUCH2, true);
		GeneratorTestUtil.compareTouch2Code("BeanWithGenericValidation",
				response.getContentAsString(), true, false);
	}

	@Test
	public void testGenerateJavascriptClassOfQOutputFormatBoolean() {
		GeneratorTestUtil
				.compareExtJs4Code("BeanWithGenericValidation",
						ModelGenerator.generateJavascript(BeanWithGenericValidation.class,
								OutputFormat.EXTJS4, IncludeValidation.ALL, true),
						true, false);
		GeneratorTestUtil
				.compareExtJs4Code("BeanWithGenericValidation",
						ModelGenerator.generateJavascript(BeanWithGenericValidation.class,
								OutputFormat.EXTJS4, IncludeValidation.ALL, false),
						false, false);

		GeneratorTestUtil
				.compareTouch2Code("BeanWithGenericValidation",
						ModelGenerator.generateJavascript(BeanWithGenericValidation.class,
								OutputFormat.TOUCH2, IncludeValidation.ALL, true),
						true, false);
		GeneratorTestUtil
				.compareTouch2Code("BeanWithGenericValidation",
						ModelGenerator.generateJavascript(BeanWithGenericValidation.class,
								OutputFormat.TOUCH2, IncludeValidation.ALL, false),
						false, false);
	}

	@Test
	public void testCreateModel() {
		ModelBean modelBean = ModelGenerator.createModel(BeanWithGenericValidation.class,
				IncludeValidation.ALL);
		assertThat(modelBean.getReadMethod()).isNull();
		assertThat(modelBean.getCreateMethod()).isNull();
		assertThat(modelBean.getUpdateMethod()).isNull();
		assertThat(modelBean.getDestroyMethod()).isNull();
		assertThat(modelBean.getIdProperty()).isNull();
		assertThat(modelBean.getVersionProperty()).isNull();
		assertThat(modelBean.isPaging()).isFalse();
		assertThat(modelBean.getName())
				.isEqualTo("ch.rasc.extclassgenerator.bean.BeanWithGenericValidation");
		assertThat(modelBean.getFields()).hasSize(6);
		assertThat(BeanWithGenericValidation.expectedFields).hasSize(6);

		for (ModelFieldBean expectedField : BeanWithGenericValidation.expectedFields) {
			ModelFieldBean field = modelBean.getFields().get(expectedField.getName());
			assertThat(field).isEqualToComparingFieldByField(expectedField);
		}

		assertThat(modelBean.getValidations()).hasSize(7);

		assertThat(modelBean.getValidations().get(0))
				.isInstanceOf(PresenceValidation.class);
		PresenceValidation presenceValidation = (PresenceValidation) modelBean
				.getValidations().get(0);
		assertThat(presenceValidation.getType()).isEqualTo("presence");

		assertThat(modelBean.getValidations().get(1))
				.isInstanceOf(GenericValidation.class);
		GenericValidation genericValidation = (GenericValidation) modelBean
				.getValidations().get(1);
		assertThat(genericValidation.getType()).isEqualTo("notUnique");
		assertThat(genericValidation.getField()).isEqualTo("singleton");

		assertThat(modelBean.getValidations().get(1))
				.isInstanceOf(GenericValidation.class);
		ExclusionValidationArray exclusionValidationArray = (ExclusionValidationArray) modelBean
				.getValidations().get(2);
		assertThat(exclusionValidationArray.getType()).isEqualTo("exclusion");
		assertThat(exclusionValidationArray.getField()).isEqualTo("excluded");

		assertThat(modelBean.getValidations().get(1))
				.isInstanceOf(GenericValidation.class);
		exclusionValidationArray = (ExclusionValidationArray) modelBean.getValidations()
				.get(3);
		assertThat(exclusionValidationArray.getType()).isEqualTo("exclusion");
		assertThat(exclusionValidationArray.getField()).isEqualTo("excluded2");

		assertThat(modelBean.getValidations().get(1))
				.isInstanceOf(GenericValidation.class);
		InclusionValidationArray inclusionValidationArray = (InclusionValidationArray) modelBean
				.getValidations().get(4);
		assertThat(inclusionValidationArray.getType()).isEqualTo("inclusion");
		assertThat(inclusionValidationArray.getField()).isEqualTo("included");

		assertThat(modelBean.getValidations().get(1))
				.isInstanceOf(GenericValidation.class);
		ExclusionValidation exclusionValidation = (ExclusionValidation) modelBean
				.getValidations().get(5);
		assertThat(exclusionValidation.getType()).isEqualTo("exclusion");
		assertThat(exclusionValidation.getField()).isEqualTo("excludedV1");

		assertThat(modelBean.getValidations().get(1))
				.isInstanceOf(GenericValidation.class);
		InclusionValidation inclusionValidation = (InclusionValidation) modelBean
				.getValidations().get(6);
		assertThat(inclusionValidation.getType()).isEqualTo("inclusion");
		assertThat(inclusionValidation.getField()).isEqualTo("includedV1");
	}

	@Test
	public void testGenerateJavascriptModelBeanOutputFormatBoolean() {
		ModelBean model = ModelGenerator.createModel(BeanWithGenericValidation.class,
				IncludeValidation.ALL);
		GeneratorTestUtil.compareExtJs4Code("BeanWithGenericValidation",
				ModelGenerator.generateJavascript(model, OutputFormat.EXTJS4, true), true,
				false);
		GeneratorTestUtil.compareExtJs4Code("BeanWithGenericValidation",
				ModelGenerator.generateJavascript(model, OutputFormat.EXTJS4, false),
				false, false);

		GeneratorTestUtil.compareTouch2Code("BeanWithGenericValidation",
				ModelGenerator.generateJavascript(model, OutputFormat.TOUCH2, true), true,
				false);
		GeneratorTestUtil.compareTouch2Code("BeanWithGenericValidation",
				ModelGenerator.generateJavascript(model, OutputFormat.TOUCH2, false),
				false, false);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLengthValidation() {
		@SuppressWarnings("unused")
		LengthValidation lv = new LengthValidation("name", (Integer) null,
				(Integer) null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRangeValidation() {
		@SuppressWarnings("unused")
		RangeValidation rv = new RangeValidation("name", (Long) null, (Long) null);
	}

}
