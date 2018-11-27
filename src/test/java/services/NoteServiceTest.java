
package services;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Note;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class NoteServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private NoteService	noteService;


	//Tests -------------------------------------------------------

	@Test
	public void NoteCreateTest() {

		super.authenticate("referee1");

		final Note note = this.noteService.create();

		Assert.isNull(note.getMoment());
		Assert.isNull(note.getCommentCustomer());
		Assert.isNull(note.getCommentReferee());
		Assert.isNull(note.getCommentHandyWorker());

	}

	@Test
	public void NoteFindOneTest() {

		super.authenticate("referee1");

		final Note note = this.noteService.create();

		final Date date = new Date(System.currentTimeMillis() - 100000000);
		note.setMoment(date);
		note.setCommentReferee("xxxxx");

		final Note saved = this.noteService.save(note);

		saved.setCommentCustomer("Example comment");

		final Note saved2 = this.noteService.save(saved);

		final Note find = this.noteService.findOne(saved2.getId());

		Assert.isTrue(find.equals(saved));
	}
}
