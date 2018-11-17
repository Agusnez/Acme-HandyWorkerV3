
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

		return null;

	}

	public Collection<EducationRecord> findAll() {

		return null;

	}

	public EducationRecord findOne(final int educationRecordID) {

		return null;

	}

	public EducationRecord save(final EducationRecord educationRecord) {

		return null;

	}

	public void delete(final EducationRecord educationRecord) {

	}

	// Other business methods

}
