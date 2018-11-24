
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Box;
import domain.Message;

import security.LoginService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ActorServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private ActorService	actorService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private BoxService boxService;
	
	@Autowired
	private LoginService loginService;

	//Tests -------------------------------------------------------

	@Test
	public void testMessage() {
		super.authenticate("customer2");
		
		Message msg = messageService.create();
		Actor recipient = actorService.findOne(2508);
		
		msg.setSender(actorService.findByUserAccount(LoginService.getPrincipal()));
		msg.setRecipient(recipient);
		msg.setSubject("Un asunto de ejemplo");
		msg.setBody("Esto es un mensaje de prueba.");
		msg.setPriority("URGENT");
		msg.setTags("Development");
		
		// Perform the sending
		this.actorService.sendMessage(msg);
		
		Box inBoxRecipient = this.boxService.findInBoxByActorId(recipient.getId());
		Box outBoxRecipient = this.boxService.findOutBoxByActorId(this.actorService.findByPrincipal().getId());
		
		Collection<Box> supposedBoxes = new ArrayList<Box>();
		supposedBoxes.add(inBoxRecipient);
		supposedBoxes.add(outBoxRecipient);
		
		Collection<Box> boxesOfTheMessage = msg.getBoxes();
		
		Assert.isTrue(boxesOfTheMessage.containsAll(supposedBoxes));
		
	}
}
