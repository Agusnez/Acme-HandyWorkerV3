
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
import domain.Endorsement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorsementServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private EndorsementService	endorsementService;

	@Autowired
	private ActorService		actorService;


	//Tests -------------------------------------------------------

	@Test
	public void testCreate() {

		final Endorsement endorsement = this.endorsementService.create();

		Assert.isTrue(endorsement.getComments().isEmpty());
		Assert.isNull(endorsement.getCreator());
		Assert.isNull(endorsement.getMoment());
		Assert.isNull(endorsement.getRecipient());

	}

	@Test
	public void testSave() {

		super.authenticate("customer1");

		final Endorsement endorsement, saved;
		Collection<Endorsement> endorsements;

		endorsement = this.endorsementService.create();

		endorsement.setCreator(this.actorService.findByPrincipal());
		endorsement.setRecipient(this.actorService.findOne(this.getEntityId("handyWorker1")));
		final Collection<String> comments = endorsement.getComments();
		comments.add("Magnifico trabajo");
		endorsement.setComments(comments);

		saved = this.endorsementService.save(endorsement);
		endorsements = this.endorsementService.findAll();
		Assert.isTrue(endorsements.contains(saved));

		super.authenticate(null);

	}

	@Test
	public void testFindOne() {

		super.authenticate("customer1");

		final Endorsement endorsement, saved, finded;

		endorsement = this.endorsementService.create();

		endorsement.setCreator(this.actorService.findByPrincipal());
		endorsement.setRecipient(this.actorService.findOne(this.getEntityId("handyWorker1")));
		final Collection<String> comments = endorsement.getComments();
		comments.add("Magnifico trabajo");
		endorsement.setComments(comments);

		saved = this.endorsementService.save(endorsement);
		finded = this.endorsementService.findOne(saved.getId());

		Assert.isTrue(finded.equals(saved));

		super.authenticate(null);
	}

	@Test
	public void testDelete() {

		super.authenticate("customer1");

		final Endorsement endorsement, saved;
		Collection<Endorsement> endorsements;

		endorsement = this.endorsementService.create();

		endorsement.setCreator(this.actorService.findByPrincipal());
		endorsement.setRecipient(this.actorService.findOne(this.getEntityId("handyWorker1")));
		final Collection<String> comments = endorsement.getComments();
		comments.add("Magnifico trabajo");
		endorsement.setComments(comments);

		saved = this.endorsementService.save(endorsement);
		endorsements = this.endorsementService.findAll();
		Assert.isTrue(endorsements.contains(saved));

		this.endorsementService.delete(saved);
		endorsements = this.endorsementService.findAll();
		Assert.isTrue(!endorsements.contains(saved));

		super.authenticate(null);

	}
}
