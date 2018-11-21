
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import domain.Actor;
import domain.Administrator;
import domain.Box;
import domain.Customer;
import domain.HandyWorker;

@Service
@Transactional
public class AdministratorService {

	// Managed Repository ------------------------
	@Autowired
	private AdministratorRepository	administratorRepository;

	// Suporting services ------------------------

	@Autowired
	private BoxService				boxService;

	@Autowired
	private ActorService			actorService;


	// Simple CRUD methods -----------------------

	public Administrator create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(!(actor.getUserAccount().getAuthorities().toString().contains("ADMIN")));

		Administrator result;
		result = new Administrator();

		Box inBox, outBox, trashBox, spamBox;

		inBox = this.boxService.create();
		outBox = this.boxService.create();
		trashBox = this.boxService.create();
		spamBox = this.boxService.create();

		inBox.setName("inBox");
		outBox.setName("outBox");
		trashBox.setName("trashBox");
		spamBox.setName("spamBox");

		inBox.setByDefault(true);
		outBox.setByDefault(true);
		trashBox.setByDefault(true);
		spamBox.setByDefault(true);

		inBox.setActor(result);
		outBox.setActor(result);
		trashBox.setActor(result);
		spamBox.setActor(result);

		inBox = this.boxService.save(inBox);
		outBox = this.boxService.save(outBox);
		trashBox = this.boxService.save(trashBox);
		spamBox = this.boxService.save(spamBox);

		return result;

	}

	public Collection<Administrator> findAll() {

		Collection<Administrator> result;
		result = this.administratorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Administrator findOne(final int administratorId) {

		Assert.notNull(administratorId);
		Administrator result;
		result = this.administratorRepository.findOne(administratorId);
		Assert.notNull(result);
		return result;
	}

	public Administrator save(final Administrator administrator) {

		Assert.notNull(administrator);
		Administrator result;
		result = this.administratorRepository.save(administrator);
		return result;
	}

	// Other business methods -----------------------

	public Collection<Double> statsOfFixUpTasksPerCustomer() {

		final Collection<Double> result = this.administratorRepository.statsOfFixUpTasksPerCustomer();
		Assert.notNull(result);
		return result;

	}

	public Collection<Double> statsOfApplicationsPerFixUpTask() {

		final Collection<Double> result = this.administratorRepository.statsOfApplicationsPerFixUpTask();
		Assert.notNull(result);
		return result;

	}

	public Collection<Double> statsOfMaximumPricePerFixUpTask() {

		final Collection<Double> result = this.administratorRepository.statsOfMaximumPricePerFixUpTask();
		Assert.notNull(result);
		return result;
	}

	public Collection<Double> statsOfOfferedPricePerApplication() {

		final Collection<Double> result = this.administratorRepository.statsOfOfferedPricePerApplication();
		Assert.notNull(result);
		return result;
	}

	public Double ratioOfApplicationsPending() {

		final Double result = this.administratorRepository.ratioOfApplicationsPending();
		Assert.notNull(result);
		return result;
	}

	public Double ratioOfApplicationsAccepted() {

		final Double result = this.administratorRepository.ratioOfApplicationsAccepted();
		Assert.notNull(result);
		return result;
	}

	public Double ratioOfApplicationsRejected() {

		final Double result = this.administratorRepository.ratioOfApplicationsRejected();
		Assert.notNull(result);
		return result;
	}

	public Double ratioOfApplicationsPendingElapsedPeriod() {

		final Double result = this.administratorRepository.ratioOfApplicationsPendingElapsedPeriod();
		Assert.notNull(result);
		return result;
	}

	public Collection<Customer> customersTenPerCentMore() {

		final Collection<Customer> result = this.administratorRepository.customersTenPerCentMore();
		Assert.notNull(result);
		return result;
	}

	public Collection<HandyWorker> handyWorkersTenPerCentMore() {

		final Collection<HandyWorker> result = this.administratorRepository.handyWorkersTenPerCentMore();
		Assert.notNull(result);
		return result;

	}

	public Collection<Double> statsOfComplaintsPerFixUpTask() {

		final Collection<Double> result = this.administratorRepository.statsOfComplaintsPerFixUpTask();
		Assert.notNull(result);
		return result;

	}

	public Collection<Double> statsOfNotesPerReport() {

		final Collection<Double> result = this.administratorRepository.statsOfNotesPerReport();
		Assert.notNull(result);
		return result;

	}

	public Double ratioOfFixUpTasksWithComplaint() {

		final Double result = this.administratorRepository.ratioOfFixUpTasksWithComplaint();
		Assert.notNull(result);
		return result;

	}

	public Collection<Customer> topThreeCustomersComplaints() {

		final Collection<Customer> result = this.administratorRepository.topThreeCustomersComplaints();
		Assert.notNull(result);
		return result;

	}

	public Collection<HandyWorker> topThreeHandyWorkersComplaints() {

		final Collection<HandyWorker> result = this.administratorRepository.topThreeHandyWorkersComplaints();
		Assert.notNull(result);
		return result;

	}
}
