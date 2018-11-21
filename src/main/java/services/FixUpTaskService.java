
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

		final FixUpTask result = new FixUpTask();

		return result;

	}

	public Collection<FixUpTask> findAll() {

		final Collection<FixUpTask> fixUpTasks = this.fixUpTaskRepository.findAll();

		Assert.notNull(fixUpTasks);

		return fixUpTasks;
	}

	public FixUpTask findOne(final int fixUpTaskID) {

		final FixUpTask fixUpTask = this.fixUpTaskRepository.findOne(fixUpTaskID);

		Assert.notNull(fixUpTask);

		return fixUpTask;

	}

	public FixUpTask save(final FixUpTask fixUpTask) {

		Assert.notNull(fixUpTask);

		final FixUpTask result = this.fixUpTaskRepository.save(fixUpTask);

		return result;

	}

	public void delete(final FixUpTask fixUpTask) {

		Assert.notNull(fixUpTask);
		Assert.isTrue(fixUpTask.getId() != 0);
		this.fixUpTaskRepository.delete(fixUpTask);
	}

	// Other business methods

}
