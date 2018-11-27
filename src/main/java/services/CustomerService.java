
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Box;
import domain.Customer;
import domain.FixUpTask;

@Service
@Transactional
public class CustomerService {

	// Managed Repository ------------------------
	@Autowired
	private CustomerRepository	customerRepository;

	// Suporting services ------------------------

	@Autowired
	private BoxService			boxService;

	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods -----------------------

	public Customer create() {
		Customer result;
		result = new Customer();
		
		final Authority authority = new Authority();
        authority.setAuthority(Authority.CUSTOMER);
        final List<Authority> list = new ArrayList<Authority>();
        list.add(authority);

        final UserAccount userAccount = new UserAccount();
        userAccount.setAuthorities(list);
        result.setUserAccount(userAccount);
        

		return result;
	

	}

	public Collection<Customer> findAll() {
		Actor actor = actorService.findByPrincipal();
		Assert.notNull(actor);
		
		Collection<Customer> result;
		result = this.customerRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Customer findOne(final int customerId) {
		Actor actor = actorService.findByPrincipal();
		Assert.notNull(actor);
		
		Assert.notNull(customerId);
		Customer result;
		result = this.customerRepository.findOne(customerId);
		return result;
	}

	public Customer save(final Customer customer) {
		Assert.notNull(customer);
		Customer result;
		
		if (customer.getId() != 0) {

			final Actor actor = this.actorService.findByPrincipal();
			Assert.notNull(actor);

			Assert.isTrue(actor.getId() == customer.getId());
			
			result = this.customerRepository.save(customer);
			
		}else {
			result = this.customerRepository.save(customer);
			
			Actor actor = this.actorService.findByPrincipal();
			int idCustomerLogged = actor.getId();
			int idCustomerOwner = customer.getId();
			Assert.isTrue(idCustomerLogged == idCustomerOwner);
			
			
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

			final Collection<Box> boxes = new ArrayList<>();
			boxes.add(spamBox);
			boxes.add(trashBox);
			boxes.add(inBox);
			boxes.add(outBox);

			inBox = this.boxService.saveNewActor(inBox);
			outBox = this.boxService.saveNewActor(outBox);
			trashBox = this.boxService.saveNewActor(trashBox);
			spamBox = this.boxService.saveNewActor(spamBox);

		}
		return result;
		
	}

	// Other business methods -----------------------

	public Collection<Double> statsOfFixUpTasksPerCustomer() {
		/*Compruebo que está logeado un Admin*/
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		final Collection<Double> result = this.customerRepository.statsOfFixUpTasksPerCustomer();
		Assert.notNull(result);
		return result;

	}

	public Collection<Customer> customersTenPerCentMore() {
		/*Compruebo que está logeado un Admin*/
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
		
		final Collection<Customer> result = this.customerRepository.customersTenPerCentMore();
		Assert.notNull(result);
		return result;
	}

	public Collection<Customer> topThreeCustomersComplaints() {
		/*Compruebo que está logeado un Admin*/
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
		
		final Collection<Customer> customers = this.customerRepository.rankingCustomersComplaints();
		Assert.notNull(customers);

		final List<Customer> ranking = new ArrayList<Customer>();
		ranking.addAll(customers);
		final Collection<Customer> result = ranking.subList(0, 3);

		return result;

	}
	
	public Customer findByTask(FixUpTask fixUpTask){
		Assert.notNull(fixUpTask);
		/*Compruebo que está logeado un HandyWorker*/
		Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(!(actor.getUserAccount().getAuthorities().contains(authority)));
		
		Customer c;
		
		c = customerRepository.findByTask(fixUpTask);
		
		/*Se comprueba porque no puede haber un fixUpTask que no la haya publicado nadie*/
		Assert.notNull(c);
		
		return c;
	}	
		public Customer findByPrincipal() {
			Customer customer;
			UserAccount userAccount;

			userAccount = LoginService.getPrincipal();
			Assert.notNull(userAccount);
			customer = this.findByUserAccount(userAccount);
			Assert.notNull(customer);

			return customer;
		}
		
		public Customer findByUserAccount(final UserAccount userAccount) {
			Assert.notNull(userAccount);

			Customer result;

			result = this.customerRepository.findByUserAccountId(userAccount.getId());

			return result;
		}
		
	
}
