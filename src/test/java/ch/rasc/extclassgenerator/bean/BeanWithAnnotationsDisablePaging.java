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
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;
import ch.rasc.extclassgenerator.ModelFieldBean;
import ch.rasc.extclassgenerator.ModelType;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Model(value = "Sch.Bean2", idProperty = "id", paging = false, readMethod = "read",
		messageProperty = "theMessageProperty", disablePagingParameters = true)
public class BeanWithAnnotationsDisablePaging extends Base {

	@Pattern(regexp = "[a-zA-Z]*")
	private String name;

	@ModelField(dateFormat = "c")
	private Date dob;

	@JsonIgnore
	private String password;

	@Size(max = 10, min = 2)
	private String accountNo;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public String getAccountNo() {
		return this.accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public static List<ModelFieldBean> expectedFields = new ArrayList<>();

	static {

		ModelFieldBean field = new ModelFieldBean("id", ModelType.INTEGER);
		expectedFields.add(field);

		field = new ModelFieldBean("name", ModelType.STRING);
		expectedFields.add(field);

		field = new ModelFieldBean("dob", ModelType.DATE);
		field.setDateFormat("c");
		expectedFields.add(field);

	}

}
