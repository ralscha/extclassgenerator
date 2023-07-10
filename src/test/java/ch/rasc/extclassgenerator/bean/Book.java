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
package ch.rasc.extclassgenerator.bean;

import java.util.List;

import org.joda.time.LocalDate;

import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelAssociation;
import ch.rasc.extclassgenerator.ModelAssociationType;
import ch.rasc.extclassgenerator.ModelClientId;
import ch.rasc.extclassgenerator.ModelField;
import ch.rasc.extclassgenerator.ModelFields;
import ch.rasc.extclassgenerator.ModelId;
import ch.rasc.extclassgenerator.ModelType;
import ch.rasc.extclassgenerator.ModelVersion;

@Model(value = "MyApp.Book", idProperty = "isbn1", versionProperty = "version1",
		clientIdProperty = "clientId1", identifier = "uuid")
@ModelFields({ @ModelField(value = "additionalProperty1", type = ModelType.INTEGER),
		@ModelField(value = "additionalProperty2", type = ModelType.STRING) })
public class Book {

	@ModelId
	public String isbn2;

	@ModelVersion
	public String version2;

	@ModelClientId
	public String clientId2;

	public String title;

	public String publisher;

	public String isbn;

	@ModelField(dateFormat = "d-m-Y")
	public LocalDate publishDate;

	public int numberOfPages;

	public boolean read;

	@ModelAssociation(value = ModelAssociationType.HAS_MANY, model = Author.class,
			autoLoad = true)
	public List<Author> authors;

}
