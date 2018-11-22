
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import domain.Actor;
import domain.Administrator;
import domain.Box;

@Service
@Transactional
public class AdministratorService {

	// Managed Repository ------------------------
	@Autowired
	private AdministratorRepository	administratorRepository;

	// Suporting services ------------------------

	@Autowired
	private BoxService				boxService;

	@Autowired
	private ActorService			actorService;


	// Simple CRUD methods -----------------------

	public Administrator create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(!(actor.getUserAccount().getAuthorities().toString().contains("ADMIN")));

		Administrator result;
		result = new Administrator();

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

		inBox = this.boxService.save(inBox);
		outBox = this.boxService.save(outBox);
		trashBox = this.boxService.save(trashBox);
		spamBox = this.boxService.save(spamBox);

		return result;

	}

	public Collection<Administrator> findAll() {

		Collection<Administrator> result;
		result = this.administratorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Administrator findOne(final int administratorId) {

		Assert.notNull(administratorId);
		Administrator result;
		result = this.administratorRepository.findOne(administratorId);
		Assert.notNull(result);
		return result;
	}

	public Administrator save(final Administrator administrator) {

		Assert.notNull(administrator);
		Administrator result;
		result = this.administratorRepository.save(administrator);
		return result;
	}

	// Other business methods -----------------------
}
