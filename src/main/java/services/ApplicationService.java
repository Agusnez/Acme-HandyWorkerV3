
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ApplicationRepository;
import domain.Application;

@Service
@Transactional
public class ApplicationService {

	//Managed repository---------------------------------
	@Autowired
	private ApplicationRepository	applicationRepository;


	//Suporting services---------------------------------

	//Simple CRUD methods--------------------------------
	public Application create() {
		return null;
	}

	public Collection<Application> findAll() {
		return null;
	}

	public Application findOne(final int applicationId) {
		return null;
	}

	public Application save(final Application application) {
		return null;
	}

	public void delete(final Application application) {

	}

	//Other business methods----------------------------

}
