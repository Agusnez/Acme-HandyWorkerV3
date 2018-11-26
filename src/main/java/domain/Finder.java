
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	//Atributos-----------------------------------------------------------------------
	private String	keyWord;
	private String	category;
	private String	warranty;
	private Double	minPrice;
	private Double	maxPrice;
	private Date	minDate;
	private Date	maxDate;
	private Date	lastUpdate;


	//Getters y Setters----------------------------------------------------------------

	public String getKeyWord() {
		return this.keyWord;
	}
	public void setKeyWord(final String keyWord) {
		this.keyWord = keyWord;
	}

	public String getCategory() {
		return this.category;
	}
	public void setCategory(final String category) {
		this.category = category;
	}

	public String getWarranty() {
		return this.warranty;
	}
	public void setWarranty(final String warranty) {
		this.warranty = warranty;
	}

	@Min(0)
	@Digits(integer = 9, fraction = 2)
	public Double getMinPrice() {
		return this.minPrice;
	}
	public void setMinPrice(final Double minPrice) {
		this.minPrice = minPrice;
	}

	@Digits(integer = 9, fraction = 2)
	public Double getMaxPrice() {
		return this.maxPrice;
	}
	public void setMaxPrice(final Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Date getMinDate() {
		return this.minDate;
	}
	public void setMinDate(final Date minDate) {
		this.minDate = minDate;
	}

	public Date getMaxDate() {
		return this.maxDate;
	}
	public void setMaxDate(final Date maxDate) {
		this.maxDate = maxDate;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getLastUpdate() {
		return this.lastUpdate;
	}
	public void setLastUpdate(final Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


	// Relationships ----------------------------------------------------------

	private Collection<FixUpTask>	fixUpTasks;
	private HandyWorker				handyWorker;


	@ManyToMany
	@Valid
	public Collection<FixUpTask> getFixUpTasks() {
		return this.fixUpTasks;
	}

	public void setFixUpTasks(final Collection<FixUpTask> fixUpTasks) {
		this.fixUpTasks = fixUpTasks;
	}

	@OneToOne(optional = false)
	@Valid
	public HandyWorker getHandyWorker() {
		return this.handyWorker;
	}
	public void setHandyWorker(final HandyWorker handyWorker) {
		this.handyWorker = handyWorker;
	}

}
