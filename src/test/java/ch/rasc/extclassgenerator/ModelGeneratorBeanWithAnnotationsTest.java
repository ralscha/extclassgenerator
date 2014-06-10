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

import org.junit.Before;
import org.junit.Test;

import ch.rasc.extclassgenerator.bean.BeanWithAnnotations;

public class ModelGeneratorBeanWithAnnotationsTest {

	@Before
	public void clearCaches() {
		ModelGenerator.clearCaches();
	}

	@Test
	public void testWithQuotes() {
		GeneratorTestUtil.testGenerateJavascript(BeanWithAnnotations.class,
				"BeanWithAnnotations", true, IncludeValidation.NONE, false);
	}

	@Test
	public void testWithoutQuotes() {
		GeneratorTestUtil
				.testWriteModel(BeanWithAnnotations.class, "BeanWithAnnotations");
		GeneratorTestUtil.testGenerateJavascript(BeanWithAnnotations.class,
				"BeanWithAnnotations", false, IncludeValidation.NONE, false);
	}

	@Test
	public void testCreateModel() {
		ModelBean modelBean = ModelGenerator.createModel(BeanWithAnnotations.class);
		assertThat(modelBean.getReadMethod()).isEqualTo("read");
		assertThat(modelBean.getCreateMethod()).isEqualTo("create");
		assertThat(modelBean.getUpdateMethod()).isEqualTo("update");
		assertThat(modelBean.getDestroyMethod()).isEqualTo("destroy");
		assertThat(modelBean.getIdProperty()).isEqualTo("aInt");
		assertThat(modelBean.isPaging()).isTrue();
		assertThat(modelBean.getName()).isEqualTo("Sch.Bean");
		assertThat(modelBean.getFields()).hasSize(26);
		assertThat(BeanWithAnnotations.expectedFields).hasSize(26);
		assertThat(modelBean.getValidations()).isEmpty();

		for (ModelFieldBean expectedField : BeanWithAnnotations.expectedFields) {
			ModelFieldBean field = modelBean.getFields().get(expectedField.getName());
			assertThat(field).isEqualsToByComparingFields(expectedField);
		}
	}

}
