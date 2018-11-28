
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
import domain.MiscellaneousRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;


	//Tests -------------------------------------------------------

	@Test
	public void MiscellaneousRecordCreateTest() {

		super.authenticate("handyWorker1");

		final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.create();
		Assert.isNull(miscellaneousRecord.getComments());
		Assert.isNull(miscellaneousRecord.getTitle());
		Assert.isNull(miscellaneousRecord.getAttachment());

		super.authenticate(null);
	}

	@Test
	public void MiscellaneousRecordSaveTest() {

		super.authenticate("handyWorker1");

		final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.create();
		final Collection<String> comments = new HashSet<>();
		final String c = "example";
		comments.add(c);
		miscellaneousRecord.setComments(comments);
		miscellaneousRecord.setAttachment("http://www.example.com");
		miscellaneousRecord.setTitle("Example");

		final MiscellaneousRecord saved = this.miscellaneousRecordService.save(miscellaneousRecord);

		final Collection<MiscellaneousRecord> find = this.miscellaneousRecordService.findAll();

		Assert.isTrue(find.contains(saved));
		super.authenticate(null);

	}

	@Test
	public void MiscellaneousRecordFindOneTest() {

		super.authenticate("handyWorker1");

		final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.create();
		final Collection<String> comments = new HashSet<>();
		final String c = "example";
		comments.add(c);
		miscellaneousRecord.setComments(comments);
		miscellaneousRecord.setAttachment("http://www.example.com");
		miscellaneousRecord.setTitle("Example");

		final MiscellaneousRecord saved = this.miscellaneousRecordService.save(miscellaneousRecord);

		final MiscellaneousRecord find = this.miscellaneousRecordService.findOne(saved.getId());

		Assert.isTrue(find.equals(saved));

		super.authenticate(null);
	}
}
