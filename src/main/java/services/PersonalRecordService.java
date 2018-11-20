
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import domain.PersonalRecord;

@Service
@Transactional
public class PersonalRecordService {

	//Managed Repository

	@Autowired
	private PersonalRecordRepository	personalRecordRepository;


	//Supporting services

	//Simple CRUD methods

	public PersonalRecord create() {

		PersonalRecord result;

		result = new PersonalRecord();

		return result;
	}

	public Collection<PersonalRecord> findAll() {

		final Collection<PersonalRecord> personalRecords = this.personalRecordRepository.findAll();

		Assert.notNull(personalRecords);

		return personalRecords;
	}

	public PersonalRecord findOne(final int personalRecordId) {

		final PersonalRecord personalRecords = this.personalRecordRepository.findOne(personalRecordId);

		Assert.notNull(personalRecords);

		return personalRecords;
	}

	public void delete(final PersonalRecord personalRecord) {

		Assert.notNull(personalRecord);
		Assert.isTrue(this.personalRecordRepository.exists(personalRecord.getId()));

		this.personalRecordRepository.delete(personalRecord);
	}
}
