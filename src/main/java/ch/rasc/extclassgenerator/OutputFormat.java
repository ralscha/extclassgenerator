/*
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

/**
 * Enumeration of all possible output formats for the model generator
 */
public enum OutputFormat {
	/**
	 * Instructs the model generator to create Ext JS 4 compatible code.
	 */
	EXTJS4,

	/**
	 * Instructs the model generator to create Ext JS 5 compatible code.
	 */
	EXTJS5,

	/**
	 * Instructs the model generator to create Sencha Touch 2 compatible code.
	 */
	TOUCH2
}
