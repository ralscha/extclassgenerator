/**
 * Copyright 2013-2018 the original author or authors.
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

import org.junit.Before;
import org.junit.Test;

import ch.rasc.extclassgenerator.bean.UserDataOptionsAll;
import ch.rasc.extclassgenerator.bean.UserDataOptionsAllAndPartial;
import ch.rasc.extclassgenerator.bean.UserDataOptionsPartial;

public class ModelGeneratorDataOptionsTest {

	@Before
	public void clearCaches() {
		ModelGenerator.clearCaches();
	}

	@Test
	public void testAllDataOptionsExtJs4() {
		ModelBean modelBean = ModelGenerator.createModel(UserDataOptionsAll.class,
				IncludeValidation.ALL);
		OutputConfig outputConfig = new OutputConfig();
		outputConfig.setOutputFormat(OutputFormat.EXTJS4);
		outputConfig.setDebug(false);
		String code = ModelGenerator.generateJavascript(modelBean, outputConfig);

		GeneratorTestUtil.compareExtJs4Code("UserDataOptionsAll", code, false, false);
	}

	@Test
	public void testAllDataOptionsExtJs5() {
		ModelBean modelBean = ModelGenerator.createModel(UserDataOptionsAll.class,
				IncludeValidation.ALL);
		OutputConfig outputConfig = new OutputConfig();
		outputConfig.setOutputFormat(OutputFormat.EXTJS5);
		outputConfig.setDebug(false);
		String code = ModelGenerator.generateJavascript(modelBean, outputConfig);

		GeneratorTestUtil.compareExtJs5Code("UserDataOptionsAll", code, false, false);
	}

	@Test
	public void testAllDataOptionsTouch2() {
		ModelBean modelBean = ModelGenerator.createModel(UserDataOptionsAll.class,
				IncludeValidation.ALL);
		OutputConfig outputConfig = new OutputConfig();
		outputConfig.setOutputFormat(OutputFormat.TOUCH2);
		outputConfig.setDebug(false);
		String code = ModelGenerator.generateJavascript(modelBean, outputConfig);

		GeneratorTestUtil.compareTouch2Code("UserDataOptionsAll", code, false, false);
	}

	@Test
	public void testPartialDataOptionsExtJs4() {
		ModelBean modelBean = ModelGenerator.createModel(UserDataOptionsPartial.class,
				IncludeValidation.ALL);
		OutputConfig outputConfig = new OutputConfig();
		outputConfig.setOutputFormat(OutputFormat.EXTJS4);
		outputConfig.setDebug(false);
		String code = ModelGenerator.generateJavascript(modelBean, outputConfig);

		GeneratorTestUtil.compareExtJs4Code("UserDataOptionsPartial", code, false, false);
	}

	@Test
	public void testPartialDataOptionsExtJs5() {
		ModelBean modelBean = ModelGenerator.createModel(UserDataOptionsPartial.class,
				IncludeValidation.ALL);
		OutputConfig outputConfig = new OutputConfig();
		outputConfig.setOutputFormat(OutputFormat.EXTJS5);
		outputConfig.setDebug(false);
		String code = ModelGenerator.generateJavascript(modelBean, outputConfig);

		GeneratorTestUtil.compareExtJs5Code("UserDataOptionsPartial", code, false, false);
	}

	@Test
	public void testPartialDataOptionsTouch2() {
		ModelBean modelBean = ModelGenerator.createModel(UserDataOptionsPartial.class,
				IncludeValidation.ALL);
		OutputConfig outputConfig = new OutputConfig();
		outputConfig.setOutputFormat(OutputFormat.TOUCH2);
		outputConfig.setDebug(false);
		String code = ModelGenerator.generateJavascript(modelBean, outputConfig);

		GeneratorTestUtil.compareTouch2Code("UserDataOptionsPartial", code, false, false);
	}

	@Test
	public void testAllAndPartialDataOptionsExtJs4() {
		ModelBean modelBean = ModelGenerator
				.createModel(UserDataOptionsAllAndPartial.class, IncludeValidation.ALL);
		OutputConfig outputConfig = new OutputConfig();
		outputConfig.setOutputFormat(OutputFormat.EXTJS4);
		outputConfig.setDebug(false);
		String code = ModelGenerator.generateJavascript(modelBean, outputConfig);

		GeneratorTestUtil.compareExtJs4Code("UserDataOptionsAllAndPartial", code, false,
				false);
	}

	@Test
	public void testAllAndPartialDataOptionsExtJs5() {
		ModelBean modelBean = ModelGenerator
				.createModel(UserDataOptionsAllAndPartial.class, IncludeValidation.ALL);
		OutputConfig outputConfig = new OutputConfig();
		outputConfig.setOutputFormat(OutputFormat.EXTJS5);
		outputConfig.setDebug(false);
		String code = ModelGenerator.generateJavascript(modelBean, outputConfig);

		GeneratorTestUtil.compareExtJs5Code("UserDataOptionsAllAndPartial", code, false,
				false);
	}

	@Test
	public void testAllAndPartialDataOptionsTouch2() {
		ModelBean modelBean = ModelGenerator
				.createModel(UserDataOptionsAllAndPartial.class, IncludeValidation.ALL);
		OutputConfig outputConfig = new OutputConfig();
		outputConfig.setOutputFormat(OutputFormat.TOUCH2);
		outputConfig.setDebug(false);
		String code = ModelGenerator.generateJavascript(modelBean, outputConfig);

		GeneratorTestUtil.compareTouch2Code("UserDataOptionsAllAndPartial", code, false,
				false);
	}
}
