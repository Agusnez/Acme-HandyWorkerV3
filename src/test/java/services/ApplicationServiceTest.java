
package services;

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
	public void testCreateApplication() {
		super.authenticate("handyWorker1");
		Application a, saved;

		a = this.applicationService.create();
		a.setComment("Application1234");

		final Money m = new Money();
		m.setAmount(23.5);
		m.setCurrency("$");
		a.setOfferedPrice(m);

		final FixUpTask f = this.fixUpTaskService.findOne(2600);
		a.setFixUpTask(f);

		saved = this.applicationService.save(a);
		Assert.isTrue(this.applicationService.findAll().contains(saved));
		super.authenticate(null);
	}

	@Test
	public void testUpdateApplication() {
		super.authenticate("customer2");

		Application a, saved;
		a = this.applicationService.findOne(super.getEntityId("application8"));
		System.out.println(a.getStatus());
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
