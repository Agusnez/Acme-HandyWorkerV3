
package services;

import java.util.ArrayList;
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

		Box result;

		result = new Box();

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

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		final Actor owner = box.getActor();

		Assert.isTrue(actor.getId() == owner.getId());

		Assert.isTrue(!box.getByDefault());

		Assert.notNull(box);

		final Box result = this.boxRepository.save(box);

		return result;

	}

	public Box saveNewActor(final Box box) {

		Assert.notNull(box);

		final Box result = this.boxRepository.save(box);

		return result;

	}

	public void delete(final Box box) {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		final Actor owner = box.getActor();

		Assert.isTrue(actor.getId() == owner.getId());

		Assert.notNull(box);
		Assert.isTrue(box.getId() != 0);
		Assert.isTrue(box.getByDefault() == false);
		this.boxRepository.delete(box);

	}
	// Other business methods

	public Box findTrashBoxByActorId(final int actorId) {
		Box result;
		result = this.boxRepository.findTrashBoxByActorId(actorId);
		return result;
	}

	public Box findInBoxByActorId(final int actorId) {
		Box result;
		result = this.boxRepository.findInBoxByActorId(actorId);
		return result;
	}

	public Box findOutBoxByActorId(final int actorId) {
		Box result;
		result = this.boxRepository.findOutBoxByActorId(actorId);
		return result;
	}

	public Box findSpamBoxByActorId(final int actorId) {
		Box result;
		result = this.boxRepository.findSpamBoxByActorId(actorId);
		return result;
	}

	public Collection<Box> findAllBoxByActor(final int actorId) {
		Collection<Box> boxes = new ArrayList<Box>();
		boxes = this.boxRepository.findAllBoxByActorId(actorId);
		return boxes;
	}

}
