
package services;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Section;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SectionServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private SectionService	sectionService;


	//Tests -------------------------------------------------------

	@Test
	public void testCreate() {

		super.authenticate("handyWorker1");
		Section s;

		s = this.sectionService.create();

		Assert.notNull(s.getNumber());
		Assert.notNull(s.getPictures());
		Assert.isNull(s.getText());
		Assert.isNull(s.getTitle());

		super.authenticate(null);
	}

	@Test
	public void testFindOne() {
		final Section s;

		s = this.sectionService.findOne(super.getEntityId("section1"));

		Assert.notNull(s);
	}

	@Test
	public void testSave() {
		super.authenticate("handyWorker1");

		Section s;

		s = this.sectionService.create();
		s.setText("example");
		final Collection<String> pictures = new HashSet<>();
		s.setPictures(pictures);
		s.setTitle("example");

		final Section saved = this.sectionService.save(s);

		final Collection<Section> sections = this.sectionService.findAll();

		Assert.isTrue(sections.contains(saved));

		super.authenticate(null);
	}
	@Test
	public void testDelete() {
		super.authenticate("handyWorker1");

		final Section s;

		s = this.sectionService.create();
		s.setText("example");
		final Collection<String> pictures = new HashSet<>();
		s.setPictures(pictures);
		s.setTitle("example");

		final Section saved = this.sectionService.save(s);

		this.sectionService.delete(saved);

		final Collection<Section> sections = this.sectionService.findAll();

		Assert.isTrue(!sections.contains(saved));

		super.authenticate(null);

	}
}
