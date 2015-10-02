/**
 * Copyright 2013-2015 Ralph Schaer <ralphschaer@gmail.com>
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

import org.apache.commons.io.IOUtils;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class GeneratorTestUtil {

	public static void testWriteModel(Class<?> clazz, String modelName) {
		try {
			MockHttpServletResponse response = new MockHttpServletResponse();
			ModelGenerator.writeModel(new MockHttpServletRequest(), response, clazz,
					OutputFormat.EXTJS4, false);
			GeneratorTestUtil.compareExtJs4Code(modelName, response.getContentAsString(),
					false, false);

			response = new MockHttpServletResponse();
			ModelGenerator.writeModel(new MockHttpServletRequest(), response, clazz,
					OutputFormat.EXTJS5, false);
			GeneratorTestUtil.compareExtJs5Code(modelName, response.getContentAsString(),
					false, false);

			response = new MockHttpServletResponse();
			ModelGenerator.writeModel(new MockHttpServletRequest(), response, clazz,
					OutputFormat.TOUCH2, false);
			GeneratorTestUtil.compareTouch2Code(modelName, response.getContentAsString(),
					false, false);

			response = new MockHttpServletResponse();
			ModelGenerator.writeModel(new MockHttpServletRequest(), response, clazz,
					OutputFormat.EXTJS4, IncludeValidation.NONE, false);
			GeneratorTestUtil.compareExtJs4Code(modelName, response.getContentAsString(),
					false, false);

			response = new MockHttpServletResponse();
			ModelGenerator.writeModel(new MockHttpServletRequest(), response, clazz,
					OutputFormat.EXTJS5, IncludeValidation.NONE, false);
			GeneratorTestUtil.compareExtJs5Code(modelName, response.getContentAsString(),
					false, false);

			response = new MockHttpServletResponse();
			ModelGenerator.writeModel(new MockHttpServletRequest(), response, clazz,
					OutputFormat.TOUCH2, IncludeValidation.NONE, false);
			GeneratorTestUtil.compareTouch2Code(modelName, response.getContentAsString(),
					false, false);

			response = new MockHttpServletResponse();
			ModelGenerator.writeModel(new MockHttpServletRequest(), response, clazz,
					OutputFormat.EXTJS4);
			GeneratorTestUtil.compareExtJs4Code(modelName, response.getContentAsString(),
					false, false);

			response = new MockHttpServletResponse();
			ModelGenerator.writeModel(new MockHttpServletRequest(), response, clazz,
					OutputFormat.EXTJS5);
			GeneratorTestUtil.compareExtJs5Code(modelName, response.getContentAsString(),
					false, false);

			response = new MockHttpServletResponse();
			ModelGenerator.writeModel(new MockHttpServletRequest(), response, clazz,
					OutputFormat.TOUCH2);
			GeneratorTestUtil.compareTouch2Code(modelName, response.getContentAsString(),
					false, false);

			response = new MockHttpServletResponse();
			ModelGenerator.writeModel(new MockHttpServletRequest(), response, clazz,
					OutputFormat.EXTJS4, true);
			GeneratorTestUtil.compareExtJs4Code(modelName, response.getContentAsString(),
					true, false);

			response = new MockHttpServletResponse();
			ModelGenerator.writeModel(new MockHttpServletRequest(), response, clazz,
					OutputFormat.EXTJS5, true);
			GeneratorTestUtil.compareExtJs5Code(modelName, response.getContentAsString(),
					true, false);

			response = new MockHttpServletResponse();
			ModelGenerator.writeModel(new MockHttpServletRequest(), response, clazz,
					OutputFormat.TOUCH2, true);
			GeneratorTestUtil.compareTouch2Code(modelName, response.getContentAsString(),
					true, false);

			response = new MockHttpServletResponse();
			ModelGenerator.writeModel(new MockHttpServletRequest(), response, clazz,
					OutputFormat.EXTJS4, IncludeValidation.NONE, true);
			GeneratorTestUtil.compareExtJs4Code(modelName, response.getContentAsString(),
					true, false);

			response = new MockHttpServletResponse();
			ModelGenerator.writeModel(new MockHttpServletRequest(), response, clazz,
					OutputFormat.EXTJS5, IncludeValidation.NONE, true);
			GeneratorTestUtil.compareExtJs5Code(modelName, response.getContentAsString(),
					true, false);

			response = new MockHttpServletResponse();
			ModelGenerator.writeModel(new MockHttpServletRequest(), response, clazz,
					OutputFormat.TOUCH2, IncludeValidation.NONE, true);
			GeneratorTestUtil.compareTouch2Code(modelName, response.getContentAsString(),
					true, false);

		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void testWriteModelBuiltinValidation(Class<?> clazz, String modelName) {
		try {

			MockHttpServletResponse response = new MockHttpServletResponse();
			ModelGenerator.writeModel(new MockHttpServletRequest(), response, clazz,
					OutputFormat.EXTJS4, IncludeValidation.BUILTIN, false);
			GeneratorTestUtil.compareExtJs4Code(modelName, response.getContentAsString(),
					false, false);

			response = new MockHttpServletResponse();
			ModelGenerator.writeModel(new MockHttpServletRequest(), response, clazz,
					OutputFormat.EXTJS5, IncludeValidation.BUILTIN, false);
			GeneratorTestUtil.compareExtJs5Code(modelName, response.getContentAsString(),
					false, false);

			response = new MockHttpServletResponse();
			ModelGenerator.writeModel(new MockHttpServletRequest(), response, clazz,
					OutputFormat.TOUCH2, IncludeValidation.BUILTIN, false);
			GeneratorTestUtil.compareTouch2Code(modelName, response.getContentAsString(),
					false, false);

			response = new MockHttpServletResponse();
			ModelGenerator.writeModel(new MockHttpServletRequest(), response, clazz,
					OutputFormat.EXTJS4, IncludeValidation.BUILTIN, true);
			GeneratorTestUtil.compareExtJs4Code(modelName, response.getContentAsString(),
					true, false);

			response = new MockHttpServletResponse();
			ModelGenerator.writeModel(new MockHttpServletRequest(), response, clazz,
					OutputFormat.EXTJS5, IncludeValidation.BUILTIN, true);
			GeneratorTestUtil.compareExtJs5Code(modelName, response.getContentAsString(),
					true, false);

			response = new MockHttpServletResponse();
			ModelGenerator.writeModel(new MockHttpServletRequest(), response, clazz,
					OutputFormat.TOUCH2, IncludeValidation.BUILTIN, true);
			GeneratorTestUtil.compareTouch2Code(modelName, response.getContentAsString(),
					true, false);

		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void testGenerateJavascript(Class<?> clazz, String modelName,
			OutputFormat outputFormat, boolean debug, boolean surroundApiWithQuotes,
			IncludeValidation includeValidation, boolean useSingleQuote) {
		OutputConfig outputConfig = new OutputConfig();
		outputConfig.setOutputFormat(outputFormat);
		outputConfig.setDebug(debug);
		outputConfig.setSurroundApiWithQuotes(surroundApiWithQuotes);
		outputConfig.setIncludeValidation(includeValidation);
		outputConfig.setUseSingleQuotes(useSingleQuote);
		if (outputFormat == OutputFormat.EXTJS4) {
			GeneratorTestUtil.compareExtJs4Code(modelName,
					ModelGenerator.generateJavascript(clazz, outputConfig), debug,
					surroundApiWithQuotes);
		}
		else if (outputFormat == OutputFormat.EXTJS5) {
			GeneratorTestUtil.compareExtJs5Code(modelName,
					ModelGenerator.generateJavascript(clazz, outputConfig), debug,
					surroundApiWithQuotes);
		}
		else if (outputFormat == OutputFormat.TOUCH2) {
			GeneratorTestUtil.compareTouch2Code(modelName,
					ModelGenerator.generateJavascript(clazz, outputConfig), debug,
					surroundApiWithQuotes);
		}
	}

	public static void testGenerateJavascript(Class<?> clazz, String modelName,
			boolean apiWithQuotes, IncludeValidation includeValidation,
			boolean useSingleQuote) {
		testGenerateJavascript(clazz, modelName, OutputFormat.EXTJS4, true, apiWithQuotes,
				includeValidation, useSingleQuote);
		if (!useSingleQuote) {
			if (includeValidation == IncludeValidation.NONE) {
				GeneratorTestUtil.compareExtJs4Code(modelName, ModelGenerator
						.generateJavascript(clazz, OutputFormat.EXTJS4, true), true,
						false);
			}
			GeneratorTestUtil
					.compareExtJs4Code(
							modelName, ModelGenerator.generateJavascript(clazz,
									OutputFormat.EXTJS4, includeValidation, true),
							true, false);
		}

		testGenerateJavascript(clazz, modelName, OutputFormat.EXTJS4, false,
				apiWithQuotes, includeValidation, useSingleQuote);
		if (!useSingleQuote) {
			if (includeValidation == IncludeValidation.NONE) {
				GeneratorTestUtil.compareExtJs4Code(modelName, ModelGenerator
						.generateJavascript(clazz, OutputFormat.EXTJS4, false), false,
						false);
			}
			GeneratorTestUtil
					.compareExtJs4Code(
							modelName, ModelGenerator.generateJavascript(clazz,
									OutputFormat.EXTJS4, includeValidation, false),
							false, false);
		}

		testGenerateJavascript(clazz, modelName, OutputFormat.EXTJS5, true, apiWithQuotes,
				includeValidation, useSingleQuote);
		if (!useSingleQuote) {
			if (includeValidation == IncludeValidation.NONE) {
				GeneratorTestUtil.compareExtJs5Code(modelName, ModelGenerator
						.generateJavascript(clazz, OutputFormat.EXTJS5, true), true,
						false);
			}
			GeneratorTestUtil
					.compareExtJs5Code(
							modelName, ModelGenerator.generateJavascript(clazz,
									OutputFormat.EXTJS5, includeValidation, true),
							true, false);
		}

		testGenerateJavascript(clazz, modelName, OutputFormat.EXTJS5, false,
				apiWithQuotes, includeValidation, useSingleQuote);
		if (!useSingleQuote) {
			if (includeValidation == IncludeValidation.NONE) {
				GeneratorTestUtil.compareExtJs5Code(modelName, ModelGenerator
						.generateJavascript(clazz, OutputFormat.EXTJS5, false), false,
						false);
			}
			GeneratorTestUtil
					.compareExtJs5Code(
							modelName, ModelGenerator.generateJavascript(clazz,
									OutputFormat.EXTJS5, includeValidation, false),
							false, false);
		}

		testGenerateJavascript(clazz, modelName, OutputFormat.TOUCH2, true, apiWithQuotes,
				includeValidation, useSingleQuote);
		if (!useSingleQuote) {
			if (includeValidation == IncludeValidation.NONE) {
				GeneratorTestUtil.compareTouch2Code(modelName, ModelGenerator
						.generateJavascript(clazz, OutputFormat.TOUCH2, true), true,
						false);
			}
			GeneratorTestUtil
					.compareTouch2Code(
							modelName, ModelGenerator.generateJavascript(clazz,
									OutputFormat.TOUCH2, includeValidation, true),
							true, false);
		}

		testGenerateJavascript(clazz, modelName, OutputFormat.TOUCH2, false,
				apiWithQuotes, includeValidation, useSingleQuote);
		if (!useSingleQuote) {
			if (includeValidation == IncludeValidation.NONE) {
				GeneratorTestUtil.compareTouch2Code(modelName, ModelGenerator
						.generateJavascript(clazz, OutputFormat.TOUCH2, false), false,
						false);
			}
			GeneratorTestUtil
					.compareTouch2Code(
							modelName, ModelGenerator.generateJavascript(clazz,
									OutputFormat.TOUCH2, includeValidation, false),
							false, false);
		}
	}

	private static void compareModelString(String expectedValue, String value,
			boolean debug) {
		if (debug) {
			assertThat(value.replaceAll("\\r?\\n", "\n"))
					.isEqualTo(expectedValue.replaceAll("\\r?\\n", "\n"));
		}
		else {
			assertThat(value.replace(" ", ""))
					.isEqualTo(expectedValue.replaceAll("\\r?\\n", "").replace(" ", ""));
		}
	}

	public static void compareExtJs4Code(String model, String value, boolean debug,
			boolean apiWithQuotes) {
		try {
			String expectedValue = IOUtils
					.toString(GeneratorTestUtil.class.getResourceAsStream("/generator/"
							+ model + "ExtJs4" + (apiWithQuotes ? "Q" : "") + ".json"));
			compareModelString(expectedValue, value, debug);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void compareExtJs5Code(String model, String value, boolean debug,
			boolean apiWithQuotes) {
		try {
			String expectedValue = IOUtils
					.toString(GeneratorTestUtil.class.getResourceAsStream("/generator/"
							+ model + "ExtJs5" + (apiWithQuotes ? "Q" : "") + ".json"));
			compareModelString(expectedValue, value, debug);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void compareTouch2Code(String model, String value, boolean debug,
			boolean apiWithQuotes) {
		try {
			String expectedValue = IOUtils
					.toString(GeneratorTestUtil.class.getResourceAsStream("/generator/"
							+ model + "Touch2" + (apiWithQuotes ? "Q" : "") + ".json"));
			compareModelString(expectedValue, value, debug);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
