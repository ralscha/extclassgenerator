/**
 * Copyright 2013-2017 the original author or authors.
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
import ch.rasc.extclassgenerator.ModelField;
import ch.rasc.extclassgenerator.ModelId;
import ch.rasc.extclassgenerator.ReferenceConfig;

@Model(extend = "MyApp.model.Base", value = "MyApp.model.Car", identifier = "negative",
		autodetectTypes = false, reader = "json", writer = "json")
public class AutoCar {

	@ModelField
	@ModelId
	public Long id;

	public String name;

	@ModelField(reference = @ReferenceConfig(type = "Manufacturer"), allowBlank = false)
	public Long manufacturerId;

	@ModelField(reference = @ReferenceConfig(type = "Driver", role = "theDriver",
			association = "CarByDriver", inverse = "cars"), allowBlank = true)
	public Long driverId;

	@ModelField(reference = @ReferenceConfig(parent = "Owner"), unique = false)
	public Long ownerId;

	@ModelField(reference = @ReferenceConfig(type = "OneToOne"), unique = true)
	public Long oneToOneId;
}
