/**
 * Copyright 2013-2016 Ralph Schaer <ralphschaer@gmail.com>
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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PartialDataOptionsBean {
	private Boolean associated;

	private Boolean changes;

	private Boolean critical;

	private Boolean persist;

	public PartialDataOptionsBean() {
		// default constructor
	}

	public PartialDataOptionsBean(PartialDataOptions partialDataOptions) {
		if (!(partialDataOptions.persist() == false
				&& partialDataOptions.associated() == false
				&& partialDataOptions.changes() == true
				&& partialDataOptions.critical() == true)) {

			if (partialDataOptions.associated()) {
				associated = partialDataOptions.associated();
			}

			if (partialDataOptions.changes()) {
				changes = partialDataOptions.changes();

				if (partialDataOptions.critical()) {
					critical = partialDataOptions.critical();
				}
			}
			else {
				if (partialDataOptions.persist()) {
					persist = partialDataOptions.persist();
				}
			}
		}
	}

	// Tests if something is set.
	public boolean hasAnyProperties() {
		return associated != null || changes != null || critical != null
				|| persist != null;
	}

	public Boolean getAssociated() {
		return associated;
	}

	/**
	 * Set to true to include associated data
	 */
	public void setAssociated(Boolean associated) {
		this.associated = associated;
	}

	public Boolean getChanges() {
		return changes;
	}

	/**
	 * Set to true to only include fields that have been modified
	 */
	public void setChanges(Boolean changes) {
		this.changes = changes;
	}

	public Boolean getCritical() {
		return critical;
	}

	/**
	 * Set to true to include fields set as critical. This is only meaningful when changes
	 * is true
	 */
	public void setCritical(Boolean critical) {
		this.critical = critical;
	}

	public Boolean getPersist() {
		return persist;
	}

	/**
	 * Set to true to only return persistent fields
	 */
	public void setPersist(Boolean persist) {
		this.persist = persist;
	}
}
