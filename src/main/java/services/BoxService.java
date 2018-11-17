
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

		return null;

	}

	public Collection<Box> findAll() {

		return null;

	}

	public Box findOne(final int boxID) {

		return null;

	}

	public Box save(final Box box) {

		return null;

	}

	public void delete(final Box box) {

	}

	// Other business methods

}
