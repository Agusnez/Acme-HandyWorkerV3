
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import security.Authority;
import domain.Actor;
import domain.Curriculum;
import domain.HandyWorker;
import domain.ProfessionalRecord;

@Service
@Transactional
public class ProfessionalRecordService {

	// Managed Repository ------------------------
	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;

	// Suporting services ------------------------

	@Autowired
	private CurriculumService				curriculumService;

	@Autowired
	private HandyWorkerService				handyWorkerService;

	@Autowired
	private ActorService					actorService;


	// Simple CRUD methods -----------------------

	public ProfessionalRecord create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		ProfessionalRecord result;

		result = new ProfessionalRecord();

		return result;

	}

	public Collection<ProfessionalRecord> findAll() {

		Collection<ProfessionalRecord> result;

		result = this.professionalRecordRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public ProfessionalRecord findOne(final int professionalRecordId) {

		ProfessionalRecord result;

		result = this.professionalRecordRepository.findOne(professionalRecordId);

		return result;
	}

	public ProfessionalRecord save(final ProfessionalRecord professionalRecord) {

		Assert.notNull(professionalRecord);
		ProfessionalRecord result;

		result = this.professionalRecordRepository.save(professionalRecord);

		final HandyWorker handyWorker = this.handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);

		final Curriculum curriculum = this.curriculumService.findByHandyWorkerId(handyWorker.getId());
		Assert.notNull(curriculum);
		curriculum.getProfessionalRecords().add(result);
		this.curriculumService.save(curriculum);

		return result;
	}

	// Other business methods -----------------------
}
