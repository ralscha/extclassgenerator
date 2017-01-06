/**
 * Copyright 2013-2017 Ralph Schaer <ralphschaer@gmail.com>
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
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;
import ch.rasc.extclassgenerator.ModelFieldBean;
import ch.rasc.extclassgenerator.ModelType;

@Model(value = "Sch.Bean", idProperty = "aInt", paging = true, readMethod = "read",
		createMethod = "create", updateMethod = "update", destroyMethod = "destroy")
public class BeanWithAnnotations {

	@ModelField("by")
	private byte aByte;

	private short aShort;

	private int aInt;

	@ModelField(value = "lo", defaultValue = "-1")
	private long aLong;

	@ModelField(type = ModelType.STRING)
	private Byte aByteObject;

	@ModelField(type = ModelType.FLOAT, allowNull = false)
	private Short aShortObject;

	@ModelField(useNull = false)
	private Integer aIntObject;

	@ModelField(useNull = true)
	private Long aLongObject;

	private BigDecimal aBigDecimal;

	@ModelField(defaultValue = "1")
	@NotNull
	@DecimalMax("500000")
	private BigInteger aBigInteger;

	@ModelField(defaultValue = "1.1")
	private float aFloat;

	@ModelField(dateFormat = "c")
	@NotEmpty
	private double aDouble;

	private Float aFloatObject;

	private Double aDoubleObject;

	@ModelField(allowNull = true)
	@Email
	@Length(max = 255)
	@Pattern(regexp = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]")
	private String aString;

	@ModelField(defaultValue = "true")
	private boolean aBoolean;

	@ModelField(defaultValue = "false")
	private Boolean aBooleanObject;

	@ModelField(dateFormat = "c")
	@Future
	private Date aDate;

	private java.sql.Date aSqlDate;

	private Timestamp aTimestamp;

	@ModelField(dateFormat = "d.m.y", useNull = true)
	private DateTime aDateTime;

	private LocalDate aLocalDate;

	// virtual field
	@ModelField(persist = true)
	private BigInteger bigValue;

	@ModelField(critical = false)
	private String aNonCriticalValue;

	@ModelField(critical = true)
	private String aCriticalValue;

	// or @ModelField(mapping="bigValue", persist=false,
	// convert="new Function('v', 'record', return (record.raw.bigValue > 1000000);)")
	@ModelField(mapping = "bigValue", persist = false,
			depends = { "lastName", "firstName" },
			convert = "function(v, record) { return (record.raw.bigValue > 1000000);}")
	private boolean aBooleanVirtual;

	@ModelField(calculate = "function(data) { return 'CALC:' + data.aString; }")
	private String calculatedValue;

	@ModelField(type = ModelType.AUTO)
	private List<Integer> someIds;

	@ModelField
	private Set<Long> moreIds;

	public byte getaByte() {
		return this.aByte;
	}

	public void setaByte(byte aByte) {
		this.aByte = aByte;
	}

	public short getaShort() {
		return this.aShort;
	}

	public void setaShort(short aShort) {
		this.aShort = aShort;
	}

	public int getaInt() {
		return this.aInt;
	}

	public void setaInt(int aInt) {
		this.aInt = aInt;
	}

	public long getaLong() {
		return this.aLong;
	}

	public void setaLong(long aLong) {
		this.aLong = aLong;
	}

	public Byte getaByteObject() {
		return this.aByteObject;
	}

	public void setaByteObject(Byte aByteObject) {
		this.aByteObject = aByteObject;
	}

	public Short getaShortObject() {
		return this.aShortObject;
	}

	public void setaShortObject(Short aShortObject) {
		this.aShortObject = aShortObject;
	}

	public Integer getaIntObject() {
		return this.aIntObject;
	}

	public void setaIntObject(Integer aIntObject) {
		this.aIntObject = aIntObject;
	}

	public Long getaLongObject() {
		return this.aLongObject;
	}

	public void setaLongObject(Long aLongObject) {
		this.aLongObject = aLongObject;
	}

	public BigDecimal getaBigDecimal() {
		return this.aBigDecimal;
	}

	public void setaBigDecimal(BigDecimal aBigDecimal) {
		this.aBigDecimal = aBigDecimal;
	}

	public BigInteger getaBigInteger() {
		return this.aBigInteger;
	}

	public void setaBigInteger(BigInteger aBigInteger) {
		this.aBigInteger = aBigInteger;
	}

	public float getaFloat() {
		return this.aFloat;
	}

	public void setaFloat(float aFloat) {
		this.aFloat = aFloat;
	}

	public double getaDouble() {
		return this.aDouble;
	}

	public void setaDouble(double aDouble) {
		this.aDouble = aDouble;
	}

	public Float getaFloatObject() {
		return this.aFloatObject;
	}

	public void setaFloatObject(Float aFloatObject) {
		this.aFloatObject = aFloatObject;
	}

	public Double getaDoubleObject() {
		return this.aDoubleObject;
	}

	public void setaDoubleObject(Double aDoubleObject) {
		this.aDoubleObject = aDoubleObject;
	}

	public String getaString() {
		return this.aString;
	}

	public void setaString(String aString) {
		this.aString = aString;
	}

	public boolean isaBoolean() {
		return this.aBoolean;
	}

	public void setaBoolean(boolean aBoolean) {
		this.aBoolean = aBoolean;
	}

	public Boolean getaBooleanObject() {
		return this.aBooleanObject;
	}

	public void setaBooleanObject(Boolean aBooleanObject) {
		this.aBooleanObject = aBooleanObject;
	}

	public Date getaDate() {
		return this.aDate;
	}

	public void setaDate(Date aDate) {
		this.aDate = aDate;
	}

	public java.sql.Date getaSqlDate() {
		return this.aSqlDate;
	}

	public void setaSqlDate(java.sql.Date aSqlDate) {
		this.aSqlDate = aSqlDate;
	}

	public Timestamp getaTimestamp() {
		return this.aTimestamp;
	}

	public void setaTimestamp(Timestamp aTimestamp) {
		this.aTimestamp = aTimestamp;
	}

	public DateTime getaDateTime() {
		return this.aDateTime;
	}

	public void setaDateTime(DateTime aDateTime) {
		this.aDateTime = aDateTime;
	}

	public LocalDate getaLocalDate() {
		return this.aLocalDate;
	}

	public void setaLocalDate(LocalDate aLocalDate) {
		this.aLocalDate = aLocalDate;
	}

	public BigInteger getBigValue() {
		return this.bigValue;
	}

	public void setBigValue(BigInteger bigValue) {
		this.bigValue = bigValue;
	}

	public boolean getaBooleanVirtual() {
		return this.aBooleanVirtual;
	}

	public void setaBooleanVirtual(boolean aBooleanVirtual) {
		this.aBooleanVirtual = aBooleanVirtual;
	}

	public List<Integer> getSomeIds() {
		return this.someIds;
	}

	public void setSomeIds(List<Integer> someIds) {
		this.someIds = someIds;
	}

	public Set<Long> getMoreIds() {
		return this.moreIds;
	}

	public void setMoreIds(Set<Long> moreIds) {
		this.moreIds = moreIds;
	}

	public static List<ModelFieldBean> expectedFields = new ArrayList<>();

	static {

		ModelFieldBean field = new ModelFieldBean("by", ModelType.INTEGER);
		expectedFields.add(field);

		field = new ModelFieldBean("aShort", ModelType.INTEGER);
		expectedFields.add(field);

		field = new ModelFieldBean("aInt", ModelType.INTEGER);
		expectedFields.add(field);

		field = new ModelFieldBean("lo", ModelType.INTEGER);
		field.setDefaultValue(-1l);
		expectedFields.add(field);

		field = new ModelFieldBean("aByteObject", ModelType.STRING);
		expectedFields.add(field);

		field = new ModelFieldBean("aShortObject", ModelType.FLOAT);
		expectedFields.add(field);

		field = new ModelFieldBean("aIntObject", ModelType.INTEGER);
		expectedFields.add(field);

		field = new ModelFieldBean("aLongObject", ModelType.INTEGER);
		field.setUseNull(Boolean.TRUE);
		expectedFields.add(field);

		field = new ModelFieldBean("aBigDecimal", ModelType.FLOAT);
		expectedFields.add(field);

		field = new ModelFieldBean("aBigInteger", ModelType.INTEGER);
		field.setDefaultValue(1l);
		expectedFields.add(field);

		field = new ModelFieldBean("aFloat", ModelType.FLOAT);
		field.setDefaultValue(1.1);
		expectedFields.add(field);

		field = new ModelFieldBean("aDouble", ModelType.FLOAT);
		expectedFields.add(field);

		field = new ModelFieldBean("aFloatObject", ModelType.FLOAT);
		expectedFields.add(field);

		field = new ModelFieldBean("aDoubleObject", ModelType.FLOAT);
		expectedFields.add(field);

		field = new ModelFieldBean("aString", ModelType.STRING);
		field.setUseNull(Boolean.TRUE);
		expectedFields.add(field);

		field = new ModelFieldBean("aBoolean", ModelType.BOOLEAN);
		field.setDefaultValue(Boolean.TRUE);
		expectedFields.add(field);

		field = new ModelFieldBean("aBooleanObject", ModelType.BOOLEAN);
		field.setDefaultValue(Boolean.FALSE);
		expectedFields.add(field);

		field = new ModelFieldBean("aDate", ModelType.DATE);
		field.setDateFormat("c");
		expectedFields.add(field);

		field = new ModelFieldBean("aSqlDate", ModelType.DATE);
		expectedFields.add(field);

		field = new ModelFieldBean("aTimestamp", ModelType.DATE);
		expectedFields.add(field);

		field = new ModelFieldBean("aDateTime", ModelType.DATE);
		field.setDateFormat("d.m.y");
		expectedFields.add(field);

		field = new ModelFieldBean("aLocalDate", ModelType.DATE);
		expectedFields.add(field);

		field = new ModelFieldBean("bigValue", ModelType.INTEGER);
		expectedFields.add(field);

		field = new ModelFieldBean("aNonCriticalValue", ModelType.STRING);
		expectedFields.add(field);

		field = new ModelFieldBean("aCriticalValue", ModelType.STRING);
		field.setCritical(Boolean.TRUE);
		expectedFields.add(field);

		field = new ModelFieldBean("aBooleanVirtual", ModelType.BOOLEAN);
		field.setMapping("bigValue");
		field.setPersist(Boolean.FALSE);
		field.setConvert(
				"function(v, record) { return (record.raw.bigValue > 1000000);}");
		List<String> depends = new ArrayList<>();
		depends.add("lastName");
		depends.add("firstName");
		field.setDepends(depends);
		expectedFields.add(field);

		field = new ModelFieldBean("calculatedValue", ModelType.STRING);
		field.setCalculate("function(data) { return 'CALC:' + data.aString; }");
		expectedFields.add(field);

		field = new ModelFieldBean("someIds", ModelType.AUTO);
		expectedFields.add(field);

		field = new ModelFieldBean("moreIds", ModelType.AUTO);
		expectedFields.add(field);
	}
}
