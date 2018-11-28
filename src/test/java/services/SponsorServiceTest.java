
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
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private SponsorService	sponsorService;

	@Autowired
	private BoxService		boxService;


	//Tests -------------------------------------------------------
	@Test
	public void createSponsor() {
		final Sponsor s = this.sponsorService.create();
		s.setAddress("1234");
		s.setName("Antonio");
		s.setMiddleName("Jose");
		s.setSurname("Martinez");
		final Sponsor saved = this.sponsorService.save(s);

		final Collection<Box> allBoxes = this.boxService.findAll();
		final Collection<Box> sponsorBoxes = new ArrayList<>();
		for (final Box box : allBoxes)
			if (box.getActor() == s)
				sponsorBoxes.add(box);

		Assert.isTrue(!allBoxes.isEmpty());
		final Sponsor sp = this.sponsorService.findOne(saved.getId());
		Assert.isTrue(sp.getId() == saved.getId());
	}

	@Test
	public void updateSponsor() {
		super.authenticate("sponsor1");
		final Sponsor s = this.sponsorService.findOne(super.getEntityId("sponsor1"));
		final String newName = "Paco";
		s.setName(newName);
		final Sponsor saved = this.sponsorService.save(s);

		final Collection<Sponsor> sponsors = this.sponsorService.findAll();

		Assert.isTrue(sponsors.contains(saved));
		super.authenticate(null);

	}

	@Test
	public void findAllSponsor() {
		Collection<Sponsor> result = new ArrayList<>();
		result = this.sponsorService.findAll();
		Assert.isTrue(!result.isEmpty());
	}

	@Test
	public void findOneSponsor() {
		Sponsor result = null;
		result = this.sponsorService.findOne(super.getEntityId("sponsor1"));
		Assert.isTrue(result != null);
	}

}
