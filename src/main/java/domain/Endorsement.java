
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@Entity
@Access(AccessType.PROPERTY)
public class Endorsement extends DomainEntity {

	//Atributos---------------------------------------------------------------------
	private Date				moment;
	private Collection<String>	comments;

	private Actor				creator;
	private Actor				recipient;


	//Getters y Setters---------------------------------------------------------------
	@Past
	@NotNull
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@ElementCollection
	public Collection<String> getComments() {
		return this.comments;
	}
	public void setComments(final Collection<String> comments) {
		this.comments = comments;
	}

	@Valid
	@ManyToOne(optional = false)
	public Actor getCreator() {
		return this.creator;
	}

	public void setCreator(final Actor creator) {
		this.creator = creator;
	}

	@Valid
	@ManyToOne(optional = false)
	public Actor getRecipient() {
		return this.recipient;
	}

	public void setRecipient(final Actor recipient) {
		this.recipient = recipient;
	}

}
