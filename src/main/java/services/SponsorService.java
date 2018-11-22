
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {

	//Managed repository---------------------------------
	@Autowired
	private SponsorRepository	sponsorRepository;


	//Suporting services---------------------------------

	//Simple CRUD methods--------------------------------
	public Sponsor create() { //Debe no estar autenticado?  
		Sponsor result;
		result = new Sponsor();
		return result;
	}

	public Collection<Sponsor> findAll() {
		Collection<Sponsor> result;
		result = this.sponsorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Sponsor findOne(final int sponsorId) {
		Sponsor sponsor;
		sponsor = this.sponsorRepository.findOne(sponsorId);
		Assert.notNull(sponsor);
		return sponsor;
	}

	public Sponsor save(final Sponsor sponsor) {
		Assert.notNull(sponsor);
		Sponsor result;
		result = this.sponsorRepository.save(sponsor);
		return result;
	}

	//Other business methods----------------------------

	public Sponsor findByPrincipal() {
		Sponsor s;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		s = this.findByUserAccount(userAccount);
		Assert.notNull(s);

		return s;
	}

	public Sponsor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Sponsor result;

		result = this.sponsorRepository.findByUserAccountId(userAccount.getId());

		return result;
	}
}
