
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import security.Authority;
import domain.Actor;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	//Managed Repository

	@Autowired
	private ConfigurationRepository	configurationRepository;

	//Supporting services

	@Autowired
	private ActorService			actorService;


	//Simple CRUD methods

	public Configuration save(final Configuration c) {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authorityAdmin = new Authority();
		authorityAdmin.setAuthority(Authority.ADMIN);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authorityAdmin));

		final Configuration configuration = this.configurationRepository.save(c);

		Assert.notNull(configuration);

		return configuration;
	}

	public Configuration findOne(final int configurationId) {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authorityAdmin = new Authority();
		authorityAdmin.setAuthority(Authority.ADMIN);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authorityAdmin));

		final Configuration configuration = this.configurationRepository.findOne(configurationId);

		Assert.notNull(configuration);

		return configuration;
	}

	public Collection<Configuration> findAll() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authorityAdmin = new Authority();
		authorityAdmin.setAuthority(Authority.ADMIN);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authorityAdmin));

		final Collection<Configuration> configurations = this.configurationRepository.findAll();

		Assert.notNull(configurations);

		return configurations;
	}

	public Boolean spamContent(final String text) {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authorityAdmin = new Authority();
		authorityAdmin.setAuthority(Authority.ADMIN);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authorityAdmin));

		Boolean result = false;

		final Configuration config = new Configuration();

		final Collection<String> spamWords = config.getSpamWord();

		for (final String word : spamWords)
			if (text.contains(word)) {
				result = true;
				break;
			}

		return result;
	}
}
