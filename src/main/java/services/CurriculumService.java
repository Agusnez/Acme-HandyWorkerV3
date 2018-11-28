
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import security.Authority;
import domain.Actor;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.HandyWorker;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;

@Service
@Transactional
public class CurriculumService {

	//Managed repository---------------------------------
	@Autowired
	private CurriculumRepository		curriculumRepository;

	//Suporting services---------------------------------
	@Autowired
	private PersonalRecordService		personalRecordService;

	@Autowired
	private EducationRecordService		educationRecordService;

	@Autowired
	private ProfessionalRecordService	professionalRecordService;

	@Autowired
	private EndorserRecordService		endorserRecordService;

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private HandyWorkerService			handyWorkerService;


	//Simple CRUD methods--------------------------------
	public Curriculum create() {

		final HandyWorker handyWorker = this.handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(handyWorker.getUserAccount().getAuthorities().contains(authority));

		Curriculum c = new Curriculum();;
		
		Collection<EducationRecord> educationRecords = new ArrayList<>();
		Collection<ProfessionalRecord> professionalRecords = new ArrayList<>();
		Collection<EndorserRecord> endorserRecords = new ArrayList<>();
		Collection<MiscellaneousRecord> miscellaneousRecords = new ArrayList<>();
		
		c.setEducationRecords(educationRecords);
		c.setProfessionalRecords(professionalRecords);
		c.setEndorserRecords(endorserRecords);
		c.setMiscellaneousRecords(miscellaneousRecords);

		final PersonalRecord pr = this.personalRecordService.create();

		final Collection<EducationRecord> c1 = new HashSet<>();
		final EducationRecord edr = this.educationRecordService.create();
		c1.add(edr);

		final Collection<ProfessionalRecord> c2 = new HashSet<>();
		final ProfessionalRecord prr = this.professionalRecordService.create();
		c2.add(prr);

		final Collection<EndorserRecord> c3 = new HashSet<>();
		final EndorserRecord enr = this.endorserRecordService.create();
		c3.add(enr);

		final Collection<MiscellaneousRecord> c4 = new HashSet<>();
		final MiscellaneousRecord mir = this.miscellaneousRecordService.create();
		c4.add(mir);

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

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Actor owner = curriculum.getHandyWorker();
		Assert.isTrue(actor.getId() == owner.getId());
		
		this.personalRecordService.save(curriculum.getPersonalRecord());
		
		
		
		Curriculum c = this.curriculumRepository.save(curriculum);


		return c;
	}

	//Other business methods----------------------------

	public Curriculum findByHandyWorkerId(final int handyWorkerId) {

		final Curriculum result = this.curriculumRepository.findByHandyWorkerId(handyWorkerId);

		return result;
	}

	public Curriculum findByPersonalRecordId(final int personalRecordId) {

		Assert.notNull(personalRecordId);

		final Curriculum result = this.curriculumRepository.findByPersonalRecordId(personalRecordId);

		Assert.notNull(result);

		return result;
	}
}
