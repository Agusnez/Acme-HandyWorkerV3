
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Message;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	//Service under test ------------------------------------------
	@Autowired
	MessageService messageService;

	//Tests -------------------------------------------------------
	@Test
	public void testCreate() {
		Message m = this.messageService.create();
		Assert.notNull(m);
		Assert.isTrue(m.getBoxes().isEmpty());
	}
	
	@Test
	public void testFindAll() {
		Collection<Message> messages;
		messages = this.messageService.findAll();
		Assert.notNull(messages);
		Assert.isTrue(messages.size() == 5);
	}
	
	@Test
	public void testFindOne() {
		int messageId = super.getEntityId("message5");
		Message m = this.messageService.findOne(messageId);
		Assert.notNull(m);
		Assert.isTrue(m.getSubject().equals("subject5"));
	}
	
	@Test
	public void testSave() {
		super.authenticate("handyworker1");
		int messageId = super.getEntityId("message4");
		Message m = this.messageService.findOne(messageId);
		m.setBody("This is a new body");
		Message savedMessage = this.messageService.save(m);
		Assert.isTrue(savedMessage.getBody().equals("This is a new body"));
		Assert.isTrue(m.getId()==savedMessage.getId());
		
	}
	
}
