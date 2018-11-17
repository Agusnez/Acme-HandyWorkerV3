package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CustomerRepository;
import domain.Customer;

@Service
@Transactional
public class CustomerService {

	// Managed Repository ------------------------
	@Autowired
	private CustomerRepository customerRepository;
	
	// Suporting services ------------------------
	
	// Simple CRUD methods -----------------------
	
	public Customer create(){
		return null;
	}
	
	public Collection<Customer> findAll(){
		return null;
	}
	
	public Customer findOne(int customerId){
		return null;
	}
	
	public Customer save(Customer customer){
		return null;
	}
	
	public void delete(Customer customer){
		
	}
	
	// Other business methods -----------------------
}
