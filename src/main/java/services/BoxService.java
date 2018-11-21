
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoxRepository;
import domain.Actor;
import domain.Box;

@Service
@Transactional
public class BoxService {

	// Managed repository

	@Autowired
	private BoxRepository	boxRepository;

	// Suporting services

	@Autowired
	private ActorService	actorService;


	// Simple CRUD methods

	public Box create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		Box result;

		result = new Box();

		result.setActor(actor);

		result = this.save(result);

		return result;

	}

	public Collection<Box> findAll() {

		final Collection<Box> boxes = this.boxRepository.findAll();

		Assert.notNull(boxes);

		return boxes;

	}

	public Box findOne(final int boxID) {

		final Box box = this.boxRepository.findOne(boxID);

		Assert.notNull(box);

		return box;

	}

	public Box save(final Box box) {

		Assert.notNull(box);

		final Box result = this.boxRepository.save(box);

		return result;

	}

	public void delete(final Box box) {

		Assert.notNull(box);
		Assert.isTrue(box.getId() != 0);
		Assert.isTrue(box.getByDefault() == true);
		this.boxRepository.delete(box);

	}
	// Other business methods

}
