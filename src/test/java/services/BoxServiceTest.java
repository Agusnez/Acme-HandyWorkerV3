
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
import domain.Box;
import domain.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class BoxServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private BoxService		boxservice;

	@Autowired
	private CustomerService	customerservice;


	//Tests -------------------------------------------------------

	@Test
	public void testSaveBox() {

		final Authority authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);
		final List<Authority> list = new ArrayList<Authority>();
		list.add(authority);

		final UserAccount userAccount = new UserAccount();
		userAccount.setAuthorities(list);
		userAccount.setUsername("Gustavito");
		userAccount.setPassword("123123");

		Customer customer, saved1;
		Collection<Customer> customers;

		customer = this.customerservice.create();
		customer.setName("González");
		customer.setMiddleName("Adolfo");
		customer.setSurname("Gustavo");
		customer.setAddress("Calle Almoralejo");
		customer.setUserAccount(userAccount);

		super.authenticate("test");

		saved1 = this.customerservice.save(customer);
		customers = this.customerservice.findAll();
		Assert.isTrue(customers.contains(saved1));

		super.authenticate(saved1.getUserAccount().getUsername());

		Box box, saved;
		Collection<Box> boxes;

		box = this.boxservice.create();
		box.setName("prueba 1");
		box.setByDefault(false);
		box.setActor(customer);

		saved = this.boxservice.save(box);
		boxes = this.boxservice.findAll();
		Assert.isTrue(boxes.contains(saved));

		super.authenticate(null);

	}
}
