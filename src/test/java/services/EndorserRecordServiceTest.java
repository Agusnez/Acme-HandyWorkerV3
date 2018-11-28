
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
import domain.EndorserRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorserRecordServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private EndorserRecordService	endorserRecordService;


	//Tests -------------------------------------------------------

	@Test
	public void EndorserRecordCreateTest() {

		super.authenticate("handyWorker1");

		final EndorserRecord endorserRecord = this.endorserRecordService.create();
		Assert.isNull(endorserRecord.getComments());
		Assert.isNull(endorserRecord.getEmail());
		Assert.isNull(endorserRecord.getFullName());
		Assert.isNull(endorserRecord.getLinkedInProfile());
		Assert.isNull(endorserRecord.getPhone());

		super.authenticate(null);

	}

	@Test
	public void EndorserRecordSaveTest() {

		super.authenticate("handyWorker1");

		final EndorserRecord endorserRecord = this.endorserRecordService.create();
		endorserRecord.setEmail("example@gmail.com");
		final Collection<String> comments = new HashSet<>();
		final String c = "example";
		comments.add(c);
		endorserRecord.setComments(comments);
		endorserRecord.setFullName("Example full name");
		endorserRecord.setLinkedInProfile("http://www.example.com");
		endorserRecord.setPhone("954678915");

		final EndorserRecord saved = this.endorserRecordService.save(endorserRecord);

		final Collection<EndorserRecord> find = this.endorserRecordService.findAll();

		Assert.isTrue(find.contains(saved));

		super.authenticate(null);

	}

	@Test
	public void EndorserRecordFindOneTest() {
		super.authenticate("handyWorker1");

		final EndorserRecord endorserRecord = this.endorserRecordService.create();
		endorserRecord.setEmail("example@gmail.com");
		final Collection<String> comments = new HashSet<>();
		final String c = "example";
		comments.add(c);
		endorserRecord.setComments(comments);
		endorserRecord.setFullName("Example full name");
		endorserRecord.setLinkedInProfile("http://www.example.com");
		endorserRecord.setPhone("954678915");

		final EndorserRecord saved = this.endorserRecordService.save(endorserRecord);

		final EndorserRecord find = this.endorserRecordService.findOne(saved.getId());

		Assert.isTrue(saved.equals(find));

		super.authenticate(null);
	}
}
