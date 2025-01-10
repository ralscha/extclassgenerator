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
package ch.rasc.extclassgenerator.bean;

import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelAssociation;
import ch.rasc.extclassgenerator.ModelAssociationType;
import ch.rasc.extclassgenerator.ModelClientId;
import ch.rasc.extclassgenerator.ModelField;
import ch.rasc.extclassgenerator.ModelId;
import ch.rasc.extclassgenerator.ModelVersion;

@Model(extend = "MyApp.model.Base", value = "MyApp.Author", identifier = "sequential")
public class Author {

	@ModelId
	public String id;

	@ModelClientId
	public String clientId;

	@ModelVersion
	public String version;

	@ModelField(defaultValue = "Mr.")
	public String title;

	@ModelField(defaultValue = ModelField.DEFAULTVALUE_UNDEFINED)
	public String firstName;

	@ModelField(convert = "null")
	public String lastName;

	public int book_id;

	@ModelAssociation(value = ModelAssociationType.BELONGS_TO)
	public Book book;

}
