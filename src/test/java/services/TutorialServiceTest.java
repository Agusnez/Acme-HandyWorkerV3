
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.HandyWorker;
import domain.Section;
import domain.Sponsorship;
import domain.Tutorial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TutorialServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private TutorialService		tutorialService;

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private SectionService		sectionService;

	@Autowired
	private SponsorshipService	sponsorshipService;


	//Tests -------------------------------------------------------

	@Test
	public void testCreate() {
		super.authenticate("handyWorker1");

		Tutorial t;

		t = this.tutorialService.create();

		Assert.isTrue(t.getSections().isEmpty());

		super.authenticate(null);
	}

	@Test
	public void testFindOne() {

		Tutorial tutorial;

		tutorial = this.tutorialService.findOne(super.getEntityId("tutorial1"));

		/* Aqui comprobamos también findAll() */
		final Collection<Tutorial> tutorials = this.tutorialService.findAll();

		Assert.isTrue(tutorials.contains(tutorial));
	}

	@Test
	public void testSave() {

		super.authenticate("handyWorker1");

		Tutorial t, saved;

		t = this.tutorialService.create();

		t.setTitle("Spring");
		final HandyWorker handyWorker = this.handyWorkerService.findOne(super.getEntityId("handyWorker1"));
		t.setHandyWorker(handyWorker);

		final Date date = new Date(System.currentTimeMillis() - 1000);
		t.setMoment(date);

		final Collection<String> pictures = new HashSet<>();
		pictures.add("http://www.dp.com/acme-handyworker");
		t.setPictures(pictures);

		final Collection<Section> sections = new HashSet<>();
		final Section section1 = this.sectionService.create();
		section1.setText("1 tema");
		section1.setTitle("example");

		sections.add(section1);
		t.setSections(sections);

		final Collection<Sponsorship> sponsorship = new HashSet<>();
		final Sponsorship sp = this.sponsorshipService.findOne(super.getEntityId("sponsorship1"));
		sponsorship.add(sp);
		t.setSponsorships(sponsorship);

		t.setSummary("example");

		saved = this.tutorialService.save(t);

		Assert.isTrue(this.tutorialService.findAll().contains(saved));

		super.authenticate(null);

	}

	@Test
	public void testDelete() {
		super.authenticate("handyWorker1");

		Tutorial t;

		t = this.tutorialService.findOne(super.getEntityId("tutorial1"));

		this.tutorialService.delete(t);

		final Collection<Tutorial> tutorials = this.tutorialService.findAll();

		Assert.isTrue(!tutorials.contains(t));

		super.authenticate(null);

	}
}
