
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
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	//Service under test ------------------------------------------
	@Autowired
	MessageService	messageService;


	//Tests -------------------------------------------------------
	@Test
	public void testCreate() {
		final Message m = this.messageService.create();
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
		final int messageId = super.getEntityId("message5");
		final Message m = this.messageService.findOne(messageId);
		Assert.notNull(m);
		Assert.isTrue(m.getSubject().equals("subject5"));
	}

	@Test
	public void testSave() {
		super.authenticate("handyworker1");
		final int messageId = super.getEntityId("message4");
		final Message m = this.messageService.findOne(messageId);
		m.setBody("This is a new body");
		final Message savedMessage = this.messageService.save(m);
		Assert.isTrue(savedMessage.getBody().equals("This is a new body"));
		Assert.isTrue(m.getId() == savedMessage.getId());

		super.authenticate(null);

	}

}
