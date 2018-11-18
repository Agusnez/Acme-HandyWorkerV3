
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	//Managed Repository

	@Autowired
	private ActorRepository	actorRepository;


	//Supporting services

	//Simple CRUD methods

	public Collection<Actor> findAll() {

		final Collection<Actor> actors = this.actorRepository.findAll();

		Assert.notNull(actors);

		return actors;
	}

	public Actor finOne(final int ActorId) {

		final Actor actor = this.actorRepository.findOne(ActorId);

		Assert.notNull(actor);

		return actor;
	}

	public Actor save(final Actor a) {

		final Actor actor = this.actorRepository.save(a);

		Assert.notNull(actor);

		return actor;
	}
}
