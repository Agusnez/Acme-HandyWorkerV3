
package services;

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
	// TODO EndorserRecord testing
	@Test
	public void EndorserRecordCreateTest() {

		super.authenticate("handyWorker1");

		final EndorserRecord endorserRecord = this.endorserRecordService.create();
		Assert.isNull(endorserRecord.getComments());
		Assert.isNull(endorserRecord.getEmail());
		Assert.isNull(endorserRecord.getFullName());
		Assert.isNull(endorserRecord.getLinkedInProfile());
		Assert.isNull(endorserRecord.getPhone());

	}

	@Test
	public void EndorserRecordSaveTest() {

	}
}
