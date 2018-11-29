
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
import domain.Sponsorship;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private SponsorshipService	sponsorshipService;


	//Tests -------------------------------------------------------
	@Test
	public void testCreate() {
		this.authenticate("sponsor1");
		final Sponsorship s = this.sponsorshipService.create();

		Assert.isNull(s.getBanner());
		Assert.isNull(s.getCreditCard());
		Assert.isNull(s.getTargetPage());
		Assert.isNull(s.getSponsor());

		this.authenticate(null);
	}

	@Test
	public void testSave() {
		this.authenticate("sponsor1");
		final Sponsorship s = this.sponsorshipService.findOne(super.getEntityId("sponsorship1"));

		s.setBanner("http://wwww.s.com/");

		final Sponsorship saved = this.sponsorshipService.save(s);

		final Collection<Sponsorship> ss = this.sponsorshipService.findAll();
		Assert.isTrue(ss.contains(saved), "##### Error create #####");

		super.authenticate(null);

	}

	@Test
	public void testFindAll() {
		final Collection<Sponsorship> ss = this.sponsorshipService.findAll();
		Assert.isTrue(ss.size() > 0, "##### Error findAll #####");

	}

	@Test
	public void testFindOne() {
		final Sponsorship sponsorship = this.sponsorshipService.findOne(super.getEntityId("sponsorship1"));

		Assert.isTrue(!sponsorship.equals(null), "##### Error findOne #####");
	}

}
