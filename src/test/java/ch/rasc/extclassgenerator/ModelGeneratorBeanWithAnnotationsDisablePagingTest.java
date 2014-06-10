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

import ch.rasc.extclassgenerator.bean.BeanWithAnnotationsDisablePaging;

public class ModelGeneratorBeanWithAnnotationsDisablePagingTest {

	@Before
	public void clearCaches() {
		ModelGenerator.clearCaches();
	}

	@Test
	public void testWithQuotes() {
		GeneratorTestUtil.testGenerateJavascript(BeanWithAnnotationsDisablePaging.class,
				"BeanWithAnnotationsDisablePaging", true, IncludeValidation.NONE, false);
	}

	@Test
	public void testWithoutQuotes() {
		GeneratorTestUtil.testWriteModel(BeanWithAnnotationsDisablePaging.class,
				"BeanWithAnnotationsDisablePaging");
		GeneratorTestUtil.testGenerateJavascript(BeanWithAnnotationsDisablePaging.class,
				"BeanWithAnnotationsDisablePaging", false, IncludeValidation.NONE, false);
	}

	@Test
	public void testCreateModel() {
		ModelBean modelBean = ModelGenerator
				.createModel(BeanWithAnnotationsDisablePaging.class);
		assertThat(modelBean.getReadMethod()).isEqualTo("read");
		assertThat(modelBean.getCreateMethod()).isNull();
		assertThat(modelBean.getUpdateMethod()).isNull();
		assertThat(modelBean.getDestroyMethod()).isNull();
		assertThat(modelBean.getIdProperty()).isEqualTo("id");
		assertThat(modelBean.isDisablePagingParameters()).isTrue();
		assertThat(modelBean.isPaging()).isFalse();
		assertThat(modelBean.getMessageProperty()).isEqualTo("theMessageProperty");
		assertThat(modelBean.getName()).isEqualTo("Sch.Bean2");
		assertThat(modelBean.getFields()).hasSize(3);
		assertThat(BeanWithAnnotationsDisablePaging.expectedFields).hasSize(3);

		for (ModelFieldBean expectedField : BeanWithAnnotationsDisablePaging.expectedFields) {
			ModelFieldBean field = modelBean.getFields().get(expectedField.getName());
			assertThat(field).isEqualsToByComparingFields(expectedField);
		}
	}

}
