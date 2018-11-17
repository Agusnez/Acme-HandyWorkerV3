package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	

}
