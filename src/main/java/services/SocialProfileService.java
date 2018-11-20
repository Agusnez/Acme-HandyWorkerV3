
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SocialProfileRepository;
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfileService {

	// Managed repository

	@Autowired
	private SocialProfileRepository	socialProfileRepository;


	// Suporting services

	// Simple CRUD methods

	public SocialProfile create() {

		final SocialProfile result = new SocialProfile();

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

	public SocialProfile save(final SocialProfile s) {

		final SocialProfile socialProfile = this.socialProfileRepository.save(s);

		Assert.notNull(socialProfile);

		return socialProfile;

	}

	public void delete(final SocialProfile socialProfile) {

		Assert.notNull(socialProfile);
		Assert.isTrue(socialProfile.getId() != 0);
		this.socialProfileRepository.delete(socialProfile);
	}

	// Other business methods	

}
