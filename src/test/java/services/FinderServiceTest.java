
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
import domain.Finder;
import domain.HandyWorker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	//Service under test ------------------------------------------
	@Autowired
	private FinderService		finderService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	//Tests -------------------------------------------------------
	// TODO Finder testing
	@Test
	public void saveFinder() {
		final HandyWorker hw = this.handyWorkerService.create();
		hw.setAddress("1234");
		hw.setName("Antonio");
		hw.setMiddleName("Jose");
		hw.setSurname("Martinez");
		final HandyWorker saved = this.handyWorkerService.save(hw);

		final Collection<Finder> finders = this.finderService.findAll();
		Finder finder = null;
		for (final Finder finder2 : finders)
			if (finder2.getHandyWorker() == saved) {
				finder = finder2;
				break;
			}
		Assert.isTrue(finder.getLastUpdate() != null);
		Assert.isTrue(finders.contains(finder));
	}
	@Test
	public void updateFinder() {
		super.authenticate("handyWorker1");

		final Collection<Finder> finders = this.finderService.findAll();
		Finder finder = null;
		final HandyWorker hw = this.handyWorkerService.findOne(super.getEntityId("handyWorker1"));
		for (final Finder finder2 : finders)
			if (finder2.getHandyWorker() == hw) {
				finder = finder2;
				break;
			}

		final String oldKeyWord = finder.getKeyWord();
		finder.setKeyWord("Hola Mundo");
		final Finder saved = this.finderService.save(finder);

		Assert.isTrue(saved.getKeyWord() != oldKeyWord);

		super.unauthenticate();
	}

	@Test
	public void findAllFinder() {
		Collection<Finder> result = new ArrayList<>();
		result = this.finderService.findAll();
		Assert.isTrue(!result.isEmpty());
	}

	@Test
	public void findOneFinder() {
		Finder result = null;
		result = this.finderService.findOne(super.getEntityId("finder1"));
		Assert.isTrue(result != null);
	}

}
