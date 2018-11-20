package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import repositories.BoxRepository;
import repositories.CustomerRepository;
import repositories.FixUpTaskRepository;
import domain.Customer;

@Service
@Transactional
public class CustomerService {

	// Managed Repository ------------------------
	@Autowired
	private CustomerRepository customerRepository;
	
	// Suporting services ------------------------
	
	ActorRepository actorRepository;
	
	
	
	// Simple CRUD methods -----------------------
	
	public Customer create(){
		Customer c;
		
		c = new Customer();
		
		return c;
	}
	
	public Collection<Customer> findAll(){
		Collection<Customer> res;
	
		res = customerRepository.findAll();
		Assert.notNull(res);
	
		return res;
	}
	
	public Customer findOne(int customerId){
		Customer c;
		
		c = customerRepository.findOne(customerId);
		
		return c;
		
		
	}
	
	public Customer save(Customer customer){
		return null;
	}
	
	public void delete(Customer customer){
		
	}
	
	// Other business methods -----------------------
}
