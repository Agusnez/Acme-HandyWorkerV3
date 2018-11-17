
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class HandyWorker extends Actor {

	//Atributos-----------------------------------------------------------------
	private String					make;

	private Collection<Application>	applications;


	//Getters y Setters----------------------------------------------------------
	@NotBlank
	public String getMake() {
		return this.make;
	}
	public void setMake(final String make) {
		this.make = make;
	}

	@Valid
	@OneToMany
	public Collection<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

}
