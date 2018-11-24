
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.HandyWorker;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CurriculumServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private HandyWorkerService			handyWorkerService;

	@Autowired
	private EducationRecordService		educationRecordService;

	@Autowired
	private ProfessionalRecordService	professinalRecordService;

	@Autowired
	private EndorserRecordService		endorserRecordService;

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;


	//Tests -------------------------------------------------------

	@Test
	public void testCurriculumFindOne() {

		Curriculum curriculum, saved;
		Curriculum find;
		final Integer curriculumId;
		HandyWorker handyWorker;
		final HandyWorker saved1;
		final PersonalRecord personalRecord;
		final Collection<EducationRecord> educationRecords;
		final EducationRecord educationRecord;
		final Collection<ProfessionalRecord> professionalRecords;
		final ProfessionalRecord professionalRecord;
		final Collection<EndorserRecord> endorserRecords;
		final EndorserRecord endorserRecord;
		final Collection<MiscellaneousRecord> miscellaneousRecords;
		final MiscellaneousRecord miscellaneousRecord;

		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		final List<Authority> list = new ArrayList<Authority>();
		list.add(authority);

		final UserAccount userAccount = new UserAccount();
		userAccount.setAuthorities(list);
		userAccount.setUsername("Gustavito");
		userAccount.setPassword("123123");

		handyWorker = this.handyWorkerService.create();
		handyWorker.setName("González");
		handyWorker.setMiddleName("Adolfo");
		handyWorker.setSurname("Gustavo");
		handyWorker.setAddress("Calle Almoralejo");
		handyWorker.setMake("Gustavo Adolfo González");
		handyWorker.setUserAccount(userAccount);

		saved1 = this.handyWorkerService.save(handyWorker);

		super.authenticate("Gustavito");

		curriculum = this.curriculumService.create();

		curriculum.setTicker("241118-RAUL");

		curriculum.setHandyWorker(saved1);

		personalRecord = curriculum.getPersonalRecord();
		personalRecord.setFullName("Gustavo Adolfo González");
		personalRecord.setPhone("658456721");
		curriculum.setPersonalRecord(personalRecord);

		educationRecords = curriculum.getEducationRecords();
		educationRecord = this.educationRecordService.create();
		educationRecord.setTitle("Ingeniería del software");
		educationRecord.setPeriod("2006-2010");
		educationRecord.setInstitution("etsic");
		educationRecords.add(educationRecord);
		curriculum.setEducationRecords(educationRecords);

		professionalRecords = curriculum.getProfessionalRecords();
		professionalRecord = this.professinalRecordService.create();
		professionalRecord.setCompanyName("Microsoft");
		professionalRecord.setPeriod("2010-2012");
		professionalRecord.setRole("ProjectManager");
		curriculum.setProfessionalRecords(professionalRecords);

		endorserRecords = curriculum.getEndorserRecords();
		endorserRecord = this.endorserRecordService.create();
		endorserRecord.setFullName("Gustavo Adolfo González");
		endorserRecord.setPhone("674567809");
		curriculum.setEndorserRecords(endorserRecords);

		miscellaneousRecords = curriculum.getMiscellaneousRecords();
		miscellaneousRecord = this.miscellaneousRecordService.create();
		miscellaneousRecord.setTitle("Java7");
		curriculum.setMiscellaneousRecords(miscellaneousRecords);

		curriculum.setHandyWorker(saved1);

		saved = this.curriculumService.save(curriculum);
		curriculumId = saved.getId();
		find = this.curriculumService.findOne(curriculumId);

		Assert.isTrue(find.getId() == curriculumId);

	}

	@Test
	public void testCurriculumSave() {

		final Curriculum curriculum;
		Curriculum saved;
		Collection<Curriculum> curriculums;
		HandyWorker handyWorker;
		final HandyWorker saved1;
		final PersonalRecord personalRecord;
		final Collection<EducationRecord> educationRecords;
		final EducationRecord educationRecord;
		final Collection<ProfessionalRecord> professionalRecords;
		final ProfessionalRecord professionalRecord;
		final Collection<EndorserRecord> endorserRecords;
		final EndorserRecord endorserRecord;
		final Collection<MiscellaneousRecord> miscellaneousRecords;
		final MiscellaneousRecord miscellaneousRecord;

		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		final List<Authority> list = new ArrayList<Authority>();
		list.add(authority);

		final UserAccount userAccount = new UserAccount();
		userAccount.setAuthorities(list);
		userAccount.setUsername("Gustavito");
		userAccount.setPassword("123123");

		handyWorker = this.handyWorkerService.create();
		handyWorker.setName("González");
		handyWorker.setMiddleName("Adolfo");
		handyWorker.setSurname("Gustavo");
		handyWorker.setAddress("Calle Almoralejo");
		handyWorker.setMake("Gustavo Adolfo González");
		handyWorker.setUserAccount(userAccount);

		saved1 = this.handyWorkerService.save(handyWorker);

		super.authenticate("Gustavito");

		curriculum = this.curriculumService.create();

		curriculum.setTicker("241118-RAUL");

		curriculum.setHandyWorker(saved1);

		personalRecord = curriculum.getPersonalRecord();
		personalRecord.setFullName("Gustavo Adolfo González");
		personalRecord.setPhone("658456721");
		curriculum.setPersonalRecord(personalRecord);

		educationRecords = curriculum.getEducationRecords();
		educationRecord = this.educationRecordService.create();
		educationRecord.setTitle("Ingeniería del software");
		educationRecord.setPeriod("2006-2010");
		educationRecord.setInstitution("etsic");
		educationRecords.add(educationRecord);
		curriculum.setEducationRecords(educationRecords);

		professionalRecords = curriculum.getProfessionalRecords();
		professionalRecord = this.professinalRecordService.create();
		professionalRecord.setCompanyName("Microsoft");
		professionalRecord.setPeriod("2010-2012");
		professionalRecord.setRole("ProjectManager");
		curriculum.setProfessionalRecords(professionalRecords);

		endorserRecords = curriculum.getEndorserRecords();
		endorserRecord = this.endorserRecordService.create();
		endorserRecord.setFullName("Gustavo Adolfo González");
		endorserRecord.setPhone("674567809");
		curriculum.setEndorserRecords(endorserRecords);

		miscellaneousRecords = curriculum.getMiscellaneousRecords();
		miscellaneousRecord = this.miscellaneousRecordService.create();
		miscellaneousRecord.setTitle("Java7");
		curriculum.setMiscellaneousRecords(miscellaneousRecords);

		saved = this.curriculumService.save(curriculum);
		curriculums = this.curriculumService.findAll();

		Assert.isTrue(curriculums.contains(saved));

	}
}
