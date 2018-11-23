
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import security.Authority;
import domain.Actor;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	//Managed Repository

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	//Supporting services

	@Autowired
	private ActorService			actorService;


	//Simple CRUD methods

	public Sponsorship create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);
		Assert.isTrue(!(actor.getUserAccount().getAuthorities().contains(authority)));

		final Sponsorship result;

		result = new Sponsorship();

		return result;
	}

	public Collection<Sponsorship> findAll() {

		final Collection<Sponsorship> sponsorships = this.sponsorshipRepository.findAll();

		Assert.notNull(sponsorships);

		return sponsorships;
	}

	public Sponsorship findOne(final int sponsorshipId) {

		final Sponsorship sponsorship = this.sponsorshipRepository.findOne(sponsorshipId);

		Assert.notNull(sponsorship);

		return sponsorship;
	}

	public Sponsorship save(final Sponsorship sponsorship) {

		Assert.notNull(sponsorship);
		final Sponsor owner = sponsorship.getSponsor();
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		Assert.isTrue(actor.getId() == owner.getId());

		final Sponsorship result = this.sponsorshipRepository.save(sponsorship);

		return result;
	}

}
