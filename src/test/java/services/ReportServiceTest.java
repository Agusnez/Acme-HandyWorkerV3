
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.UserAccount;
import utilities.AbstractTest;
import domain.Complaint;
import domain.Referee;
import domain.Report;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ReportServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private ReportService		reportSevice;

	@Autowired
	private RefereeService		refereeservice;

	@Autowired
	private CustomerService		customerservice;

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	//Tests -------------------------------------------------------
	// TODO Report testing

	@Test
	public void testCreate() {

		this.authenticate("referee1");

		Report report;
		report = this.reportSevice.create();

		Assert.isNull(report.getComplaint());
		Assert.isNull(report.getDescription());
		Assert.isNull(report.getFinalMode());
		Assert.isNull(report.getMoment());
		Assert.isNull(report.getReferee());
		Assert.isTrue(report.getAttachments().isEmpty());
		Assert.isTrue(report.getNotes().isEmpty());

	}

	@Test
	public void testSave() {

		final Complaint complaint = this.createComplaint();

		this.authenticate("admin");

		final Referee referee = this.createNewActorAndLogIn2();

		Report report;
		final Report saved;
		final Collection<Report> reports;

		report = this.reportSevice.create();

		report.setComplaint(complaint);
		report.setReferee(referee);
		report.setDescription("prueba");
		report.setFinalMode(true);

		saved = this.reportSevice.save(report);
		reports = this.reportSevice.findAll();
		Assert.isTrue(reports.contains(saved));

	}

	@Test
	public void testFindOne() {

		final Complaint complaint = this.createComplaint();

		this.authenticate("admin");

		final Referee referee = this.createNewActorAndLogIn2();

		Report report;
		final Report saved, finded;

		report = this.reportSevice.create();

		report.setComplaint(complaint);
		report.setReferee(referee);
		report.setDescription("prueba");
		report.setFinalMode(true);

		saved = this.reportSevice.save(report);
		finded = this.reportSevice.findOne(saved.getId());
		Assert.isTrue(finded.equals(saved));

	}

	private Complaint createComplaint() {

		super.authenticate("customer4");

		Complaint complaint;
		final Complaint saved;
		final Collection<Complaint> complaints;

		complaint = this.complaintService.create();
		complaint.setDescription("xxxx");
		complaint.setTicker("251118-ASRT");

		final Integer numberOfComplaints = this.customerservice.findByPrincipal().getComplaints().size();
		final Integer numberOfComplaints2 = this.fixUpTaskService.findOne(this.getEntityId("fixUpTask6")).getComplaints().size();

		saved = this.complaintService.saveNewComplaint(complaint, this.getEntityId("fixUpTask6"));

		final Integer numberOfComplaints3 = this.customerservice.findByPrincipal().getComplaints().size();
		final Integer numberOfComplaints4 = this.fixUpTaskService.findOne(this.getEntityId("fixUpTask6")).getComplaints().size();

		Assert.isTrue(numberOfComplaints + 1 == numberOfComplaints3);
		Assert.isTrue(numberOfComplaints2 + 1 == numberOfComplaints4);

		complaints = this.complaintService.findAll();
		Assert.isTrue(complaints.contains(saved));

		return saved;

	}

	private Referee createNewActorAndLogIn2() {

		Referee referee, saved1;
		Collection<Referee> referees;

		referee = this.refereeservice.create();
		referee.setName("González");
		referee.setMiddleName("Adolfo");
		referee.setSurname("Gustavo");
		referee.setAddress("Calle Almoralejo");

		final UserAccount userAccount = referee.getUserAccount();
		userAccount.setUsername("Gustavito1");
		userAccount.setPassword("123123");

		referee.setUserAccount(userAccount);

		saved1 = this.refereeservice.save(referee);
		referees = this.refereeservice.findAll();
		Assert.isTrue(referees.contains(saved1));

		super.authenticate("Gustavito1");

		return saved1;

	}
}
