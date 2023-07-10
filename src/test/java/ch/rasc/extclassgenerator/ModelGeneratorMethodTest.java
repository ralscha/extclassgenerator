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

import org.junit.Before;
import org.junit.Test;

import ch.rasc.extclassgenerator.bean.UserClass;

public class ModelGeneratorMethodTest {

	@Before
	public void clearCaches() {
		ModelGenerator.clearCaches();
	}

	@Test
	public void testInterfaceExtJs4() {
		ModelBean modelBean = ModelGenerator.createModel(UserClass.class,
				IncludeValidation.ALL);
		OutputConfig outputConfig = new OutputConfig();
		outputConfig.setOutputFormat(OutputFormat.EXTJS4);
		outputConfig.setDebug(false);
		String code = ModelGenerator.generateJavascript(modelBean, outputConfig);

		GeneratorTestUtil.compareExtJs4Code("UserClass", code, false, false);
	}

	@Test
	public void testInterfaceExtJs5() {
		ModelBean modelBean = ModelGenerator.createModel(UserClass.class,
				IncludeValidation.ALL);
		OutputConfig outputConfig = new OutputConfig();
		outputConfig.setOutputFormat(OutputFormat.EXTJS5);
		outputConfig.setDebug(false);
		String code = ModelGenerator.generateJavascript(modelBean, outputConfig);

		GeneratorTestUtil.compareExtJs5Code("UserClass", code, false, false);
	}

	@Test
	public void testInterfaceTouch2() {
		ModelBean modelBean = ModelGenerator.createModel(UserClass.class,
				IncludeValidation.ALL);
		OutputConfig outputConfig = new OutputConfig();
		outputConfig.setOutputFormat(OutputFormat.TOUCH2);
		outputConfig.setDebug(false);
		String code = ModelGenerator.generateJavascript(modelBean, outputConfig);

		GeneratorTestUtil.compareTouch2Code("UserClass", code, false, false);
	}
}
