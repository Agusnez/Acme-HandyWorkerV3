
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import security.Authority;
import domain.Actor;
import domain.Curriculum;
import domain.EndorserRecord;
import domain.HandyWorker;

@Service
@Transactional
public class EndorserRecordService {

	// Managed Repository ------------------------
	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;

	// Suporting services ------------------------

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private HandyWorkerService			handyWorkerService;

	@Autowired
	private ActorService				actorService;


	// Simple CRUD methods -----------------------

	public EndorserRecord create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(!(actor.getUserAccount().getAuthorities().contains(authority)));

		EndorserRecord result;

		result = new EndorserRecord();

		return result;
	}

	public Collection<EndorserRecord> findAll() {

		Collection<EndorserRecord> result;

		result = this.endorserRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public EndorserRecord findOne(final int endorserRecordId) {

		EndorserRecord result;

		result = this.endorserRecordRepository.findOne(endorserRecordId);

		return result;
	}

	public EndorserRecord save(final EndorserRecord endorserRecord) {

		Assert.notNull(endorserRecord);
		EndorserRecord result;

		result = this.endorserRecordRepository.save(endorserRecord);

		final HandyWorker handyWorker = this.handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);

		final Curriculum curriculum = this.curriculumService.findByHandyWorkerId(handyWorker.getId());
		Assert.notNull(curriculum);
		curriculum.getEndorserRecords().add(result);
		this.curriculumService.save(curriculum);

		return result;
	}

	// Other business methods -----------------------
}
