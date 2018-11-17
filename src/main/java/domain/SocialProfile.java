
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SocialProfile extends DomainEntity {

	//Atributos--------------------------------------------------------
	private String	nick;
	private String	socialName;
	private String	link;


	//Getters y Setters--------------------------------------------------
	@NotBlank
	public String getNick() {
		return this.nick;
	}
	public void setNick(final String nick) {
		this.nick = nick;
	}

	@NotBlank
	public String getSocialName() {
		return this.socialName;
	}
	public void setSocialName(final String socialName) {
		this.socialName = socialName;
	}

	@URL
	public String getLink() {
		return this.link;
	}
	public void setLink(final String link) {
		this.link = link;
	}


	private Actor	actor;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

}
