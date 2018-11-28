
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@Entity
@Access(AccessType.PROPERTY)
public class Curriculum extends DomainEntity {

	private String							ticker;

	private Collection<ProfessionalRecord>	professionalRecords;
	private Collection<EducationRecord>		educationRecords;
	private PersonalRecord					personalRecord;
	private Collection<EndorserRecord>		endorserRecords;
	private Collection<MiscellaneousRecord>	miscellaneousRecords;

	private HandyWorker						handyWorker;


	@Column(unique = true)
	@Pattern(regexp = "\\A\\d{2}(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])-[A-Z]{4}\\z")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<ProfessionalRecord> getProfessionalRecords() {
		return this.professionalRecords;
	}

	public void setProfessionalRecords(final Collection<ProfessionalRecord> professionalRecords) {
		this.professionalRecords = professionalRecords;
	}
	
	public void addProfessionalRecord(ProfessionalRecord professionalRecord) {
		this.professionalRecords.add(professionalRecord);
	}
	
	public void removeProfessionalRecord(ProfessionalRecord professionalRecord) {
		this.professionalRecords.remove(professionalRecord);
	}

	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<EducationRecord> getEducationRecords() {
		return this.educationRecords;
	}

	public void setEducationRecords(final Collection<EducationRecord> educationRecords) {
		this.educationRecords = educationRecords;
	}
	
	public void addEducationRecord(EducationRecord educationRecord) {
		this.educationRecords.add(educationRecord);
	}
	
	public void removeEducationRecord(EducationRecord educationRecord) {
		this.educationRecords.remove(educationRecord);
	}

	@Valid
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	public PersonalRecord getPersonalRecord() {
		return this.personalRecord;
	}

	public void setPersonalRecord(final PersonalRecord personalRecord) {
		this.personalRecord = personalRecord;
	}

	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<EndorserRecord> getEndorserRecords() {
		return this.endorserRecords;
	}

	public void setEndorserRecords(final Collection<EndorserRecord> endorserRecords) {
		this.endorserRecords = endorserRecords;
	}

	public void addEndorserRecord(EndorserRecord endorserRecord) {
		this.endorserRecords.add(endorserRecord);
	}
	
	public void removeEndorserRecord(EndorserRecord endorserRecord) {
		this.endorserRecords.remove(endorserRecord);
	}
	
	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<MiscellaneousRecord> getMiscellaneousRecords() {
		return this.miscellaneousRecords;
	}

	public void setMiscellaneousRecords(final Collection<MiscellaneousRecord> miscellaneousRecords) {
		this.miscellaneousRecords = miscellaneousRecords;
	}
	
	public void addMiscellaneousRecord(MiscellaneousRecord miscellaneousRecord) {
		this.miscellaneousRecords.add(miscellaneousRecord);
	}
	
	public void removeMiscellaneousRecord(MiscellaneousRecord miscellaneousRecord) {
		this.miscellaneousRecords.remove(miscellaneousRecord);
	}

	@Valid
	@OneToOne(optional = false)
	public HandyWorker getHandyWorker() {
		return this.handyWorker;
	}

	public void setHandyWorker(final HandyWorker handyWorker) {
		this.handyWorker = handyWorker;
	}
	
	

}
