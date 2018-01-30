/**
 * Copyright 2013-2018 the original author or authors.
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.ReadableDateTime;

import ch.rasc.extclassgenerator.ModelFieldBean;
import ch.rasc.extclassgenerator.ModelType;

public class BeanWithoutAnnotations {

	private byte aByte;

	private short aShort;

	private int aInt;

	private long aLong;

	private Byte aByteObject;

	private Short aShortObject;

	private Integer aIntObject;

	private Long aLongObject;

	private BigDecimal aBigDecimal;

	private BigInteger aBigInteger;

	private Calendar aCalendar;

	private GregorianCalendar aSecondCalendar;

	private float aFloat;

	private double aDouble;

	private Float aFloatObject;

	private Double aDoubleObject;

	private String aString;

	private boolean aBoolean;

	private Boolean aBooleanObject;

	private Date aDate;

	private java.sql.Date aSqlDate;

	private Timestamp aTimestamp;

	private DateTime aDateTime;

	private ReadableDateTime aReadableDateTime;

	private LocalDate aLocalDate;

	private java.time.LocalDate aJava8LocalDate;

	private java.time.LocalDateTime aJava8LocalDateTime;

	private java.time.ZonedDateTime aJava8ZonedDateTime;

	private java.time.OffsetDateTime aJava8OffsetDateTime;

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

	public ReadableDateTime getaReadableDateTime() {
		return this.aReadableDateTime;
	}

	public void setaReadableDateTime(ReadableDateTime aReadableDateTime) {
		this.aReadableDateTime = aReadableDateTime;
	}

	public LocalDate getaLocalDate() {
		return this.aLocalDate;
	}

	public void setaLocalDate(LocalDate aLocalDate) {
		this.aLocalDate = aLocalDate;
	}

	public java.time.LocalDate getaJava8LocalDate() {
		return this.aJava8LocalDate;
	}

	public void setaJava8LocalDate(java.time.LocalDate aJava8LocalDate) {
		this.aJava8LocalDate = aJava8LocalDate;
	}

	public java.time.LocalDateTime getaJava8LocalDateTime() {
		return this.aJava8LocalDateTime;
	}

	public void setaJava8LocalDateTime(java.time.LocalDateTime aJava8LocalDateTime) {
		this.aJava8LocalDateTime = aJava8LocalDateTime;
	}

	public java.time.ZonedDateTime getaJava8ZonedDateTime() {
		return this.aJava8ZonedDateTime;
	}

	public void setaJava8ZonedDateTime(java.time.ZonedDateTime aJava8ZonedDateTime) {
		this.aJava8ZonedDateTime = aJava8ZonedDateTime;
	}

	public java.time.OffsetDateTime getaJava8OffsetDateTime() {
		return this.aJava8OffsetDateTime;
	}

	public void setaJava8OffsetDateTime(java.time.OffsetDateTime aJava8OffsetDateTime) {
		this.aJava8OffsetDateTime = aJava8OffsetDateTime;
	}

	public Calendar getaCalendar() {
		return this.aCalendar;
	}

	public void setaCalendar(Calendar aCalendar) {
		this.aCalendar = aCalendar;
	}

	public GregorianCalendar getaSecondCalendar() {
		return this.aSecondCalendar;
	}

	public void setaSecondCalendar(GregorianCalendar aSecondCalendar) {
		this.aSecondCalendar = aSecondCalendar;
	}

	public static List<ModelFieldBean> expectedFields = new ArrayList<>();

	static {
		expectedFields.add(new ModelFieldBean("aByte", ModelType.INTEGER));
		expectedFields.add(new ModelFieldBean("aShort", ModelType.INTEGER));
		expectedFields.add(new ModelFieldBean("aInt", ModelType.INTEGER));
		expectedFields.add(new ModelFieldBean("aLong", ModelType.INTEGER));
		expectedFields.add(new ModelFieldBean("aByteObject", ModelType.INTEGER));
		expectedFields.add(new ModelFieldBean("aShortObject", ModelType.INTEGER));
		expectedFields.add(new ModelFieldBean("aIntObject", ModelType.INTEGER));
		expectedFields.add(new ModelFieldBean("aLongObject", ModelType.INTEGER));
		expectedFields.add(new ModelFieldBean("aBigDecimal", ModelType.FLOAT));
		expectedFields.add(new ModelFieldBean("aBigInteger", ModelType.INTEGER));
		expectedFields.add(new ModelFieldBean("aCalendar", ModelType.DATE));
		expectedFields.add(new ModelFieldBean("aSecondCalendar", ModelType.DATE));
		expectedFields.add(new ModelFieldBean("aFloat", ModelType.FLOAT));
		expectedFields.add(new ModelFieldBean("aDouble", ModelType.FLOAT));
		expectedFields.add(new ModelFieldBean("aFloatObject", ModelType.FLOAT));
		expectedFields.add(new ModelFieldBean("aDoubleObject", ModelType.FLOAT));
		expectedFields.add(new ModelFieldBean("aString", ModelType.STRING));
		expectedFields.add(new ModelFieldBean("aBoolean", ModelType.BOOLEAN));
		expectedFields.add(new ModelFieldBean("aBooleanObject", ModelType.BOOLEAN));
		expectedFields.add(new ModelFieldBean("aDate", ModelType.DATE));
		expectedFields.add(new ModelFieldBean("aSqlDate", ModelType.DATE));
		expectedFields.add(new ModelFieldBean("aTimestamp", ModelType.DATE));
		expectedFields.add(new ModelFieldBean("aDateTime", ModelType.DATE));
		expectedFields.add(new ModelFieldBean("aReadableDateTime", ModelType.DATE));
		expectedFields.add(new ModelFieldBean("aLocalDate", ModelType.DATE));
		expectedFields.add(new ModelFieldBean("aJava8LocalDate", ModelType.DATE));
		expectedFields.add(new ModelFieldBean("aJava8LocalDateTime", ModelType.DATE));
		expectedFields.add(new ModelFieldBean("aJava8ZonedDateTime", ModelType.DATE));
		expectedFields.add(new ModelFieldBean("aJava8OffsetDateTime", ModelType.DATE));
	}

}
