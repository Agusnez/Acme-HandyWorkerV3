
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
import domain.Tutorial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TutorialServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private TutorialService	tutorialService;

	//Tests -------------------------------------------------------
	
	@Test
	public void testCreate(){
		super.authenticate("handyWorker1");
		
		Tutorial t;
		
		t = this.tutorialService.create();
		
		Assert.isTrue(t.getSections().isEmpty());
		
		super.authenticate(null);
	}
	
	@Test
	public void testFindOne(){
		
		Tutorial tutorial;
		
		tutorial = this.tutorialService.findOne(super.getEntityId("tutorial1"));
		
		/*Aqui comprobamos también findAll()*/
		Collection<Tutorial> tutorials = this.tutorialService.findAll();
		
		Assert.isTrue(tutorials.contains(tutorial));
	}
	
	@Test
	public void testSave(){
		
		super.authenticate("handyWorker1");
		
		Tutorial t, saved;
		
		t = this.tutorialService.create();
		t.setTitle("Spring");
		
		saved = this.tutorialService.save(t);
		
		Assert.isTrue(this.tutorialService.findAll().contains(saved));
		
		super.authenticate(null);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
