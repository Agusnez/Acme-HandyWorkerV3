
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import domain.EndorserRecord;

@Service
@Transactional
public class EndorserRecordService {

	// Managed Repository ------------------------
	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;


	// Suporting services ------------------------

	//	@Autowired
	//	private CurriculumService			curriculumService;

	//	@Autowired
	//	private HandyWorkerService				handyWorkerService;

	// Simple CRUD methods -----------------------

	public EndorserRecord create() {

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

		//		final HandyWorker handyWorker = handyWorkerService.findByPrincipal();
		//		Assert.notNull(handyWorker.getCurriculum()); 
		//
		//		final Curriculum curriculum = handyWorker.getCurriculum();
		//		curriculum.getEndorserRecords().add(result);
		//		this.curriculumService.save(curriculum);

		return result;
	}

	public void delete(final EndorserRecord endorserRecord) {

		Assert.notNull(endorserRecord);
		Assert.isTrue(endorserRecord.getId() != 0);

		//		final HandyWorker handyWorker = handyWorkerService.findByPrincipal();
		//		Assert.notNull(handyWorker);
		//		Assert.notNull(handyWorker.getCurriculum());
		//
		//		final Curriculum curriculum = handyWorker.getCurriculum() QUERY;
		//		curriculum.getEndorserRecords().remove(endorserRecord);

		this.endorserRecordRepository.delete(endorserRecord);

	}

	// Other business methods -----------------------
}
