
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import domain.Box;
import domain.Customer;

@Service
@Transactional
public class CustomerService {

	// Managed Repository ------------------------
	@Autowired
	private CustomerRepository	customerRepository;

	// Suporting services ------------------------

	@Autowired
	private BoxService			boxService;


	// Simple CRUD methods -----------------------

	public Customer create() {
		Customer result;
		result = new Customer();

		return result;

	}

	public Collection<Customer> findAll() {

		Collection<Customer> result;
		result = this.customerRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Customer findOne(final int customerId) {

		Assert.notNull(customerId);
		Customer result;
		result = this.customerRepository.findOne(customerId);
		return result;
	}

	public Customer save(final Customer customer) {

		Assert.notNull(customer);
		Customer result;
		result = this.customerRepository.save(customer);

		if (customer.getId() == 0) {
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

		final Collection<Double> result = this.customerRepository.statsOfFixUpTasksPerCustomer();
		Assert.notNull(result);
		return result;

	}

	public Collection<Customer> customersTenPerCentMore() {

		final Collection<Customer> result = this.customerRepository.customersTenPerCentMore();
		Assert.notNull(result);
		return result;
	}

	public Collection<Customer> topThreeCustomersComplaints() {

		final Collection<Customer> customers = this.customerRepository.rankingCustomersComplaints();
		Assert.notNull(customers);

		//		List<Customer> lista = new ArrayList<Customer>();
		//		lista.addAll(customers);
		//		Collection<Customer> result;
		//		result.add(lista.get(0));
		//		result.add(lista.get(1));
		//		result.add(lista.get(2));

		return customers;

	}
}
