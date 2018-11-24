
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class ActorService {

	//Managed Repository ---------------------------------------------------
	@Autowired
	private ActorRepository	actorRepository;

	//Supporting services --------------------------------------------------
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private BoxService boxService;
	
	@Autowired
	private MessageService messageService;

	//Simple CRUD methods --------------------------------------------------

	public Collection<Actor> findAll() {

		final Collection<Actor> actors = this.actorRepository.findAll();

		Assert.notNull(actors);

		return actors;
	}

	public Actor findOne(final int ActorId) {

		final Actor actor = this.actorRepository.findOne(ActorId);

		Assert.notNull(actor);

		return actor;
	}

	public Actor save(final Actor a) {

		final Actor actor = this.actorRepository.save(a);

		Assert.notNull(actor);

		return actor;
	}
	
	public void delete(Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() != 0);
		Assert.isTrue(actorRepository.exists(actor.getId()));
		this.actorRepository.delete(actor);
	}

	//Other business methods----------------------------

	public Actor findByPrincipal() {
		Actor a;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		a = this.findByUserAccount(userAccount);
		Assert.notNull(a);

		return a;
	}
	
	public Actor findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		Actor result;
		result = this.actorRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public UserAccount findUserAccount(Actor actor) {
		Assert.notNull(actor);
		UserAccount result;
		result = this.userAccountService.findByActor(actor);
		return result;
	}
	
	public Actor editPersonalData(Actor actor) {
		Assert.notNull(actor);
		UserAccount userAccount;
		
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(actor.getUserAccount().equals(userAccount));
		Actor result = this.save(actor);
		
		return result;
	}
	
	public void sendMessage(Message message) {
		Assert.notNull(message);
		
		UserAccount userAccount;
		
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		
		Assert.isTrue(message.getSender().getUserAccount().equals(userAccount));
		
		message.setMoment(new Date());
		 
		Box inBoxReceiver = this.boxService.findInBoxByActorId(message.getRecipient().getId());
		Box outBoxSender = this.boxService.findInBoxByActorId(message.getSender().getId());
		Collection<Box> c = new ArrayList<Box>(message.getBoxes());
		c.add(outBoxSender);
		c.add(inBoxReceiver);
		message.setBoxes(c);
	
	}

	public Collection<Actor> suspiciousActors() {

		final Actor actor = this.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(!(actor.getUserAccount().getAuthorities().contains(authority)));

		Collection<Actor> result;

		result = this.suspiciousActors();

		return result;

	}
}
