
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select avg(c.fixUpTasks.size), min(c.fixUpTasks.size), max(c.fixUpTasks.size), stddev(c.fixUpTasks.size) from Customer c")
	Collection<Double> statsOfFixUpTasksPerCustomer();

	@Query("select distinct c1.name from Customer c1 join c1.fixUpTasks f where c1.fixUpTasks.size > (select avg(c2.fixUpTasks.size)*1.1 from Customer c2) order by f.applications.size")
	Collection<Customer> customersTenPerCentMore();

	@Query("select c.name, c.complaints.size from Customer c order by c.complaints.size desc")
	Collection<Customer> rankingCustomersComplaints();

}
