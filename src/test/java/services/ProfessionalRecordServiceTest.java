
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
import domain.ProfessionalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private ProfessionalRecordService	professionalRecordService;


	//Tests -------------------------------------------------------

	@Test
	public void testCreate() {
		super.authenticate("handyWorker1");
		ProfessionalRecord profRecord;

		profRecord = this.professionalRecordService.create();

		Assert.isNull(profRecord.getAttachment());
		Assert.isNull(profRecord.getComments());
		Assert.isNull(profRecord.getCompanyName());
		Assert.isNull(profRecord.getPeriod());
		Assert.isNull(profRecord.getRole());

		super.authenticate(null);
	}

	@Test
	public void ProfessionalRecordSaveTest() {

		super.authenticate("handyWorker1");

		final ProfessionalRecord ProfessionalRecord = this.professionalRecordService.create();

		ProfessionalRecord.setAttachment("http://www.example.com");
		final Collection<String> comments = new HashSet<>();
		final String c = "example";
		comments.add(c);
		ProfessionalRecord.setComments(comments);
		ProfessionalRecord.setCompanyName("Example");
		ProfessionalRecord.setPeriod("Example-example");
		ProfessionalRecord.setRole("Example");

		final ProfessionalRecord saved = this.professionalRecordService.save(ProfessionalRecord);

		final Collection<ProfessionalRecord> find = this.professionalRecordService.findAll();

		Assert.isTrue(find.contains(saved));

		super.authenticate(null);

	}

	@Test
	public void EducationRecordFindOneTest() {

		super.authenticate("handyWorker1");

		final ProfessionalRecord professionalRecord = this.professionalRecordService.create();

		professionalRecord.setAttachment("http://www.example.com");
		final Collection<String> comments = new HashSet<>();
		final String c = "example";
		comments.add(c);
		professionalRecord.setComments(comments);
		professionalRecord.setRole("example");
		professionalRecord.setPeriod("Example-example");
		professionalRecord.setCompanyName("Example");

		final ProfessionalRecord saved = this.professionalRecordService.save(professionalRecord);

		final ProfessionalRecord find = this.professionalRecordService.findOne(saved.getId());

		Assert.isTrue(saved.equals(find));

		super.authenticate(null);
	}
}
