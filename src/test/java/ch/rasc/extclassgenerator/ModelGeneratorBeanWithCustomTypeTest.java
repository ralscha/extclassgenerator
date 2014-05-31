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

import ch.rasc.extclassgenerator.bean.BeanWithCustomType;

public class ModelGeneratorBeanWithCustomTypeTest {

	@Before
	public void clearCaches() {
		ModelGenerator.clearCaches();
	}

	@Test
	public void testWithQuotes() {
		GeneratorTestUtil.testGenerateJavascript(BeanWithCustomType.class,
				"BeanWithCustomType", true, IncludeValidation.NONE, false);
	}

	@Test
	public void testWithoutQuotes() {
		GeneratorTestUtil.testWriteModel(BeanWithCustomType.class,
				"BeanWithCustomType");
		GeneratorTestUtil.testGenerateJavascript(BeanWithCustomType.class,
				"BeanWithCustomType", false, IncludeValidation.NONE, false);
	}

	@Test
	public void testCreateModel() {
		ModelBean modelBean = ModelGenerator
				.createModel(BeanWithCustomType.class);
		assertThat(modelBean.getReadMethod()).isNull();
		assertThat(modelBean.getCreateMethod()).isNull();
		assertThat(modelBean.getUpdateMethod()).isNull();
		assertThat(modelBean.getDestroyMethod()).isNull();
		assertThat(modelBean.getIdProperty()).isEqualTo("id");
		assertThat(modelBean.isDisablePagingParameters()).isFalse();
		assertThat(modelBean.isPaging()).isFalse();
		assertThat(modelBean.getMessageProperty()).isNull();
		assertThat(modelBean.getRootProperty()).isNull();
		assertThat(modelBean.getTotalProperty()).isNull();
		assertThat(modelBean.getSuccessProperty()).isNull();
		assertThat(modelBean.getName()).isEqualTo("Sch.BeanWithCustomType");
		assertThat(modelBean.getFields()).hasSize(5);
		assertThat(BeanWithCustomType.expectedFields).hasSize(5);

		for (ModelFieldBean expectedField : BeanWithCustomType.expectedFields) {
			ModelFieldBean field = modelBean.getFields().get(
					expectedField.getName());
			assertThat(field).isEqualsToByComparingFields(expectedField);
		}
	}
}
