
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
import domain.HandyWorker;

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
	private ProfessionalRecordService	professionalRecordService;

	@Autowired
	private EducationRecordService		educationRecordService;

	@Autowired
	private PersonalRecordService		personalRecordService;

	@Autowired
	private EndorserRecordService		endorserRecordService;

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private HandyWorkerService			handyWorkerService;

	@Autowired
	private ActorService				actorService;


	//Tests -------------------------------------------------------

	@Test
	public void testCurriculumFindOne() {

		Curriculum curriculum;
		Curriculum find;
		final Integer curriculumId;
		HandyWorker handyWorker;

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

		this.handyWorkerService.save(handyWorker);

		super.authenticate("Gustavito");

		curriculum = this.curriculumService.create();

		curriculum.setTicker("Afnw67767cb87tcb8757");

		curriculum.setHandyWorker(handyWorker);

		curriculumId = curriculum.getId();

		find = this.curriculumService.findOne(curriculumId);

		Assert.isTrue(find.getId() == curriculumId);

	}

	@Test
	public void testCurriculumSave() {

		final Curriculum curriculum;
		Curriculum saved;
		Collection<Curriculum> curriculums;
		HandyWorker handyWorker;

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

		this.handyWorkerService.save(handyWorker);

		super.authenticate("Gustavito");

		curriculum = this.curriculumService.create();

		curriculum.setTicker("Afnw67767cb87tcb8757");

		curriculum.setHandyWorker(handyWorker);

		saved = this.curriculumService.save(curriculum);
		curriculums = this.curriculumService.findAll();

		Assert.isTrue(curriculums.contains(saved));

	}

}
