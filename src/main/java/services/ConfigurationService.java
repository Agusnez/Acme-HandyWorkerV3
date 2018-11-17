
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ConfigurationRepository;

@Service
@Transactional
public class ConfigurationService {

	//Managed Repository

	@Autowired
	private ConfigurationRepository	configurationRepository;

	//Supporting services

	//Simple CRUD methods

}
