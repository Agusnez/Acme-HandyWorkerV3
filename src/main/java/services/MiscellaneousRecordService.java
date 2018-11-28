
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import security.Authority;
import domain.Actor;
import domain.Curriculum;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	// Managed Repository ------------------------
	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	// Suporting services ------------------------

	@Autowired
	private CurriculumService				curriculumService;

	@Autowired
	private ActorService					actorService;


	// Simple CRUD methods -----------------------

	public MiscellaneousRecord create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		final MiscellaneousRecord result;

		result = new MiscellaneousRecord();

		return result;
	}

	public Collection<MiscellaneousRecord> findAll() {
		Collection<MiscellaneousRecord> result;

		result = this.miscellaneousRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public MiscellaneousRecord findOne(final int miscellaneousRecordId) {
		MiscellaneousRecord result;

		result = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);

		return result;
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		Assert.notNull(miscellaneousRecord);
		MiscellaneousRecord result;

		result = this.miscellaneousRecordRepository.save(miscellaneousRecord);

		final Curriculum curriculum = this.curriculumService.findByHandyWorkerId(actor.getId());
		Assert.notNull(curriculum);
		curriculum.getMiscellaneousRecords().add(result);
		this.curriculumService.save(curriculum);

		return result;
	}

	// Other business methods -----------------------
}
