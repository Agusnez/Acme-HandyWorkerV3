
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

		return null;

	}

	public Collection<SocialProfile> findAll() {

		return null;

	}

	public SocialProfile findOne(final int socialProfileID) {

		return null;

	}

	public SocialProfile save(final SocialProfile socialProfile) {

		return null;

	}

	public void delete(final SocialProfile socialProfile) {

	}

	// Other business methods	

}
