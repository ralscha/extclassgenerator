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

import org.junit.Before;
import org.junit.Test;

import ch.rasc.extclassgenerator.bean.BeanWithAnnotations2;

public class ModelGeneratorBeanWithAnnotations2Test {

	@Before
	public void clearCaches() {
		ModelGenerator.clearCaches();
	}

	@Test
	public void testWithQuotes() {
		GeneratorTestUtil.testGenerateJavascript(BeanWithAnnotations2.class,
				"BeanWithAnnotations2", true, IncludeValidation.NONE, false);
	}

	@Test
	public void testWithoutQuotes() {
		GeneratorTestUtil.testWriteModel(BeanWithAnnotations2.class,
				"BeanWithAnnotations2");
		GeneratorTestUtil.testGenerateJavascript(BeanWithAnnotations2.class,
				"BeanWithAnnotations2", false, IncludeValidation.NONE, false);
	}

	@Test
	public void testCreateModel() {
		ModelBean modelBean = ModelGenerator.createModel(BeanWithAnnotations2.class);
		assertThat(modelBean.getReadMethod()).isEqualTo("read");
		assertThat(modelBean.getCreateMethod()).isNull();
		assertThat(modelBean.getUpdateMethod()).isNull();
		assertThat(modelBean.getDestroyMethod()).isNull();
		assertThat(modelBean.getIdProperty()).isEqualTo("id");
		assertThat(modelBean.getVersionProperty()).isNull();
		assertThat(modelBean.isDisablePagingParameters()).isFalse();
		assertThat(modelBean.isPaging()).isFalse();
		assertThat(modelBean.getMessageProperty()).isEqualTo("theMessageProperty");
		assertThat(modelBean.getName()).isEqualTo("Sch.Bean2");
		assertThat(modelBean.getFields()).hasSize(3);
		assertThat(BeanWithAnnotations2.expectedFields).hasSize(3);

		for (ModelFieldBean expectedField : BeanWithAnnotations2.expectedFields) {
			ModelFieldBean field = modelBean.getFields().get(expectedField.getName());
			assertThat(field).isEqualToComparingFieldByField(expectedField);
		}
	}

}
