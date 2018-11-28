
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
import domain.EducationRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EducationRecordServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private EducationRecordService	educationRecordService;


	//Tests -------------------------------------------------------

	@Test
	public void EducationRecordCreateTest() {

		super.authenticate("handyWorker1");

		final EducationRecord educationRecord = this.educationRecordService.create();

		Assert.isNull(educationRecord.getAttachment());
		Assert.isNull(educationRecord.getComments());
		Assert.isNull(educationRecord.getInstitution());
		Assert.isNull(educationRecord.getPeriod());
		Assert.isNull(educationRecord.getTitle());

		super.authenticate(null);

	}

	@Test
	public void EducationRecordSaveTest() {

		super.authenticate("handyWorker1");

		final EducationRecord educationRecord = this.educationRecordService.create();

		educationRecord.setAttachment("http://www.example.com");
		final Collection<String> comments = new HashSet<>();
		final String c = "example";
		comments.add(c);
		educationRecord.setComments(comments);
		educationRecord.setInstitution("Example");
		educationRecord.setPeriod("Example-example");
		educationRecord.setTitle("Example");

		final EducationRecord saved = this.educationRecordService.save(educationRecord);

		final Collection<EducationRecord> find = this.educationRecordService.findAll();

		Assert.isTrue(find.contains(saved));

		super.authenticate(null);

	}

	@Test
	public void EducationRecordFindOneTest() {

		super.authenticate("handyWorker1");

		final EducationRecord educationRecord = this.educationRecordService.create();

		educationRecord.setAttachment("http://www.example.com");
		final Collection<String> comments = new HashSet<>();
		final String c = "example";
		comments.add(c);
		educationRecord.setComments(comments);
		educationRecord.setInstitution("Example");
		educationRecord.setPeriod("Example-example");
		educationRecord.setTitle("Example");

		final EducationRecord saved = this.educationRecordService.save(educationRecord);

		final EducationRecord find = this.educationRecordService.findOne(saved.getId());

		Assert.isTrue(saved.equals(find));

		super.authenticate(null);

	}
}
