
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest {

	//Service under test ------------------------------------------
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private FixUpTaskService fixUpTaskService;

	//Tests -------------------------------------------------------
	
	@Test
	public void testCreateCategory(){
		super.authenticate("admin");
		
		Category c; 
		c = this.categoryService.create();
		Assert.notNull(c.getParent());
		
		super.authenticate(null);
	}
	
	@Test
	public void testFindOneCategory(){
		super.authenticate("admin");
		
		Category c, saved; 
		c = this.categoryService.create();
		c.setName("zapatos"); /*NO POPULAR ESTA CATEGORY*/
		
		saved = this.categoryService.save(c);
		
		Collection<Category> categories = this.categoryService.findAll();
		Assert.isTrue(categories.contains(saved));
		
		super.authenticate(null);
		
	}
	
	@Test
	public void testDeleteCategory(){
		super.authenticate("admin");
		
		Category c, saved; 
		c = this.categoryService.create();
		c.setName("zapatos"); /*NO POPULAR ESTA CATEGORY*/
		
		saved = this.categoryService.save(c);
		
		this.categoryService.delete(saved);
		
		
		super.authenticate(null);
		
	}
	
	@Test
	public void testSaveDuplicate(){
		super.authenticate("admin");
		
		Category c1, c2, saved1, saved2; 
		
		c1 = this.categoryService.create();
		c1.setName("zapatos"); /*NO POPULAR ESTA CATEGORY*/
		
		c2 = this.categoryService.create();
		c2.setName("zapatos");
		
		saved1 = this.categoryService.save(c1);
		
		Collection<Category> categories = this.categoryService.findAll();
		
		Assert.isTrue(categories.contains(saved1));
		
		
		try{
			saved2 = this.categoryService.save(c2);
			Assert.isTrue(categories.contains(saved2));
		}catch (Exception e){
			
		}
		super.authenticate(null);
		
	}
	
	@Test
	public void testDeleteCATEGORY(){
		super.authenticate("admin");
		Category c;
		
		c = this.categoryService.findByName("CATEGORY");
		
		try{
			
			this.categoryService.delete(c);
			
		}catch (Exception e){
			
		}
		
		Collection<Category> categories = this.categoryService.findAll();
		Assert.isTrue(categories.contains(c));
				
		super.authenticate(null);
		
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
