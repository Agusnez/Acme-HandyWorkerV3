
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorsementRepository;
import security.Authority;
import domain.Actor;
import domain.Endorsement;

@Service
@Transactional
public class EndorsementService {

	// Managed repository -------------------------------------------

	@Autowired
	private EndorsementRepository	endorsementRepository;

	// Supporting services ------------------------------------------

	@Autowired
	private ActorService			actorService;


	// Simple CRUD methods ------------------------------------------

	public Endorsement create() {

		final Endorsement result = new Endorsement();
		final Collection<String> comments = new HashSet<>();
		result.setComments(comments);

		return result;

	}

	public Collection<Endorsement> findAll() {
		Assert.notNull(this.endorsementRepository);
		final Collection<Endorsement> result = this.endorsementRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	public Endorsement findOne(final int endorsementId) {
		Assert.isTrue(endorsementId != 0);
		final Endorsement result = this.endorsementRepository.findOne(endorsementId);
		Assert.notNull(result);
		return result;

	}

	public Endorsement save(final Endorsement endorsement) {

		Assert.notNull(endorsement);
		Endorsement result = null;

		if (endorsement.getId() == 0) {

			final Actor actor = this.actorService.findByPrincipal();
			final Authority authority = new Authority();
			authority.setAuthority(Authority.CUSTOMER);
			final Authority authority2 = new Authority();
			authority2.setAuthority(Authority.HANDYWORKER);

			Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority) || actor.getUserAccount().getAuthorities().contains(authority2));
			Assert.isTrue(!endorsement.getCreator().getUserAccount().getAuthorities().equals(endorsement.getRecipient().getUserAccount().getAuthorities()));

			final Date moment = new Date(System.currentTimeMillis() - 1000);
			endorsement.setMoment(moment);

			result = this.endorsementRepository.save(endorsement);

		} else {

			final Actor actor = this.actorService.findByPrincipal();
			Assert.isTrue(endorsement.getCreator().equals(actor));

			result = this.endorsementRepository.save(endorsement);
		}

		return result;

	}

	public void delete(final Endorsement endorsement) {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		final Actor owner = endorsement.getCreator();

		Assert.isTrue(actor.getId() == owner.getId());

		Assert.notNull(endorsement);
		Assert.isTrue(endorsement.getId() != 0);
		Assert.isTrue(this.endorsementRepository.exists(endorsement.getId()));

		this.endorsementRepository.delete(endorsement);

	}

	// Other business methods -----------------------------------------

}
