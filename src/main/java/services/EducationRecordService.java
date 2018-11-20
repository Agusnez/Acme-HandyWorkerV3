
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EducationRecordReposiroty;
import domain.EducationRecord;

@Service
@Transactional
public class EducationRecordService {

	// Managed repository
	@Autowired
	private EducationRecordReposiroty	educationRecordRepository;


	// Suporting services

	// Simple CRUD methods

	public EducationRecord create() {

		final EducationRecord result = new EducationRecord();

		return result;

	}

	public Collection<EducationRecord> findAll() {

		final Collection<EducationRecord> educationRecords = this.educationRecordRepository.findAll();

		Assert.notNull(educationRecords);

		return educationRecords;

	}

	public EducationRecord findOne(final int educationRecordID) {

		final EducationRecord educationRecord = this.educationRecordRepository.findOne(educationRecordID);

		Assert.notNull(educationRecord);

		return educationRecord;

	}

	public EducationRecord save(final EducationRecord s) {

		final EducationRecord educationRecord = this.educationRecordRepository.save(s);

		Assert.notNull(educationRecord);

		return educationRecord;

	}

	public void delete(final EducationRecord educationRecord) {

		this.educationRecordRepository.delete(educationRecord);
	}

	// Other business methods

}
