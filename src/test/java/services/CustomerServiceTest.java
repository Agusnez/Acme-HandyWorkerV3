
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.UserAccount;
import utilities.AbstractTest;
import domain.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CustomerServiceTest extends AbstractTest {

	//Service under test ------------------------------------------
	@Autowired
	private CustomerService	customerService;

	//Tests -------------------------------------------------------

	
	@Test
	public void testCreate(){
		Customer c;
		
		c = this.customerService.create();
			
		Assert.isTrue(c.getFixUpTasks().isEmpty());
		Assert.isTrue(c.getFixUpTasks().isEmpty());
			
	}
	
	@Test
	public void testFindAll(){
		super.authenticate("handyWorker1");
		
		Collection<Customer> customers = this.customerService.findAll();
		
		/*Hasta hoy (27-11-2018) hay 4 customers*/
		
			/*Con esto vamos a comprobar también el método findOne*/
		Customer c1 = this.customerService.findOne(super.getEntityId("customer1"));
		Customer c2 = this.customerService.findOne(super.getEntityId("customer2"));
		Customer c3 = this.customerService.findOne(super.getEntityId("customer3"));
		Customer c4 = this.customerService.findOne(super.getEntityId("customer4"));
		
		Assert.isTrue(customers.contains(c1)
				&& customers.contains(c2) && customers.contains(c3) && customers.contains(c4));
		
		super.authenticate(null);
		
	}
	
	@Test
	public void testSave(){
		Customer c, saved;
		
		c = this.customerService.create();
		c.setName("Angel");
		c.setMiddleName("Manuel");
		c.setSurname("Calzado");
		c.setAddress("Calle Escalera del Castillo nº6");
		
		final UserAccount userAccount = c.getUserAccount();
		userAccount.setUsername("Gustavito212");
		userAccount.setPassword("123123");
		
		c.setUserAccount(userAccount);
		
		saved = this.customerService.save(c);
		
		Assert.isTrue(this.customerService.findAll().contains(saved));
	}
	
	@Test
	public void testUpdateSameCustomer(){
		super.authenticate("customer1");
		Customer c, updated;
		
		c = this.customerService.findOne(super.getEntityId("customer1"));
		c.setName("cambio");
		
		updated = this.customerService.save(c);
		
		Assert.isTrue(updated.getName()=="cambio");
		
		super.authenticate(null);
	}
	
	@Test
	public void testUpdateOtherCustomer(){
		super.authenticate("customer2");
		Customer c;
		
		c = this.customerService.findOne(super.getEntityId("customer1"));
		c.setName("cambio");
		
		try{
			this.customerService.save(c);
			
		}catch (Exception e){
			
		}
		
		Collection<Customer> customers = this.customerService.findAll();
		for(Customer customer : customers){
			
			if(customer.getId()==c.getId()){
				Assert.isTrue(customer.getName().equals(c.getName()));
				break;
			}
			
		}
		super.authenticate(null);
	}
	
	
}
