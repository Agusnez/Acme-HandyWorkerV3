
package services;

import java.util.Collection;
import java.util.HashSet;

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
import domain.Customer;
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
	private ActorService		actorService;


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
	private Complaint createComplaint() {

		this.createNewActorAndLogIn1();

		Complaint complaint;
		final Complaint saved;
		final Collection<Complaint> complaints;

		complaint = this.complaintService.create();
		complaint.setDescription("xxxx");
		complaint.setTicker("251118-ASRT");

		final Integer numberOfComplaints = this.customerservice.findByPrincipal().getComplaints().size();

		saved = this.complaintService.save(complaint);

		final Integer numberOfComplaints2 = this.customerservice.findByPrincipal().getComplaints().size();

		Assert.isTrue(numberOfComplaints + 1 == numberOfComplaints2);

		complaints = this.complaintService.findAll();
		Assert.isTrue(complaints.contains(saved));

		return saved;

	}

	private void createNewActorAndLogIn1() {

		Customer customer, saved1;
		Collection<Customer> customers;
		final Collection<Complaint> complaints = new HashSet<>();

		customer = this.customerservice.create();
		customer.setName("González");
		customer.setMiddleName("Adolfo");
		customer.setSurname("Gustavo");
		customer.setAddress("Calle Almoralejo");
		customer.setComplaints(complaints);

		final UserAccount userAccount = customer.getUserAccount();
		userAccount.setUsername("Gustavito2");
		userAccount.setPassword("123123");

		customer.setUserAccount(userAccount);

		saved1 = this.customerservice.save(customer);
		customers = this.customerservice.findAll();
		Assert.isTrue(customers.contains(saved1));

		super.authenticate("Gustavito2");

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
