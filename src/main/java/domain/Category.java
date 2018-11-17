
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	private String		name;

	private Category	parent;


	@NotBlank
	@Column(unique = true)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@ManyToOne(optional = true)
	@Valid
	public Category getParent() {
		return this.parent;
	}

	public void setParent(final Category parent) {
		this.parent = parent;
	}

}
