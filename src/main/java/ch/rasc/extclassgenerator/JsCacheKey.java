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
package ch.rasc.extclassgenerator;

import java.util.Objects;

final class JsCacheKey {
	private final String modelName;

	private final OutputConfig config;

	JsCacheKey(ModelBean modelBean, OutputConfig config) {
		this.modelName = modelBean.getName();
		this.config = config;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.config, this.modelName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		JsCacheKey other = (JsCacheKey) obj;
		if (!Objects.equals(this.config, other.config)
				|| !Objects.equals(this.modelName, other.modelName)) {
			return false;
		}
		return true;
	}

}