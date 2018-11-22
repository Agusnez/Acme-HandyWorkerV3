
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;

@Service
@Transactional
public class CurriculumService {

	//Managed repository---------------------------------
	@Autowired
	private CurriculumRepository	curriculumRepository;

	//Suporting services---------------------------------
	PersonalRecordService			personalRecordService;
	EducationRecordService			educationRecordService;
	ProfessionalRecordService		professionalRecordService;
	EndorserRecordService			endorserRecordService;
	MiscellaneousRecordService		miscellaneousRecordService;


	//Simple CRUD methods--------------------------------
	public Curriculum create() {
		//Comprobar que el que lo crea es el Handy Worker
		Curriculum c;
		c = new Curriculum();

		final PersonalRecord pr = this.personalRecordService.create();
		final EducationRecord er = this.educationRecordService.create();
		final ProfessionalRecord pr1 = this.professionalRecordService.create();
		final EndorserRecord er1 = this.endorserRecordService.create();
		final MiscellaneousRecord mr = this.miscellaneousRecordService.create();

		final Collection<EducationRecord> c1 = c.getEducationRecords();
		c1.add(er);
		final Collection<ProfessionalRecord> c2 = c.getProfessionalRecords();
		c2.add(pr1);
		final Collection<EndorserRecord> c3 = c.getEndorserRecords();
		c3.add(er1);
		final Collection<MiscellaneousRecord> c4 = c.getMiscellaneousRecords();
		c4.add(mr);

		c.setEducationRecords(c1);
		c.setEndorserRecords(c3);
		c.setMiscellaneousRecords(c4);
		c.setPersonalRecord(pr);
		c.setProfessionalRecords(c2);

		return c;
	}

	public Collection<Curriculum> findAll() {
		Collection<Curriculum> result;
		result = this.curriculumRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Curriculum findOne(final int curriculumId) {
		Curriculum c;
		c = this.curriculumRepository.findOne(curriculumId);
		Assert.notNull(c);
		return c;
	}

	public Curriculum save(final Curriculum curriculum) {
		Assert.notNull(curriculum);
		//Comprobar autoridad 
		Curriculum c;
		c = this.curriculumRepository.save(curriculum);
		return c;
	}

	//Other business methods----------------------------

}
