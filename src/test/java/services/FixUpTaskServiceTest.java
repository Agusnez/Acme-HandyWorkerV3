
package services;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.UserAccount;
import utilities.AbstractTest;
import domain.Customer;
import domain.FixUpTask;
import domain.Money;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FixUpTaskServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private FixUpTaskService	fixUpTaskService;

	@Autowired
	private CustomerService		customerService;


	//Tests -------------------------------------------------------
	// TODO FixUpTask testing
	@Test
	public void CustomerCreateTest() {

		final FixUpTask fixUpTask;
		Customer customer, saved1;
		Collection<Customer> customers;

		customer = this.customerService.create();
		customer.setName("González");
		customer.setMiddleName("Adolfo");
		customer.setSurname("Gustavo");
		customer.setAddress("Calle Almoralejo");

		final UserAccount userAccount = customer.getUserAccount();
		userAccount.setUsername("Gustavito");
		userAccount.setPassword("123123");

		customer.setUserAccount(userAccount);

		saved1 = this.customerService.save(customer);
		customers = this.customerService.findAll();
		Assert.isTrue(customers.contains(saved1));

		super.authenticate("Gustavito");

		fixUpTask = this.fixUpTaskService.create();

		Assert.isNull(fixUpTask.getTicker());
		Assert.isNull(fixUpTask.getMoment());
		Assert.isNull(fixUpTask.getDescription());
		Assert.isNull(fixUpTask.getAddress());
		Assert.isNull(fixUpTask.getMaximumPrice());
		Assert.isNull(fixUpTask.getStartDate());
		Assert.isNull(fixUpTask.getEndDate());

		Assert.isTrue(fixUpTask.getApplications().isEmpty());
		Assert.isTrue(fixUpTask.getComplaints().isEmpty());

	}

	@Test
	public void FixUpTaskSaveTest() {

		final FixUpTask fixUpTask, saved;
		Customer customer, saved1;
		Collection<Customer> customers;

		customer = this.customerService.create();
		customer.setName("González");
		customer.setMiddleName("Adolfo");
		customer.setSurname("Gustavo");
		customer.setAddress("Calle Almoralejo");

		final UserAccount userAccount = customer.getUserAccount();
		userAccount.setUsername("Gustavito");
		userAccount.setPassword("123123");

		customer.setUserAccount(userAccount);

		saved1 = this.customerService.save(customer);
		customers = this.customerService.findAll();
		Assert.isTrue(customers.contains(saved1));

		super.authenticate("Gustavito");

		fixUpTask = this.fixUpTaskService.create();

		fixUpTask.setTicker("120318-DUGE");
		fixUpTask.setDescription("Example description");
		fixUpTask.setAddress("Example address");
		final Money money = new Money();
		money.setAmount(145.);
		money.setCurrency("euros");
		fixUpTask.setMaximumPrice(money);
		final Date startDate = new Date(System.currentTimeMillis() - 100000000);
		fixUpTask.setStartDate(startDate);
		final Date endDate = new Date(System.currentTimeMillis() - 1000000);
		fixUpTask.setEndDate(endDate);

		saved = this.fixUpTaskService.save(fixUpTask);

		final Collection<FixUpTask> fixUpTasks = this.fixUpTaskService.findAll();

		Assert.isTrue(fixUpTasks.contains(saved));
	}
}
