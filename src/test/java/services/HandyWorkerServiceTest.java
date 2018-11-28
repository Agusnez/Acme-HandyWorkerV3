
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

import utilities.AbstractTest;
import domain.Box;
import domain.Finder;
import domain.HandyWorker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class HandyWorkerServiceTest extends AbstractTest {

	//Service under test ------------------------------------------
	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private BoxService			boxService;

	@Autowired
	private FinderService		finderService;


	//Tests -------------------------------------------------------

	@Test
	public void saveHandyWorker() {
		final HandyWorker hw = this.handyWorkerService.create();
		hw.setAddress("1234");
		hw.setName("Antonio");
		hw.setMiddleName("Jose");
		hw.setSurname("Martinez");
		final HandyWorker saved = this.handyWorkerService.save(hw);

		Assert.isTrue(saved.getMake() != "");

		final Collection<Box> allBoxes = this.boxService.findAll();
		final Collection<Box> handyWorkerBoxes = new ArrayList<>();
		for (final Box box : allBoxes)
			if (box.getActor() == hw)
				handyWorkerBoxes.add(box);

		Assert.isTrue(!allBoxes.isEmpty());

		final Collection<Finder> finders = this.finderService.findAll();
		Finder finder = null;
		for (final Finder finder1 : finders)
			if (finder1.getHandyWorker() == hw) {
				finder = finder1;
				Assert.isTrue(finder != null);
				break;
			}
	}

	@Test
	public void createHandyWorker() {
		final HandyWorker hw = this.handyWorkerService.create();
		Assert.isNull(hw.getAddress());
		Assert.isNull(hw.getSuspicious());
		Assert.isTrue(hw.getApplications().isEmpty());
		Assert.isNull(hw.getName());
		Assert.isNull(hw.getMiddleName());
		Assert.isNull(hw.getSurname());
		Assert.isNull(hw.getPhone());
		Assert.isNull(hw.getPhoto());
		Assert.isNull(hw.getEmail());
		Assert.isNull(hw.getMake());
	}

	@Test
	public void updateHandyWorker() {
		super.authenticate("handyWorker1");
		final HandyWorker hw = this.handyWorkerService.findOne(super.getEntityId("handyWorker1"));
		final String newName = "Paco";
		hw.setName(newName);
		final HandyWorker saved = this.handyWorkerService.save(hw);

		final Collection<HandyWorker> handyWorkers = this.handyWorkerService.findAll();

		Assert.isTrue(handyWorkers.contains(saved));
		super.authenticate(null);

	}

	@Test
	public void findAllHandyWorkers() {
		Collection<HandyWorker> result = new ArrayList<>();
		result = this.handyWorkerService.findAll();
		Assert.isTrue(!result.isEmpty());
	}

	@Test
	public void findOneHandyWorkers() {
		HandyWorker result = null;
		result = this.handyWorkerService.findOne(super.getEntityId("handyWorker1"));
		Assert.isTrue(result != null);
	}

}
