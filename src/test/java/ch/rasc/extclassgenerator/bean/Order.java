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

import java.util.List;

import ch.rasc.extclassgenerator.ModelAssociation;
import ch.rasc.extclassgenerator.ModelAssociationType;

public class Order {

	public int entityId;

	@ModelAssociation(value = ModelAssociationType.HAS_MANY, model = Pos.class,
			getterName = "ignore", setterName = "ignore", autoLoad = false,
			foreignKey = "orderId", name = "pos", primaryKey = "entityId")
	public List<Pos> positions;

}
