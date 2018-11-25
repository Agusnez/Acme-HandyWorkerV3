
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.Authority;
import domain.Actor;
import domain.Application;
import domain.Customer;
import domain.FixUpTask;

@Service
@Transactional
public class ApplicationService {

	//Managed repository---------------------------------
	@Autowired
	private ApplicationRepository	applicationRepository;

	//Suporting services---------------------------------
	@Autowired
	private ActorService			actorService;

	@Autowired
	private CustomerService			customerService;


	//Simple CRUD methods--------------------------------
	public Application create() {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		Application result;
		result = new Application();
		return result;
	}

	public Collection<Application> findAll() {
		Collection<Application> result;
		result = this.applicationRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Application findOne(final int applicationId) {
		Application application;
		application = this.applicationRepository.findOne(applicationId);
		Assert.notNull(application);
		return application;
	}

	public Application save(final Application application) {
		Assert.notNull(application);
		Application app;

		if (application.getId() == 0) {
			final Actor actor = this.actorService.findByPrincipal();
			Assert.notNull(actor);
			final Authority authority = new Authority();
			authority.setAuthority(Authority.HANDYWORKER);
			Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

			application.setStatus("PENDING");
			final Date now = new Date();
			application.setMoment(now);

			app = this.applicationRepository.save(application);
		} else {
			final Actor actor = this.actorService.findByPrincipal();
			Assert.notNull(actor);
			final Authority authority = new Authority();
			authority.setAuthority(Authority.CUSTOMER);
			Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

			Assert.notNull(application);
			Assert.isTrue(application.getStatus() == "PENDING");
			Assert.isTrue(this.applicationRepository.exists(application.getId()));
			Assert.isTrue(application.getStatus() != "ACCEPTED" || application.getCreditCard() != null);
			final Customer customer = this.customerService.findByPrincipal();
			final FixUpTask fixUp = application.getFixUpTask();
			Assert.isTrue(customer.getFixUpTasks().contains(fixUp));

			final Collection<Application> c = application.getFixUpTask().getApplications();
			c.remove(application);
			if (application.getStatus() == "ACCEPTED")
				for (final Application application2 : c) {
					application2.setStatus("REJECTED");
					this.applicationRepository.save(application2);
				}
			app = this.applicationRepository.save(application);

		}
		return app;
	}

	//Other business methods----------------------------
	public Collection<Double> statsOfOfferedPricePerApplication() {

		final Collection<Double> result = this.applicationRepository.statsOfOfferedPricePerApplication();
		Assert.notNull(result);
		return result;
	}

	public Double ratioOfApplicationsPending() {

		final Double result = this.applicationRepository.ratioOfApplicationsPending();
		Assert.notNull(result);
		return result;
	}

	public Double ratioOfApplicationsAccepted() {
		final Double result = this.applicationRepository.ratioOfApplicationsAccepted();
		Assert.notNull(result);
		return result;
	}

	public Double ratioOfApplicationsRejected() {

		final Double result = this.applicationRepository.ratioOfApplicationsRejected();
		Assert.notNull(result);
		return result;
	}

	public Double ratioOfApplicationsPendingElapsedPeriod() {

		final Double result = this.applicationRepository.ratioOfApplicationsPendingElapsedPeriod();
		Assert.notNull(result);
		return result;
	}
	//Devuelve las applications dado un Customer
	public Collection<Application> findApplicationsByCustomer(final int customerId) {
		final Collection<Application> result = this.applicationRepository.findApplicationsByCustomer(customerId);
		Assert.notNull(result);
		return result;

	}
	//Devuelve las applications dado un HandyWorker
	public Collection<Application> findApplicationsByHandyWorker(final int handyWorkerId) {
		final Collection<Application> result = this.applicationRepository.findApplicationsByHandyWorker(handyWorkerId);
		Assert.notNull(result);
		return result;

	}

	public Application findApplicationByFixUpTaskId(final int fixUpTaskId) {
		final Application result = this.applicationRepository.findApplicationByFixUpTaskId(fixUpTaskId);
		Assert.notNull(result);
		return result;
	}

}
