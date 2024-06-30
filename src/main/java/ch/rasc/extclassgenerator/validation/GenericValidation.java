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
package ch.rasc.extclassgenerator.validation;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

public class GenericValidation extends AbstractValidation {

	private final Map<String, Object> options;

	public GenericValidation(String type, String field, Map<String, Object> options) {
		super(type, field);
		if (options != null) {
			this.options = new LinkedHashMap<>(options);
		}
		else {
			this.options = null;
		}
	}

	@JsonAnyGetter
	public Map<String, Object> getOptions() {
		return this.options;
	}
}
