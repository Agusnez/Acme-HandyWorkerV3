
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


	//Simple CRUD methods--------------------------------
	public Curriculum create() {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		Curriculum c = new Curriculum();;
		
		Collection<EducationRecord> educationRecords = new ArrayList<>();
		Collection<ProfessionalRecord> professionalRecords = new ArrayList<>();
		Collection<EndorserRecord> endorserRecords = new ArrayList<>();
		Collection<MiscellaneousRecord> miscellaneousRecords = new ArrayList<>();
		
		c.setEducationRecords(educationRecords);
		c.setProfessionalRecords(professionalRecords);
		c.setEndorserRecords(endorserRecords);
		c.setMiscellaneousRecords(miscellaneousRecords);

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
		
		for (EducationRecord edu: curriculum.getEducationRecords()) {
			this.educationRecordService.save(edu);
		}
		for (ProfessionalRecord pro: curriculum.getProfessionalRecords()) {
			this.professionalRecordService.save(pro);
		}
		for (EndorserRecord endo: curriculum.getEndorserRecords()) {
			this.endorserRecordService.save(endo);
		}
		for (MiscellaneousRecord misce: curriculum.getMiscellaneousRecords()) {
			this.miscellaneousRecordService.save(misce);
		}
		
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
