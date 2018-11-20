
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoxRepository;
import domain.Box;

@Service
@Transactional
public class BoxService {

	// Managed repository

	@Autowired
	private BoxRepository	boxRepository;


	// Suporting services

	// Simple CRUD methods

	public Box create() {

		final Box result = new Box();

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

	public Box save(final Box s) {

		final Box box = this.boxRepository.save(s);

		Assert.notNull(box);

		return box;

	}

	public void delete(final Box box) {

		this.boxRepository.delete(box);

	}

	// Other business methods

}
