
package services;

import java.util.Collection;

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
import domain.Referee;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class RefereeServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private RefereeService	refereeService;


	//Tests -------------------------------------------------------
	// TODO Referee testing

	@Test
	public void testCreate() {

		this.authenticate("admin");

		final Referee referee = this.refereeService.create();

		Assert.isNull(referee.getAddress());
		Assert.isNull(referee.getEmail());
		Assert.isNull(referee.getMiddleName());
		Assert.isNull(referee.getName());
		Assert.isNull(referee.getPhone());
		Assert.isNull(referee.getPhoto());
		Assert.isNull(referee.getSurname());
		Assert.isNull(referee.getSuspicious());
		Assert.isNull(referee.getUserAccount().getUsername());
		Assert.isNull(referee.getUserAccount().getPassword());

		final Authority authority = new Authority();
		authority.setAuthority(Authority.REFEREE);
		Assert.isTrue(referee.getUserAccount().getAuthorities().contains(authority));

		super.authenticate(null);

	}

	@Test
	public void testSave() {

		this.authenticate("admin");

		this.createNewActorAndLogIn();

		super.authenticate(null);

	}

	@Test
	public void testFindOne() {

		this.authenticate("admin");

		final Referee referee = this.createNewActorAndLogIn();

		final Referee finded = this.refereeService.findOne(referee.getId());

		Assert.isTrue(finded.equals(referee));

		super.authenticate(null);

	}

	private Referee createNewActorAndLogIn() {

		Referee referee, saved1;
		Collection<Referee> referees;

		referee = this.refereeService.create();
		referee.setName("González");
		referee.setMiddleName("Adolfo");
		referee.setSurname("Gustavo");
		referee.setAddress("Calle Almoralejo");

		final UserAccount userAccount = referee.getUserAccount();
		userAccount.setUsername("Gustavito");
		userAccount.setPassword("123123");

		referee.setUserAccount(userAccount);

		saved1 = this.refereeService.save(referee);
		referees = this.refereeService.findAll();
		Assert.isTrue(referees.contains(saved1));

		super.authenticate("Gustavito");

		return saved1;

	}
}
