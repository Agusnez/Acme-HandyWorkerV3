
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Box;
import domain.Referee;

@Service
@Transactional
public class RefereeService {

	// Managed repository -------------------------------------------

	@Autowired
	private RefereeRepository	refereeRepository;

	// Supporting services ------------------------------------------

	@Autowired
	private BoxService			boxService;

	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods ------------------------------------------

	public Referee create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authorityAdmin = new Authority();
		authorityAdmin.setAuthority(Authority.ADMIN);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authorityAdmin));

		Referee result;
		result = new Referee();

		final Authority authorityReferee = new Authority();
		authorityReferee.setAuthority(Authority.REFEREE);
		final List<Authority> list = new ArrayList<Authority>();
		list.add(authorityReferee);

		final UserAccount userAccount = new UserAccount();
		userAccount.setAuthorities(list);
		result.setUserAccount(userAccount);

		return result;

	}

	public Collection<Referee> findAll() {

		final Collection<Referee> result = this.refereeRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	public Referee findOne(final int refereeId) {

		final Referee result = this.refereeRepository.findOne(refereeId);
		Assert.notNull(result);
		return result;

	}

	public Referee save(final Referee referee) {

		Assert.notNull(referee);

		if (referee.getId() != 0) {

			final Actor actor = this.actorService.findByPrincipal();
			Assert.notNull(actor);

			Assert.isTrue(actor.getId() == referee.getId());

		}

		final Referee result = this.refereeRepository.save(referee);

		if (referee.getId() == 0) {
			Box inBox, outBox, trashBox, spamBox;

			inBox = this.boxService.create();
			outBox = this.boxService.create();
			trashBox = this.boxService.create();
			spamBox = this.boxService.create();

			inBox.setName("inBox");
			outBox.setName("outBox");
			trashBox.setName("trashBox");
			spamBox.setName("spamBox");

			inBox.setByDefault(true);
			outBox.setByDefault(true);
			trashBox.setByDefault(true);
			spamBox.setByDefault(true);

			inBox.setActor(result);
			outBox.setActor(result);
			trashBox.setActor(result);
			spamBox.setActor(result);

			final Collection<Box> boxes = new ArrayList<>();
			boxes.add(spamBox);
			boxes.add(trashBox);
			boxes.add(inBox);
			boxes.add(outBox);

			inBox = this.boxService.saveNewActor(inBox);
			outBox = this.boxService.saveNewActor(outBox);
			trashBox = this.boxService.saveNewActor(trashBox);
			spamBox = this.boxService.saveNewActor(spamBox);

		}

		return result;

	}

	// Other business methods -----------------------------------------

	public Referee findByPrincipal() {
		Referee referee;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		referee = this.findByUserAccount(userAccount);
		Assert.notNull(referee);

		return referee;
	}

	public Referee findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Referee result;

		result = this.refereeRepository.findByUserAccountId(userAccount.getId());

		return result;
	}
}
