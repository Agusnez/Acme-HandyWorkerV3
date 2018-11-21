
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	//Managed Repository

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;


	//Supporting services

	//Simple CRUD methods

	public Sponsorship create() {

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

		final Sponsorship result = this.sponsorshipRepository.save(sponsorship);

		return result;
	}

}
