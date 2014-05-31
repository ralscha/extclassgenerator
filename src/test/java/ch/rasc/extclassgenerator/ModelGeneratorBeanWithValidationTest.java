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

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import ch.rasc.extclassgenerator.bean.BeanWithValidation;
import ch.rasc.extclassgenerator.validation.CreditCardNumberValidation;
import ch.rasc.extclassgenerator.validation.DigitsValidation;
import ch.rasc.extclassgenerator.validation.EmailValidation;
import ch.rasc.extclassgenerator.validation.FormatValidation;
import ch.rasc.extclassgenerator.validation.FutureValidation;
import ch.rasc.extclassgenerator.validation.LengthValidation;
import ch.rasc.extclassgenerator.validation.NotBlankValidation;
import ch.rasc.extclassgenerator.validation.PastValidation;
import ch.rasc.extclassgenerator.validation.PresenceValidation;
import ch.rasc.extclassgenerator.validation.RangeValidation;

public class ModelGeneratorBeanWithValidationTest {

	@Before
	public void clearCaches() {
		ModelGenerator.clearCaches();
	}

	@Test
	public void testWriteModelHttpServletRequestHttpServletResponseClassOfQOutputFormatBoolean()
			throws IOException {
		MockHttpServletResponse response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response,
				BeanWithValidation.class, OutputFormat.EXTJS4,
				IncludeValidation.ALL, true);
		GeneratorTestUtil.compareExtJs4Code("BeanWithValidation",
				response.getContentAsString(), true, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response,
				BeanWithValidation.class, OutputFormat.TOUCH2,
				IncludeValidation.ALL, false);
		GeneratorTestUtil.compareTouch2Code("BeanWithValidation",
				response.getContentAsString(), false, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response,
				BeanWithValidation.class, OutputFormat.EXTJS4,
				IncludeValidation.ALL, true);
		GeneratorTestUtil.compareExtJs4Code("BeanWithValidation",
				response.getContentAsString(), true, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response,
				BeanWithValidation.class, OutputFormat.TOUCH2,
				IncludeValidation.ALL, true);
		GeneratorTestUtil.compareTouch2Code("BeanWithValidation",
				response.getContentAsString(), true, false);
	}

	@Test
	public void testWriteModelHttpServletRequestHttpServletResponseModelBeanOutputFormat()
			throws IOException {
		MockHttpServletResponse response = new MockHttpServletResponse();
		ModelBean model = ModelGenerator.createModel(BeanWithValidation.class,
				IncludeValidation.ALL);
		ModelGenerator.writeModel(new MockHttpServletRequest(), response,
				model, OutputFormat.EXTJS4);
		GeneratorTestUtil.compareExtJs4Code("BeanWithValidation",
				response.getContentAsString(), false, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response,
				model, OutputFormat.TOUCH2);
		GeneratorTestUtil.compareTouch2Code("BeanWithValidation",
				response.getContentAsString(), false, false);
	}

	@Test
	public void testWriteModelHttpServletRequestHttpServletResponseModelBeanOutputFormatBoolean()
			throws IOException {
		MockHttpServletResponse response = new MockHttpServletResponse();
		ModelBean model = ModelGenerator.createModel(BeanWithValidation.class,
				IncludeValidation.ALL);
		ModelGenerator.writeModel(new MockHttpServletRequest(), response,
				model, OutputFormat.EXTJS4, false);
		GeneratorTestUtil.compareExtJs4Code("BeanWithValidation",
				response.getContentAsString(), false, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response,
				model, OutputFormat.TOUCH2, false);
		GeneratorTestUtil.compareTouch2Code("BeanWithValidation",
				response.getContentAsString(), false, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response,
				model, OutputFormat.EXTJS4, true);
		GeneratorTestUtil.compareExtJs4Code("BeanWithValidation",
				response.getContentAsString(), true, false);

		response = new MockHttpServletResponse();
		ModelGenerator.writeModel(new MockHttpServletRequest(), response,
				model, OutputFormat.TOUCH2, true);
		GeneratorTestUtil.compareTouch2Code("BeanWithValidation",
				response.getContentAsString(), true, false);
	}

	@Test
	public void testGenerateJavascriptClassOfQOutputFormatBoolean() {
		GeneratorTestUtil.compareExtJs4Code("BeanWithValidation",
				ModelGenerator.generateJavascript(BeanWithValidation.class,
						OutputFormat.EXTJS4, IncludeValidation.ALL, true),
				true, false);
		GeneratorTestUtil.compareExtJs4Code("BeanWithValidation",
				ModelGenerator.generateJavascript(BeanWithValidation.class,
						OutputFormat.EXTJS4, IncludeValidation.ALL, false),
				false, false);

		GeneratorTestUtil.compareTouch2Code("BeanWithValidation",
				ModelGenerator.generateJavascript(BeanWithValidation.class,
						OutputFormat.TOUCH2, IncludeValidation.ALL, true),
				true, false);
		GeneratorTestUtil.compareTouch2Code("BeanWithValidation",
				ModelGenerator.generateJavascript(BeanWithValidation.class,
						OutputFormat.TOUCH2, IncludeValidation.ALL, false),
				false, false);
	}

	@Test
	public void testCreateModel() {
		ModelBean modelBean = ModelGenerator.createModel(
				BeanWithValidation.class, IncludeValidation.ALL);
		assertThat(modelBean.getReadMethod()).isNull();
		assertThat(modelBean.getCreateMethod()).isNull();
		assertThat(modelBean.getUpdateMethod()).isNull();
		assertThat(modelBean.getDestroyMethod()).isNull();
		assertThat(modelBean.getIdProperty()).isNull();
		assertThat(modelBean.isPaging()).isFalse();
		assertThat(modelBean.getName()).isEqualTo(
				"ch.rasc.extclassgenerator.bean.BeanWithValidation");
		assertThat(modelBean.getFields()).hasSize(10);
		assertThat(BeanWithValidation.expectedFields).hasSize(10);

		for (ModelFieldBean expectedField : BeanWithValidation.expectedFields) {
			ModelFieldBean field = modelBean.getFields().get(
					expectedField.getName());
			assertThat(field).isEqualsToByComparingFields(expectedField);
		}

		assertThat(modelBean.getValidations()).hasSize(13);

		assertThat(modelBean.getValidations().get(0)).isInstanceOf(
				PresenceValidation.class);
		PresenceValidation presenceValidation = (PresenceValidation) modelBean
				.getValidations().get(0);
		assertThat(presenceValidation.getType()).isEqualTo("presence");

		assertThat(modelBean.getValidations().get(1)).isInstanceOf(
				EmailValidation.class);
		EmailValidation emailValidation = (EmailValidation) modelBean
				.getValidations().get(1);
		assertThat(emailValidation.getType()).isEqualTo("email");
		assertThat(emailValidation.getField()).isEqualTo("email");

		assertThat(modelBean.getValidations().get(2)).isInstanceOf(
				FormatValidation.class);
		FormatValidation formatValidation = (FormatValidation) modelBean
				.getValidations().get(2);
		assertThat(formatValidation.getType()).isEqualTo("format");
		assertThat(formatValidation.getField()).isEqualTo("url");
		assertThat(formatValidation.getMatcher()).isEqualTo(
				"/^\\/\\w.*\\.\\w.*/");

		assertThat(modelBean.getValidations().get(3)).isInstanceOf(
				RangeValidation.class);
		RangeValidation rangeValidation = (RangeValidation) modelBean
				.getValidations().get(3);
		assertThat(rangeValidation.getType()).isEqualTo("range");
		assertThat(rangeValidation.getField()).isEqualTo("minMax1");
		assertThat(rangeValidation.getMin()).isNull();
		assertThat(rangeValidation.getMax()).isEqualTo(new BigDecimal("100"));

		assertThat(modelBean.getValidations().get(4)).isInstanceOf(
				RangeValidation.class);
		rangeValidation = (RangeValidation) modelBean.getValidations().get(4);
		assertThat(rangeValidation.getType()).isEqualTo("range");
		assertThat(rangeValidation.getField()).isEqualTo("minMax1");
		assertThat(rangeValidation.getMax()).isNull();
		assertThat(rangeValidation.getMin()).isEqualByComparingTo(
				new BigDecimal("1"));

		assertThat(modelBean.getValidations().get(5)).isInstanceOf(
				RangeValidation.class);
		rangeValidation = (RangeValidation) modelBean.getValidations().get(5);
		assertThat(rangeValidation.getType()).isEqualTo("range");
		assertThat(rangeValidation.getField()).isEqualTo("minMax2");
		assertThat(rangeValidation.getMin()).isNull();
		assertThat(rangeValidation.getMax()).isEqualTo(new BigDecimal(10000));

		assertThat(modelBean.getValidations().get(6)).isInstanceOf(
				RangeValidation.class);
		rangeValidation = (RangeValidation) modelBean.getValidations().get(6);
		assertThat(rangeValidation.getType()).isEqualTo("range");
		assertThat(rangeValidation.getField()).isEqualTo("minMax2");
		assertThat(rangeValidation.getMax()).isNull();
		assertThat(rangeValidation.getMin()).isEqualTo(new BigDecimal(20));

		assertThat(modelBean.getValidations().get(7)).isInstanceOf(
				RangeValidation.class);
		rangeValidation = (RangeValidation) modelBean.getValidations().get(7);
		assertThat(rangeValidation.getType()).isEqualTo("range");
		assertThat(rangeValidation.getField()).isEqualTo("minMax3");
		assertThat(rangeValidation.getMin()).isEqualTo(new BigDecimal(20));
		assertThat(rangeValidation.getMax()).isEqualTo(new BigDecimal(50));

		assertThat(modelBean.getValidations().get(8)).isInstanceOf(
				DigitsValidation.class);
		DigitsValidation digitsValidation = (DigitsValidation) modelBean
				.getValidations().get(8);
		assertThat(digitsValidation.getType()).isEqualTo("digits");
		assertThat(digitsValidation.getField()).isEqualTo("digits");
		assertThat(digitsValidation.getInteger()).isEqualTo(10);
		assertThat(digitsValidation.getFraction()).isEqualTo(2);

		assertThat(modelBean.getValidations().get(9)).isInstanceOf(
				FutureValidation.class);
		assertThat(modelBean.getValidations().get(9).getType()).isEqualTo(
				"future");
		assertThat(modelBean.getValidations().get(9).getField()).isEqualTo(
				"future");

		assertThat(modelBean.getValidations().get(10)).isInstanceOf(
				PastValidation.class);
		assertThat(modelBean.getValidations().get(10).getType()).isEqualTo(
				"past");
		assertThat(modelBean.getValidations().get(10).getField()).isEqualTo(
				"past");

		assertThat(modelBean.getValidations().get(11)).isInstanceOf(
				NotBlankValidation.class);
		assertThat(modelBean.getValidations().get(11).getType()).isEqualTo(
				"notBlank");
		assertThat(modelBean.getValidations().get(11).getField()).isEqualTo(
				"notBlank");

		assertThat(modelBean.getValidations().get(12)).isInstanceOf(
				CreditCardNumberValidation.class);
		assertThat(modelBean.getValidations().get(12).getType()).isEqualTo(
				"creditCardNumber");
		assertThat(modelBean.getValidations().get(12).getField()).isEqualTo(
				"creditCardNumber");

	}

	@Test
	public void testGenerateJavascriptModelBeanOutputFormatBoolean() {
		ModelBean model = ModelGenerator.createModel(BeanWithValidation.class,
				IncludeValidation.ALL);
		GeneratorTestUtil.compareExtJs4Code("BeanWithValidation",
				ModelGenerator.generateJavascript(model, OutputFormat.EXTJS4,
						true), true, false);
		GeneratorTestUtil.compareExtJs4Code("BeanWithValidation",
				ModelGenerator.generateJavascript(model, OutputFormat.EXTJS4,
						false), false, false);

		GeneratorTestUtil.compareTouch2Code("BeanWithValidation",
				ModelGenerator.generateJavascript(model, OutputFormat.TOUCH2,
						true), true, false);
		GeneratorTestUtil.compareTouch2Code("BeanWithValidation",
				ModelGenerator.generateJavascript(model, OutputFormat.TOUCH2,
						false), false, false);
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
		RangeValidation rv = new RangeValidation("name", (Long) null,
				(Long) null);
	}

}
