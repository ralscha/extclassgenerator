/**
 * Copyright 2013-2015 Ralph Schaer <ralphschaer@gmail.com>
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

final class ModelCacheKey {
	private final String className;

	private final OutputConfig outputConfig;

	public ModelCacheKey(String className, OutputConfig outputConfig) {
		this.className = className;
		this.outputConfig = outputConfig;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (this.className == null ? 0 : this.className.hashCode());
		result = prime * result
				+ (this.outputConfig == null ? 0 : this.outputConfig.hashCode());
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
		ModelCacheKey other = (ModelCacheKey) obj;
		if (this.className == null) {
			if (other.className != null) {
				return false;
			}
		}
		else if (!this.className.equals(other.className)) {
			return false;
		}
		if (this.outputConfig == null) {
			if (other.outputConfig != null) {
				return false;
			}
		}
		else if (!this.outputConfig.equals(other.outputConfig)) {
			return false;
		}
		return true;
	}

}