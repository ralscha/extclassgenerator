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
package ch.rasc.extclassgenerator;

final class JsCacheKey {
	private final String modelName;

	private final OutputConfig config;

	JsCacheKey(ModelBean modelBean, OutputConfig config) {
		this.modelName = modelBean.getName();
		this.config = config;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.config == null ? 0 : this.config.hashCode());
		result = prime * result
				+ (this.modelName == null ? 0 : this.modelName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		JsCacheKey other = (JsCacheKey) obj;
		if (this.config == null) {
			if (other.config != null) {
				return false;
			}
		}
		else if (!this.config.equals(other.config)) {
			return false;
		}
		if (this.modelName == null) {
			if (other.modelName != null) {
				return false;
			}
		}
		else if (!this.modelName.equals(other.modelName)) {
			return false;
		}
		return true;
	}

}