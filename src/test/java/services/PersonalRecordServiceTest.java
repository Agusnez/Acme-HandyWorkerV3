
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
import domain.PersonalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class PersonalRecordServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private PersonalRecordService	personalRecordService;


	//Tests -------------------------------------------------------

	@Test
	public void testPersonalRecordCreate() {

		super.authenticate("handyWorker1");

		final PersonalRecord personalRecord = this.personalRecordService.create();
		Assert.isNull(personalRecord.getLinkedInProfile());
		Assert.isNull(personalRecord.getEmail());
		Assert.isNull(personalRecord.getFullName());
		Assert.isNull(personalRecord.getLinkedInProfile());
		Assert.isNull(personalRecord.getPhone());

	}

	@Test
	public void testPersonalRecordFindOne() {

		super.authenticate("handyWorker1");

		final PersonalRecord personalRecord = this.personalRecordService.create();
		personalRecord.setEmail("example@gmail.com");
		final Collection<String> comments = new HashSet<>();
		final String c = "example";
		comments.add(c);
		personalRecord.setLinkedInProfile("http://www.example.com");
		personalRecord.setFullName("Example full name");
		personalRecord.setLinkedInProfile("http://www.example.com");
		personalRecord.setPhone("954678915");

		final PersonalRecord saved = this.personalRecordService.save(personalRecord);

		final Collection<PersonalRecord> find = this.personalRecordService.findAll();

		Assert.isTrue(find.contains(saved));

	}
	@Test
	public void testPersonalRecordSave() {

		super.authenticate("handyWorker1");

		final PersonalRecord personalRecord = this.personalRecordService.create();
		personalRecord.setEmail("example@gmail.com");
		final Collection<String> comments = new HashSet<>();
		final String c = "example";
		comments.add(c);
		personalRecord.setLinkedInProfile("http://www.example.com");
		personalRecord.setFullName("Example full name");
		personalRecord.setLinkedInProfile("http://www.example.com");
		personalRecord.setPhone("954678915");

		final PersonalRecord saved = this.personalRecordService.save(personalRecord);

		final PersonalRecord find = this.personalRecordService.findOne(saved.getId());

		Assert.isTrue(find.equals(saved));

	}
}
