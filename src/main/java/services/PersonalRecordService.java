
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import security.Authority;
import domain.HandyWorker;
import domain.PersonalRecord;

@Service
@Transactional
public class PersonalRecordService {

	//Managed Repository

	@Autowired
	private PersonalRecordRepository	personalRecordRepository;

	//Supporting services

	@Autowired
	private HandyWorkerService			handyWorkerService;


	//Simple CRUD methods

	public PersonalRecord create() {

		final HandyWorker handyWorker = this.handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(handyWorker.getUserAccount().getAuthorities().contains(authority));

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

	public PersonalRecord save(final PersonalRecord personalRecord) {
		final HandyWorker handyWorker = this.handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);
		Assert.notNull(personalRecord);

		PersonalRecord result;

		result = this.personalRecordRepository.save(personalRecord);

		return result;
	}
}
