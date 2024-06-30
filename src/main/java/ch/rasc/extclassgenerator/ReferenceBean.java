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

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ReferenceBean {

	private String type;
	private String association;
	private String child;
	private String parent;
	private String role;
	private String inverse;

	public ReferenceBean() {
		// default constructor
	}

	public ReferenceBean(ReferenceConfig reference) {
		this.type = ModelGenerator.trimToNull(reference.type());
		this.association = ModelGenerator.trimToNull(reference.association());
		this.child = ModelGenerator.trimToNull(reference.child());
		this.parent = ModelGenerator.trimToNull(reference.parent());
		this.role = ModelGenerator.trimToNull(reference.role());
		this.inverse = ModelGenerator.trimToNull(reference.inverse());
	}

	// Tests if something is set.
	public boolean hasAnyProperties() {
		return StringUtils.hasText(this.type) || StringUtils.hasText(this.association)
				|| StringUtils.hasText(this.child) || StringUtils.hasText(this.parent)
				|| StringUtils.hasText(this.role) || StringUtils.hasText(this.inverse);
	}

	// Tests if only type is set.
	public boolean typeOnly() {
		return StringUtils.hasText(this.type) && !StringUtils.hasText(this.association)
				&& !StringUtils.hasText(this.child) && !StringUtils.hasText(this.parent)
				&& !StringUtils.hasText(this.role) && !StringUtils.hasText(this.inverse);
	}

	public String getType() {
		return this.type;
	}

	/**
	 * The type which this field references. This is the value set by the string form of
	 * reference. If the referenced entity has an ownership relationship this field should
	 * be omitted and reference.parent or reference.child should be specified instead.
	 */
	public void setType(String type) {
		this.type = type;
	}

	public String getAssociation() {
		return this.association;
	}

	/**
	 * The name of the association. By default, the name of the assocation is the
	 * capitalized inverse plus "By" plus the capitalized role.
	 */
	public void setAssociation(String association) {
		this.association = association;
	}

	public String getChild() {
		return this.child;
	}

	/**
	 * Set this property instead of reference.type to indicate that the referenced entity
	 * is an owned child of this entity. That is, the reference entity should be deleted
	 * when this entity is deleted.
	 */
	public void setChild(String child) {
		this.child = child;
	}

	public String getParent() {
		return this.parent;
	}

	/**
	 * Set this property instead of reference.type to indicate that the referenced entity
	 * is the owning parent of this entity. That is, this entity should be deleted when
	 * the reference entity is deleted.
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getRole() {
		return this.role;
	}

	/**
	 * The name of the role played by the referenced entity. By default, this is the field
	 * name (minus its "Id" suffix if present).
	 */
	public void setRole(String role) {
		this.role = role;
	}

	public String getInverse() {
		return this.inverse;
	}

	/**
	 * The name of the inverse role (of this entity with respect to the reference entity).
	 * By default, this is the pluralized name of this entity, unless this reference is
	 * unique, in which case the default name is the singularized name of this entity.
	 */
	public void setInverse(String inverse) {
		this.inverse = inverse;
	}

}
