
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.LoginService;
import domain.Application;

@Service
@Transactional
public class ApplicationService {

	//Managed repository---------------------------------
	@Autowired
	private ApplicationRepository	applicationRepository;


	//Suporting services---------------------------------

	//Simple CRUD methods--------------------------------
	public Application create() {//Comprobar que es el handy worker o el customer
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

	public Application save(final Application application) {//Comprobar que es el handy worker o el customer
		Assert.notNull(application);
		Application app;
		app = this.applicationRepository.save(application);
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

	//Un customer actualiza una Application
	public Application customerUpdate(final Application application) {

		Assert.notNull(application);
		Assert.isTrue(application.getStatus() == "PENDING");
		Assert.isTrue(this.applicationRepository.exists(application.getId()));
		//Assert.isTrue(application.getStatus() != "ACCEPTED" || application.getCreditCard() !=null); //FALTA EL CREDIT CARD
		final int id = LoginService.getPrincipal().getId();
		//int customerId = application.getFixUpTask().getCustomer().getId();
		//Assert.isTrue(id==customerId);

		final Collection<Application> c = application.getFixUpTask().getApplications();
		c.remove(application);
		if (application.getStatus() == "ACCEPTED")
			for (final Application application2 : c) {
				application2.setStatus("REJECTED");
				this.applicationRepository.save(application2);
			}
		return this.applicationRepository.save(application);

	}

}
