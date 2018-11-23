
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Actor;
import domain.Finder;
import domain.HandyWorker;

@Service
@Transactional
public class FinderService {

	// Managed repository -------------------------------------------

	@Autowired
	private FinderRepository	finderRepository;

	// Supporting services ------------------------------------------

	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods ------------------------------------------

	public Finder create() {

		Finder result;

		result = new Finder();

		return result;

	}

	public Collection<Finder> findAll() {

		final Collection<Finder> result = this.finderRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	public Finder findOne(final int finderId) {

		final Finder result = this.finderRepository.findOne(finderId);
		Assert.notNull(result);
		return result;

	}

	public Finder save(final Finder finder) {

		Assert.notNull(finder);
		final Actor handyWorker = this.actorService.findByPrincipal();
		Assert.notNull(handyWorker);
		final HandyWorker owner = finder.getHandyWorker();
		Assert.notNull(owner);
		Assert.isTrue(handyWorker.getId() == owner.getId());

		final Finder result = this.finderRepository.save(finder);

		return result;

	}

	// Other business methods -----------------------------------------

}
