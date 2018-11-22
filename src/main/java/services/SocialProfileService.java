
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SocialProfileRepository;
import domain.Actor;
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfileService {

	// Managed repository

	@Autowired
	private SocialProfileRepository	socialProfileRepository;

	// Suporting services

	@Autowired
	private ActorService			actorService;


	// Simple CRUD methods

	public SocialProfile create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		SocialProfile result;

		result = new SocialProfile();

		result.setActor(actor);

		result = this.save(result);

		return result;

	}

	public Collection<SocialProfile> findAll() {

		final Collection<SocialProfile> socialProfiles = this.socialProfileRepository.findAll();

		Assert.notNull(socialProfiles);

		return socialProfiles;
	}

	public SocialProfile findOne(final int socialProfileID) {

		final SocialProfile socialProfile = this.socialProfileRepository.findOne(socialProfileID);

		Assert.notNull(socialProfile);

		return socialProfile;

	}

	public SocialProfile save(final SocialProfile socialProfile) {

		Assert.notNull(socialProfile);

		final SocialProfile result = this.socialProfileRepository.save(socialProfile);

		return result;

	}

	// Other business methods	

}
