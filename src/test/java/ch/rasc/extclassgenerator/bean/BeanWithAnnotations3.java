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

import java.util.ArrayList;
import java.util.List;

import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelFieldBean;
import ch.rasc.extclassgenerator.ModelType;

@Model(value = "Sch.Bean3", idProperty = "id", readMethod = "read",
		messageProperty = "theMessageProperty", rootProperty = "theRootProperty",
		successProperty = "theSuccessProperty", totalProperty = "theTotalProperty")
public class BeanWithAnnotations3 {

	private String id;

	private String name;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static List<ModelFieldBean> expectedFields = new ArrayList<>();

	static {
		ModelFieldBean field = new ModelFieldBean("id", ModelType.STRING);
		expectedFields.add(field);

		field = new ModelFieldBean("name", ModelType.STRING);
		expectedFields.add(field);
	}

}
