
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

	@Test
	public void testEditBox() {

		this.createNewActorAndLogIn();

		Box box, saved, changed, saved1;
		Collection<Box> boxes;

		box = this.boxservice.create();
		box.setName("prueba 1");
		box.setByDefault(false);
		box.setActor(this.actorservice.findByPrincipal());

		saved = this.boxservice.save(box);
		boxes = this.boxservice.findAll();
		Assert.isTrue(boxes.contains(saved));
		Assert.isTrue(saved.getName() == "prueba 1");

		changed = saved;
		changed.setName("prueba 2");

		saved1 = this.boxservice.save(changed);
		boxes = this.boxservice.findAll();
		Assert.isTrue(boxes.contains(saved1));
		Assert.isTrue(saved1.getName() == "prueba 2");

		super.authenticate(null);

	}

	@Test
	public void testEditBoxDefault() {

		this.createNewActorAndLogIn();

		Collection<Box> boxes;

		boxes = this.boxservice.findAllBoxByActor(this.actorservice.findByPrincipal().getId());

		Integer i = 1;

		for (final Box box : boxes) {
			final Box saved = box;
			final String nombre = "Caja " + i;
			box.setName(nombre);
			i++;

			try {

				this.boxservice.save(box);

			} catch (final Exception e) {

			}

			Assert.isTrue(saved.getName() == this.boxservice.findOne(saved.getId()).getName());
		}

		super.authenticate(null);

	}

	@Test
	public void testEditExternalBox() {

		this.createNewActorAndLogIn();

		Box box, saved, changed;
		Collection<Box> boxes;

		box = this.boxservice.create();
		box.setName("prueba 1");
		box.setByDefault(false);
		box.setActor(this.actorservice.findByPrincipal());

		saved = this.boxservice.save(box);
		boxes = this.boxservice.findAll();
		Assert.isTrue(boxes.contains(saved));

		super.authenticate("customer1");

		changed = saved;
		changed.setName("prueba 2");

		try {

			this.boxservice.save(changed);

		} catch (final Exception e) {

		}

		Assert.isTrue(saved.getName() == this.boxservice.findOne(saved.getId()).getName());

		super.authenticate(null);

	}

	private void createNewActorAndLogIn() {

		Customer customer, saved1;
		Collection<Customer> customers;

		customer = this.customerservice.create();
		customer.setName("González");
		customer.setMiddleName("Adolfo");
		customer.setSurname("Gustavo");
		customer.setAddress("Calle Almoralejo");

		final UserAccount userAccount = customer.getUserAccount();
		userAccount.setUsername("Gustavito");
		userAccount.setPassword("123123");

		customer.setUserAccount(userAccount);

		saved1 = this.customerservice.save(customer);
		customers = this.customerservice.findAll();
		Assert.isTrue(customers.contains(saved1));

		super.authenticate("Gustavito");

	}

}
