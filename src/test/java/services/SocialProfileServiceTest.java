
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
import domain.Customer;
import domain.SocialProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SocialProfileServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private SocialProfileService	socialprofileservice;

	@Autowired
	private ActorService			actorservice;

	@Autowired
	private CustomerService			customerservice;


	//Tests -------------------------------------------------------

	@Test
	public void testCreate() {

		SocialProfile socialProfile;

		socialProfile = this.socialprofileservice.create();

		Assert.isNull(socialProfile.getActor());
		Assert.isNull(socialProfile.getLink());
		Assert.isNull(socialProfile.getNick());
		Assert.isNull(socialProfile.getSocialName());

	}

	@Test
	public void testSave() {

		super.authenticate("customer1");

		SocialProfile socialProfile, saved;
		Collection<SocialProfile> socialProfiles;

		socialProfile = this.socialprofileservice.create();
		socialProfile.setActor(this.actorservice.findByPrincipal());
		socialProfile.setLink("https://github.com/xxx");
		socialProfile.setNick("Ornitorrinco");
		socialProfile.setSocialName("Otorrino");

		saved = this.socialprofileservice.save(socialProfile);
		socialProfiles = this.socialprofileservice.findAll();
		Assert.isTrue(socialProfiles.contains(saved));

		super.authenticate(null);
	}

	@Test
	public void testFindOne() {

		super.authenticate("customer1");

		SocialProfile socialProfile, saved, finded;
		Collection<SocialProfile> socialProfiles;

		socialProfile = this.socialprofileservice.create();
		socialProfile.setActor(this.actorservice.findByPrincipal());
		socialProfile.setLink("https://github.com/xxx");
		socialProfile.setNick("Fernand");
		socialProfile.setSocialName("Fernand");

		saved = this.socialprofileservice.save(socialProfile);
		socialProfiles = this.socialprofileservice.findAll();
		Assert.isTrue(socialProfiles.contains(saved));

		finded = this.socialprofileservice.findOne(saved.getId());

		Assert.isTrue(saved == finded);

		super.authenticate(null);

	}

	@Test
	public void testFindAllByActor() {

		this.createNewActorAndLogIn();

		SocialProfile socialProfile1;
		final SocialProfile socialProfile2;
		SocialProfile saved1;
		final SocialProfile saved2;
		Collection<SocialProfile> socialProfiles;

		socialProfile1 = this.socialprofileservice.create();
		socialProfile1.setActor(this.actorservice.findByPrincipal());
		socialProfile1.setLink("https://github.com/xxx");
		socialProfile1.setNick("Fernand");
		socialProfile1.setSocialName("Fernand");

		socialProfile2 = this.socialprofileservice.create();
		socialProfile2.setActor(this.actorservice.findByPrincipal());
		socialProfile2.setLink("https://github.com/yyy");
		socialProfile2.setNick("Jordi");
		socialProfile2.setSocialName("Jordi");

		saved1 = this.socialprofileservice.save(socialProfile1);
		saved2 = this.socialprofileservice.save(socialProfile2);

		socialProfiles = this.socialprofileservice.findAllByActor(this.actorservice.findByPrincipal().getId());

		Assert.isTrue(socialProfiles.contains(saved1));
		Assert.isTrue(socialProfiles.contains(saved2));

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
