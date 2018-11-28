
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Application;
import domain.CreditCard;
import domain.FixUpTask;
import domain.Money;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	//Tests -------------------------------------------------------
	@Test
	public void testSave() {
		super.authenticate("handyWorker1");
		Application a, saved;

		a = this.applicationService.create();
		a.setComment("Application1234");

		final Money m = new Money();
		m.setAmount(23.5);
		m.setCurrency("$");
		a.setOfferedPrice(m);

		final FixUpTask f = this.fixUpTaskService.findOne(this.getEntityId("fixUpTask3"));
		a.setFixUpTask(f);

		saved = this.applicationService.save(a);
		Assert.isTrue(this.applicationService.findAll().contains(saved));
		super.authenticate(null);
	}

	@Test
	public void testCreate() {

		super.authenticate("handyWorker1");

		final Application a = this.applicationService.create();

		Assert.isNull(a.getComment());
		Assert.isNull(a.getCreditCard());
		Assert.isNull(a.getFixUpTask());
		Assert.isNull(a.getMoment());
		Assert.isNull(a.getOfferedPrice());
		Assert.isNull(a.getStatus());

	}

	@Test
	public void testAccept() {
		super.authenticate("customer2");

		Application a, saved;
		Collection<Application> applications;

		a = this.applicationService.findOne(super.getEntityId("application8"));
		a.setStatus("ACCEPTED");

		final CreditCard c = new CreditCard();
		c.setBrandName("BBVA");
		c.setCvv(847);
		c.setExpMonth(07);
		c.setExpYear(2018);
		c.setHolderName("Paco");
		c.setNumber("4531023072020581");

		a.setCreditCard(c);

		saved = this.applicationService.save(a);

		Assert.isTrue(saved.getStatus().equals("ACCEPTED"));

		applications = this.fixUpTaskService.findOne(this.getEntityId("fixUpTask3")).getApplications();

		Integer i = 0;
		Integer y = 0;

		for (final Application application : applications)
			if (application.getStatus() == "ACCEPTED")
				i++;
		for (final Application application : applications)
			if (application.getStatus() == "REJECTED")
				y++;

		Assert.isTrue(i == 1);
		Assert.isTrue(y + i == applications.size());

		super.authenticate(null);

	}

	@Test
	public void testFindOne() {

		super.authenticate("handyWorker1");
		Application a, saved;

		a = this.applicationService.create();
		a.setComment("Application1234");

		final Money m = new Money();
		m.setAmount(23.5);
		m.setCurrency("$");
		a.setOfferedPrice(m);

		final FixUpTask f = this.fixUpTaskService.findOne(this.getEntityId("fixUpTask3"));
		a.setFixUpTask(f);

		saved = this.applicationService.save(a);
		Assert.isTrue(this.applicationService.findOne(saved.getId()).equals(saved));

		super.authenticate(null);
	}

	//	@Test
	//	public void testUpdateApplicationNoCustomer() {
	//
	//	}
	//	
	//	@Test
	//	public void testUpdateApplicationNoCustomerOwn() {
	//	}
	//	@Test
	//	public void testUpdateWrongAttributeApplication() {
	//	}
	//	@Test
	//	public void testUpdateApplicationNoPending() {
	//	}
	//	

}
