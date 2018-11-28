
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.LoginService;
import utilities.AbstractTest;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
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
	private PersonalRecordService		personalRecordService;

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
	public void testCurriculumCreate() {

		super.authenticate("handyworker1");
		final Curriculum c = this.curriculumService.create();
		Assert.isNull(c.getTicker());
	}

	@Test
	public void testCurriculumFindAll() {
		final Collection<Curriculum> cvs = this.curriculumService.findAll();
		Assert.isTrue(cvs.size() >= 1);

	}

	@Test
	public void testCurriculumFindOne() {
		final int cvid = super.getEntityId("curriculum1");
		final Curriculum cv = this.curriculumService.findOne(cvid);
		Assert.notNull(cv);
	}

	@Test
	public void testCurriculumSave() {

		super.authenticate("handyworker1");

		final Curriculum cv = this.curriculumService.create();
		cv.setTicker("120218-ASRB");
		cv.setHandyWorker(this.handyWorkerService.findByUserAccount(LoginService.getPrincipal()));

		final PersonalRecord personalRecord = this.personalRecordService.create();
		personalRecord.setPhone("679237785");
		personalRecord.setFullName("Manolito Gafotas");
		cv.setPersonalRecord(personalRecord);

		final EducationRecord educationRecord = this.educationRecordService.create();
		educationRecord.setTitle("Educacion Basica");
		educationRecord.setInstitution("Universidad de la calle");
		educationRecord.setPeriod("To la vida");
		cv.addEducationRecord(educationRecord);

		final ProfessionalRecord profesionalRecord = this.professinalRecordService.create();
		profesionalRecord.setCompanyName("Desguace Totos");
		profesionalRecord.setPeriod("3 años");
		profesionalRecord.setRole("Chatarrero");
		profesionalRecord.setComments(new ArrayList<String>());
		cv.addProfessionalRecord(profesionalRecord);

		final EndorserRecord endorserRecord = this.endorserRecordService.create();
		endorserRecord.setFullName("Manolito Gafotas");
		endorserRecord.setPhone("679237785");
		endorserRecord.setComments(new ArrayList<String>());
		cv.addEndorserRecord(endorserRecord);

		final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.create();
		miscellaneousRecord.setTitle("Diploma al mejor chatarrero del año");
		miscellaneousRecord.setComments(new ArrayList<String>());
		cv.addMiscellaneousRecord(miscellaneousRecord);

		final Curriculum cvSaved = this.curriculumService.save(cv);
		Assert.notNull(cvSaved);
		Assert.notNull(cvSaved.getEducationRecords());
		Assert.isTrue(cvSaved.getPersonalRecord().getId() != 0);

	}
}
