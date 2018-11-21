
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
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


	// Simple CRUD methods ------------------------------------------

	public Referee create() {

		Referee result;
		result = new Referee();

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

		final Referee result = this.refereeRepository.save(referee);

		return result;

	}

	// Other business methods -----------------------------------------

}
