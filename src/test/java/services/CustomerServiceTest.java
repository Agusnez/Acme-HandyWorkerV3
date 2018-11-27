
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import utilities.AbstractTest;
import domain.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CustomerServiceTest extends AbstractTest {

	//Service under test ------------------------------------------
	
	private CustomerService customerService;

	//Tests -------------------------------------------------------

	
//TODO: NullPointerException, no sé por qué	
//	@Test
//	public void testCreate(){
//		Customer c;
//		
//		c = this.customerService.create();
//		
//		Assert.isTrue(c.getUserAccount().getAuthorities().contains(Authority.CUSTOMER));
//		
//	}
	

