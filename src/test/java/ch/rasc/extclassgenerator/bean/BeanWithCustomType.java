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
package ch.rasc.extclassgenerator.bean;

import java.util.ArrayList;
import java.util.List;

import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;
import ch.rasc.extclassgenerator.ModelFieldBean;
import ch.rasc.extclassgenerator.ModelType;

@Model(value = "Sch.BeanWithCustomType")
public class BeanWithCustomType {

	private String id;

	@ModelField(type = ModelType.INTEGER)
	private int age;

	@ModelField(type = ModelType.STRING, customType = "city")
	private String city;

	@ModelField(customType = "email")
	private String email;

	@ModelField(customType = "creditcardnumber")
	private String creditcardnumber;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreditcardnumber() {
		return creditcardnumber;
	}

	public void setCreditcardnumber(String creditcardnumber) {
		this.creditcardnumber = creditcardnumber;
	}

	public static List<ModelFieldBean> expectedFields = new ArrayList<ModelFieldBean>();
	static {
		ModelFieldBean field = new ModelFieldBean("id", ModelType.STRING);
		expectedFields.add(field);

		field = new ModelFieldBean("age", ModelType.INTEGER);
		expectedFields.add(field);

		
		field = new ModelFieldBean("city", "city");
		expectedFields.add(field);

		field = new ModelFieldBean("email", "email");
		expectedFields.add(field);

		field = new ModelFieldBean("creditcardnumber", "creditcardnumber");
		expectedFields.add(field);

	}

}
