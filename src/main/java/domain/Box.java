
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Box extends DomainEntity {

	//Atributos----------------------------------------------------------
	private String	name;
	private Boolean	byDefault;

	private Actor	actor;


	//Getters y Setters--------------------------------------------------
	@NotBlank
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	public Boolean getByDefault() {
		return this.byDefault;
	}
	public void setByDefault(final Boolean byDefault) {
		this.byDefault = byDefault;
	}

	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

}
