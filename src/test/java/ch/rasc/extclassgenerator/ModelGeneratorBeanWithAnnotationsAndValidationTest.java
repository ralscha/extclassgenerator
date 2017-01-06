/**
 * Copyright 2013-2017 Ralph Schaer <ralphschaer@gmail.com>
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

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import ch.rasc.extclassgenerator.bean.BeanWithAnnotations;
import ch.rasc.extclassgenerator.validation.EmailValidation;
import ch.rasc.extclassgenerator.validation.FormatValidation;
import ch.rasc.extclassgenerator.validation.LengthValidation;
import ch.rasc.extclassgenerator.validation.PresenceValidation;
import ch.rasc.extclassgenerator.validation.RangeValidation;

public class ModelGeneratorBeanWithAnnotationsAndValidationTest {

	@Before
	public void clearCaches() {
		ModelGenerator.clearCaches();
	}

	@Test
	public void testWithQuotes() {
		GeneratorTestUtil.testGenerateJavascript(BeanWithAnnotations.class,
				"BeanWithAnnotationsValidation", true, IncludeValidation.BUILTIN, false);
	}

	@Test
	public void testWithoutQuotes() {
		GeneratorTestUtil.testWriteModelBuiltinValidation(BeanWithAnnotations.class,
				"BeanWithAnnotationsValidation");
		GeneratorTestUtil.testGenerateJavascript(BeanWithAnnotations.class,
				"BeanWithAnnotationsValidation", false, IncludeValidation.BUILTIN, false);
	}

	@Test
	public void testCreateModel() {
		OutputConfig oc = new OutputConfig();
		oc.setIncludeValidation(IncludeValidation.BUILTIN);
		oc.setOutputFormat(OutputFormat.EXTJS5);
		ModelBean modelBean = ModelGenerator.createModel(BeanWithAnnotations.class, oc);

		assertThat(modelBean.getReadMethod()).isEqualTo("read");
		assertThat(modelBean.getCreateMethod()).isEqualTo("create");
		assertThat(modelBean.getUpdateMethod()).isEqualTo("update");
		assertThat(modelBean.getDestroyMethod()).isEqualTo("destroy");
		assertThat(modelBean.getIdProperty()).isEqualTo("aInt");
		assertThat(modelBean.getVersionProperty()).isNull();
		assertThat(modelBean.isPaging()).isTrue();
		assertThat(modelBean.getName()).isEqualTo("Sch.Bean");
		assertThat(modelBean.getFields()).hasSize(29);
		assertThat(BeanWithAnnotations.expectedFields).hasSize(29);

		for (ModelFieldBean expectedField : BeanWithAnnotations.expectedFields) {
			ModelFieldBean field = modelBean.getFields().get(expectedField.getName());
			assertThat(field).isEqualToComparingFieldByField(expectedField);
		}

		assertThat(modelBean.getValidations()).hasSize(6);
		assertThat(modelBean.getValidations().get(0))
				.isInstanceOf(PresenceValidation.class);
		assertThat(modelBean.getValidations().get(0).getType()).isEqualTo("presence");
		assertThat(modelBean.getValidations().get(0).getField()).isEqualTo("aBigInteger");

		assertThat(modelBean.getValidations().get(1)).isInstanceOf(RangeValidation.class);
		assertThat(modelBean.getValidations().get(1).getType()).isEqualTo("range");
		assertThat(modelBean.getValidations().get(1).getField()).isEqualTo("aBigInteger");
		RangeValidation rv = (RangeValidation) modelBean.getValidations().get(1);
		assertThat(rv.getMin()).isNull();
		assertThat(rv.getMax()).isEqualTo(new BigDecimal("500000"));

		assertThat(modelBean.getValidations().get(2))
				.isInstanceOf(PresenceValidation.class);
		assertThat(modelBean.getValidations().get(2).getType()).isEqualTo("presence");
		assertThat(modelBean.getValidations().get(2).getField()).isEqualTo("aDouble");

		assertThat(modelBean.getValidations().get(3)).isInstanceOf(EmailValidation.class);
		assertThat(modelBean.getValidations().get(3).getType()).isEqualTo("email");
		assertThat(modelBean.getValidations().get(3).getField()).isEqualTo("aString");

		assertThat(modelBean.getValidations().get(4))
				.isInstanceOf(LengthValidation.class);
		LengthValidation lengthValidation = (LengthValidation) modelBean.getValidations()
				.get(4);
		assertThat(lengthValidation.getType()).isEqualTo("length");
		assertThat(lengthValidation.getField()).isEqualTo("aString");
		assertThat(lengthValidation.getMax()).isEqualTo(255L);

		assertThat(modelBean.getValidations().get(5))
				.isInstanceOf(FormatValidation.class);
		FormatValidation formatValidation = (FormatValidation) modelBean.getValidations()
				.get(5);
		assertThat(formatValidation.getType()).isEqualTo("format");
		assertThat(formatValidation.getField()).isEqualTo("aString");
		assertThat(formatValidation.getMatcher())
				.isEqualTo("/\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]/");
	}

}
