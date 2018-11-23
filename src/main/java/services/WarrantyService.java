
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.WarrantyRepository;
import security.Authority;
import domain.Actor;
import domain.Warranty;

@Service
@Transactional
public class WarrantyService {

	// Managed Repository ------------------------
	@Autowired
	private WarrantyRepository	warrantyRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	// Simple CRUD methods -----------------------

	public Warranty create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(!(actor.getUserAccount().getAuthorities().contains(authority)));

		Warranty result;

		result = new Warranty();

		return result;
	}

	public Collection<Warranty> findAll() {

		Collection<Warranty> result;

		result = this.warrantyRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Warranty findOne(final int warrantyId) {

		Warranty result;

		result = this.warrantyRepository.findOne(warrantyId);

		return result;
	}

	public Warranty save(final Warranty warranty) {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(!(actor.getUserAccount().getAuthorities().contains(authority)));

		Assert.notNull(warranty);
		Assert.isTrue(warranty.getFinalMode() == false);
		Warranty result;

		result = this.warrantyRepository.save(warranty);
		return result;
	}

	public void delete(final Warranty warranty) {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(!(actor.getUserAccount().getAuthorities().contains(authority)));

		Assert.notNull(warranty);
		Assert.isTrue(warranty.getId() != 0);
		Assert.isTrue(warranty.getFinalMode() == false);

		final Integer references = this.fixUpTaskService.countFixUpTaskByWarrantyId(warranty.getId());

		Assert.isTrue(references > 0);

		this.warrantyRepository.delete(warranty);

	}

	// Other business methods -----------------------

}
