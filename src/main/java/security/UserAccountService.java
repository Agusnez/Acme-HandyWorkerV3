/*
 * UserAccountService.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.ActorService;
import domain.Actor;

@Service
@Transactional
public class UserAccountService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private UserAccountRepository	userAccountRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------

	public UserAccountService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public UserAccount findByActor(final Actor actor) {
		Assert.notNull(actor);
		UserAccount result;
		result = this.userAccountRepository.findByActorId(actor.getId());
		return result;
	}

	public UserAccount createCustomer() {

		final Authority authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);
		final List<Authority> list = new ArrayList<Authority>();
		list.add(authority);

		final UserAccount userAccount = new UserAccount();
		userAccount.setAuthorities(list);

		return userAccount;

	}

	public UserAccount createHandyWorker() {

		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		final List<Authority> list = new ArrayList<Authority>();
		list.add(authority);

		final UserAccount userAccount = new UserAccount();
		userAccount.setAuthorities(list);

		return userAccount;

	}

	public UserAccount createReferee() {

		final Authority authority = new Authority();
		authority.setAuthority(Authority.REFEREE);
		final List<Authority> list = new ArrayList<Authority>();
		list.add(authority);

		final UserAccount userAccount = new UserAccount();
		userAccount.setAuthorities(list);

		return userAccount;

	}

	public UserAccount createAdmin() {

		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final List<Authority> list = new ArrayList<Authority>();
		list.add(authority);

		final UserAccount userAccount = new UserAccount();
		userAccount.setAuthorities(list);

		return userAccount;

	}

	public UserAccount createSponsor() {

		final Authority authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);
		final List<Authority> list = new ArrayList<Authority>();
		list.add(authority);

		final UserAccount userAccount = new UserAccount();
		userAccount.setAuthorities(list);

		return userAccount;

	}

	public Collection<UserAccount> findAll() {

		Collection<UserAccount> result;
		result = this.userAccountRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	public UserAccount findOne(final int userAccountId) {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		Assert.notNull(userAccountId);
		UserAccount result;
		result = this.userAccountRepository.findOne(userAccountId);

		return result;
	}

	public UserAccount save(final UserAccount userAccount) {

		Assert.notNull(userAccount);
		UserAccount result;

		if (userAccount.getId() != 0) {

			final Actor actor = this.actorService.findByPrincipal();
			Assert.notNull(actor);

			Assert.isTrue(actor.getId() == userAccount.getId());

			result = this.userAccountRepository.save(userAccount);

		} else
			result = this.userAccountRepository.save(userAccount);

		return result;

	}

	// Other business methods -------------------------------------------------

}
