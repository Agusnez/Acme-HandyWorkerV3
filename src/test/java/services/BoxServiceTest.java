
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

	@Autowired
	private ActorService	actorservice;


	//Tests -------------------------------------------------------

	@Test
	public void testCreateBox() {

		Box box;
		box = this.boxservice.create();

		Assert.isNull(box.getName());
		Assert.isNull(box.getByDefault());
		Assert.isNull(box.getActor());

	}

	@Test
	public void testSaveBox() {

		super.authenticate("customer1");

		Box box, saved;
		Collection<Box> boxes;

		box = this.boxservice.create();
		box.setName("prueba 1");
		box.setByDefault(false);
		box.setActor(this.actorservice.findByPrincipal());

		saved = this.boxservice.save(box);
		boxes = this.boxservice.findAll();
		Assert.isTrue(boxes.contains(saved));

		super.authenticate(null);

	}

	@Test
	public void testSaveBoxNewActor() {

		this.createNewActorAndLogIn();

		Box box, saved;
		Collection<Box> boxes;

		box = this.boxservice.create();
		box.setName("prueba 1");
		box.setByDefault(false);
		box.setActor(this.actorservice.findByPrincipal());

		saved = this.boxservice.save(box);
		boxes = this.boxservice.findAll();
		Assert.isTrue(boxes.contains(saved));

		super.authenticate(null);

	}

	@Test
	public void test4DefaultBoxesNewActor() {

		this.createNewActorAndLogIn();

		Collection<Box> boxes;

		final int actorId = this.actorservice.findByPrincipal().getId();

		boxes = this.boxservice.findAllBoxByActor(actorId);
		Assert.isTrue(boxes.size() == 4);
		Assert.isTrue(boxes.contains(this.boxservice.findInBoxByActorId(actorId)));
		Assert.isTrue(boxes.contains(this.boxservice.findOutBoxByActorId(actorId)));
		Assert.isTrue(boxes.contains(this.boxservice.findTrashBoxByActorId(actorId)));
		Assert.isTrue(boxes.contains(this.boxservice.findSpamBoxByActorId(actorId)));

		super.authenticate(null);

	}

	@Test
	public void testDeleteBox() {

		super.authenticate("customer1");

		Box box, saved;
		Collection<Box> boxes;

		box = this.boxservice.create();
		box.setName("prueba 1");
		box.setByDefault(false);
		box.setActor(this.actorservice.findByPrincipal());

		saved = this.boxservice.save(box);
		boxes = this.boxservice.findAll();

		this.boxservice.delete(saved);
		boxes = this.boxservice.findAll();
		Assert.isTrue(!boxes.contains(saved));

		super.authenticate(null);

	}

	@Test
	public void testDeleteBoxDefault() {

		this.createNewActorAndLogIn();

		final int actorId = this.actorservice.findByPrincipal().getId();

		final Box defaultBox1 = this.boxservice.findInBoxByActorId(actorId);
		final Box defaultBox2 = this.boxservice.findOutBoxByActorId(actorId);
		final Box defaultBox3 = this.boxservice.findTrashBoxByActorId(actorId);
		final Box defaultBox4 = this.boxservice.findSpamBoxByActorId(actorId);

		Collection<Box> boxes;

		try {

			this.boxservice.delete(defaultBox1);
			this.boxservice.delete(defaultBox2);
			this.boxservice.delete(defaultBox3);
			this.boxservice.delete(defaultBox4);

		} catch (final Exception e) {

		}

		boxes = this.boxservice.findAll();
		Assert.isTrue(boxes.contains(defaultBox1));
		Assert.isTrue(boxes.contains(defaultBox2));
		Assert.isTrue(boxes.contains(defaultBox3));
		Assert.isTrue(boxes.contains(defaultBox4));

		super.authenticate(null);

	}

	@Test
	public void testFindInBoxByActor() {

		this.createNewActorAndLogIn();

		Box inBox;
		inBox = this.boxservice.findInBoxByActorId(this.actorservice.findByPrincipal().getId());

		Assert.isTrue(inBox.getName() == "inBox" && inBox.getByDefault());

		super.authenticate(null);

	}

	@Test
	public void testFindOutBoxByActor() {

		this.createNewActorAndLogIn();

		Box inBox;
		inBox = this.boxservice.findOutBoxByActorId(this.actorservice.findByPrincipal().getId());

		Assert.isTrue(inBox.getName() == "outBox" && inBox.getByDefault());

		super.authenticate(null);

	}

	@Test
	public void testFindTrashBoxByActor() {

		this.createNewActorAndLogIn();

		Box inBox;
		inBox = this.boxservice.findTrashBoxByActorId(this.actorservice.findByPrincipal().getId());

		Assert.isTrue(inBox.getName() == "trashBox" && inBox.getByDefault());

		super.authenticate(null);

	}

	@Test
	public void testFindSpamBoxByActor() {

		this.createNewActorAndLogIn();

		Box inBox;
		inBox = this.boxservice.findSpamBoxByActorId(this.actorservice.findByPrincipal().getId());

		Assert.isTrue(inBox.getName() == "spamBox" && inBox.getByDefault());

		super.authenticate(null);

	}

	@Test
	public void testFindOneBox() {

		super.authenticate("customer1");

		Box box, saved, finded;
		Collection<Box> boxes;

		box = this.boxservice.create();
		box.setName("prueba 1");
		box.setByDefault(false);
		box.setActor(this.actorservice.findByPrincipal());

		saved = this.boxservice.save(box);
		boxes = this.boxservice.findAll();
		Assert.isTrue(boxes.contains(saved));

		super.authenticate(null);

		finded = this.boxservice.findOne(saved.getId());
		Assert.isTrue(saved == finded);

	}

	@Test
	public void testFindAllBoxByActor() {

		this.createNewActorAndLogIn();

		Box box, saved;
		Collection<Box> boxes;

		final int actorId = this.actorservice.findByPrincipal().getId();

		box = this.boxservice.create();
		box.setName("prueba 1");
		box.setByDefault(false);
		box.setActor(this.actorservice.findByPrincipal());

		saved = this.boxservice.save(box);
		boxes = this.boxservice.findAllBoxByActor(actorId);
		Assert.isTrue(boxes.size() == 5);
		Assert.isTrue(boxes.contains(saved));
		Assert.isTrue(boxes.contains(this.boxservice.findInBoxByActorId(actorId)));
		Assert.isTrue(boxes.contains(this.boxservice.findOutBoxByActorId(actorId)));
		Assert.isTrue(boxes.contains(this.boxservice.findTrashBoxByActorId(actorId)));
		Assert.isTrue(boxes.contains(this.boxservice.findSpamBoxByActorId(actorId)));

		super.authenticate(null);

	}

	private void createNewActorAndLogIn() {

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

		saved1 = this.customerservice.save(customer);
		customers = this.customerservice.findAll();
		Assert.isTrue(customers.contains(saved1));

		super.authenticate("Gustavito");

	}

}
