package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import domain.Referee;

@Service
@Transactional
public class RefereeService {
	// Managed repository -------------------------------------------

	@Autowired
	private RefereeRepository refereeRepository;

	// Supporting services ------------------------------------------

	// Simple CRUD methods ------------------------------------------

	public Referee create() {

		return new Referee();

	}

	public Collection<Referee> findAll() {
		Assert.notNull(this.refereeRepository);
		Collection<Referee> result = this.refereeRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	public Referee findOne(final int refereeId) {
		Assert.isTrue(refereeId != 0);
		Referee result = this.refereeRepository.findOne(refereeId);
		Assert.notNull(result);
		return result;

	}

	public Referee save(final Referee referee) {
		Assert.notNull(referee);

		return this.refereeRepository.save(referee);

	}

	public void delete(final Referee referee) {
		Assert.notNull(referee);
		Assert.isTrue(referee.getId() != 0);
		Assert.isTrue(this.refereeRepository.exists(referee.getId()));
		this.refereeRepository.delete(referee);
	}

	// Other business methods -----------------------------------------

}
