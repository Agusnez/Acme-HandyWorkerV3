
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Box;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {

	//Managed repository---------------------------------
	@Autowired
	private SponsorRepository	sponsorRepository;

	//Suporting services---------------------------------

	@Autowired
	private BoxService			boxService;

	@Autowired
	private ActorService		actorService;


	//Simple CRUD methods--------------------------------
	public Sponsor create() { //Debe no estar autenticado?  
		Sponsor result;
		result = new Sponsor();
		return result;
	}

	public Collection<Sponsor> findAll() {
		Collection<Sponsor> result;
		result = this.sponsorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Sponsor findOne(final int sponsorId) {
		Sponsor sponsor;
		sponsor = this.sponsorRepository.findOne(sponsorId);
		Assert.notNull(sponsor);
		return sponsor;
	}

	public Sponsor save(final Sponsor sponsor) {
		Assert.notNull(sponsor);

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		Assert.isTrue(actor.getId() == sponsor.getId());

		Sponsor result;
		result = this.sponsorRepository.save(sponsor);

		if (sponsor.getId() == 0) {
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

	//Other business methods----------------------------

	public Sponsor findByPrincipal() {
		Sponsor s;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		s = this.findByUserAccount(userAccount);
		Assert.notNull(s);

		return s;
	}

	public Sponsor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Sponsor result;

		result = this.sponsorRepository.findByUserAccountId(userAccount.getId());

		return result;
	}
}
