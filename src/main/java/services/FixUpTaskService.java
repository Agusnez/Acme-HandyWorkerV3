
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.FixUpTaskRepository;
import domain.FixUpTask;

@Service
@Transactional
public class FixUpTaskService {

	// Managed repository

	@Autowired
	private FixUpTaskRepository	fixUpTaskRepository;


	// Suporting services

	// Simple CRUD methods

	public FixUpTask create() {

		return null;

	}

	public Collection<FixUpTask> findAll() {

		return null;

	}

	public FixUpTask findOne(final int fixUpTaskID) {

		return null;

	}

	public FixUpTask save(final FixUpTask fixUpTask) {

		return null;

	}

	public void delete(final FixUpTask fixUpTask) {

	}

	// Other business methods

}
