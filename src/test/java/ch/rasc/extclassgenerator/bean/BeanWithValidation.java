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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Range;

import ch.rasc.extclassgenerator.ModelFieldBean;
import ch.rasc.extclassgenerator.ModelType;
import ch.rasc.extclassgenerator.ModelValidation;
import ch.rasc.extclassgenerator.ModelValidationType;
import ch.rasc.extclassgenerator.ModelValidations;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

@ModelValidations({
		@ModelValidation(propertyName = "email", value = ModelValidationType.PRESENCE) })
public class BeanWithValidation {

	@Email
	public String email;

	@Pattern(regexp = "^/\\w.*\\.\\w.*")
	public String url;

	@DecimalMax("100")
	@DecimalMin("1")
	public BigDecimal minMax1;

	@Max(10000)
	@Min(20)
	public int minMax2;

	@Range(min = 20, max = 50)
	public long minMax3;

	@Digits(integer = 10, fraction = 2)
	public String digits;

	@Future
	public Date future;

	public Date past;

	@NotBlank
	public String notBlank;

	@CreditCardNumber
	public String creditCardNumber;

	@Past
	public Date getPast() {
		return this.past;
	}

	public void setPast(Date past) {
		this.past = past;
	}

	public static List<ModelFieldBean> expectedFields = new ArrayList<>();

	static {

		ModelFieldBean field = new ModelFieldBean("email", ModelType.STRING);
		expectedFields.add(field);

		field = new ModelFieldBean("url", ModelType.STRING);
		expectedFields.add(field);

		field = new ModelFieldBean("minMax1", ModelType.FLOAT);
		expectedFields.add(field);

		field = new ModelFieldBean("minMax2", ModelType.INTEGER);
		expectedFields.add(field);

		field = new ModelFieldBean("minMax3", ModelType.INTEGER);
		expectedFields.add(field);

		field = new ModelFieldBean("digits", ModelType.STRING);
		expectedFields.add(field);

		field = new ModelFieldBean("future", ModelType.DATE);
		expectedFields.add(field);

		field = new ModelFieldBean("past", ModelType.DATE);
		expectedFields.add(field);

		field = new ModelFieldBean("notBlank", ModelType.STRING);
		expectedFields.add(field);

		field = new ModelFieldBean("creditCardNumber", ModelType.STRING);
		expectedFields.add(field);
	}
}
