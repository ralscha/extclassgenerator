package ch.rasc.extclassgenerator;

public class AllDataOptionsBean {
	private Boolean associated;

	private Boolean changes;

	private Boolean critical;

	private Boolean persist;

	public AllDataOptionsBean() {
		// default constructor
	}

	public AllDataOptionsBean(AllDataOptions allDataOptions) {
		if (!(allDataOptions.persist() == true && allDataOptions.associated() == false
				&& allDataOptions.changes() == false
				&& allDataOptions.critical() == false)) {

			if (allDataOptions.associated()) {
				associated = allDataOptions.associated();
			}

			if (allDataOptions.changes()) {
				changes = allDataOptions.changes();

				if (allDataOptions.critical()) {
					critical = allDataOptions.critical();
				}
			}
			else {
				if (allDataOptions.persist()) {
					persist = allDataOptions.persist();
				}
			}
		}
	}

	// Tests if something is set.
	public boolean hasAnyProperties() {
		return associated != null && changes != null && critical != null
				&& persist != null;
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
