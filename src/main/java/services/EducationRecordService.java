
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EducationRecordReposiroty;
import domain.Curriculum;
import domain.EducationRecord;
import domain.HandyWorker;

@Service
@Transactional
public class EducationRecordService {

	// Managed repository
	@Autowired
	private EducationRecordReposiroty	educationRecordRepository;

	// Suporting services

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private HandyWorkerService			handyWorkerService;


	// Simple CRUD methods

	public EducationRecord create() {

		final EducationRecord result;

		result = new EducationRecord();

		return result;

	}

	public Collection<EducationRecord> findAll() {

		Collection<EducationRecord> result;

		result = this.educationRecordRepository.findAll();
		Assert.notNull(result);

		return result;

	}

	public EducationRecord findOne(final int educationRecordId) {

		EducationRecord result;

		result = this.educationRecordRepository.findOne(educationRecordId);

		return result;

	}

	public EducationRecord save(final EducationRecord educationRecord) {

		Assert.notNull(educationRecord);
		EducationRecord result;

		result = this.educationRecordRepository.save(educationRecord);

		final HandyWorker handyWorker = this.handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);

		final Curriculum curriculum = this.curriculumService.findByHandyWorkerId(handyWorker.getId());
		Assert.notNull(curriculum);
		curriculum.getEducationRecords().add(result);
		this.curriculumService.save(curriculum);

		return result;

	}

	// Other business methods

}
