
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
import domain.SocialProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SocialProfileServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private SocialProfileService	socialprofileservice;

	@Autowired
	private ActorService			actorservice;


	//Tests -------------------------------------------------------

	@Test
	public void testCreate() {

		SocialProfile socialProfile;

		socialProfile = this.socialprofileservice.create();

		Assert.isNull(socialProfile.getActor());
		Assert.isNull(socialProfile.getLink());
		Assert.isNull(socialProfile.getNick());
		Assert.isNull(socialProfile.getSocialName());

	}

	@Test
	public void testSave() {

		super.authenticate("customer1");

		SocialProfile socialProfile, saved;
		Collection<SocialProfile> socialProfiles;

		socialProfile = this.socialprofileservice.create();
		socialProfile.setActor(this.actorservice.findByPrincipal());
		socialProfile.setLink("https://github.com/xxx");
		socialProfile.setNick("Ornitorrinco");
		socialProfile.setSocialName("Otorrino");

		saved = this.socialprofileservice.save(socialProfile);
		socialProfiles = this.socialprofileservice.findAll();
		Assert.isTrue(socialProfiles.contains(saved));

		super.authenticate(null);
	}

}
