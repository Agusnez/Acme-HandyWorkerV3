
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.FixUpTask;
import domain.Phase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class PhaseServiceTest extends AbstractTest {

	//Service under test ------------------------------------------
	@Autowired
	private PhaseService		phaseService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	//Tests -------------------------------------------------------
	@Test
	public void createPhase() {
		super.authenticate("handyWorker1");
		final FixUpTask fixUp = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask5"));

		final Phase phase = this.phaseService.create();
		phase.setDescription("Hola mundo");

		//		final Calendar calStart = Calendar.getInstance();
		//		calStart.set(2018, 8, 16);
		//		final Date start = new Date(calStart.getTimeInMillis());
		//		System.out.println(start);
		//		final Calendar calEnd = Calendar.getInstance();
		//		calEnd.set(2018, 9, 21);
		//		final Date end = new Date(calEnd.getTimeInMillis());
		//		System.out.println(end);

		final Date start = new GregorianCalendar(2018, Calendar.AUGUST, 17).getTime();
		final Date end = new GregorianCalendar(2018, Calendar.SEPTEMBER, 20).getTime();
		System.out.println(start);
		System.out.println(end);

		phase.setStartMoment(start);
		phase.setEndMoment(end);
		phase.setFixUpTask(fixUp);
		phase.setTitle("Phase de prueba");

		final Phase result = this.phaseService.save(phase);
		final Collection<Phase> phases = this.phaseService.findAll();
		Assert.isTrue(phases.contains(result));

	}

	@Test
	public void updatePhase() {
		super.authenticate("handyWorker1");
		final Phase phase = this.phaseService.findOne(super.getEntityId("phase6"));
		final String title = phase.getTitle();
		phase.setTitle("Hola Buenos Dias");

		final Phase result = this.phaseService.save(phase);

		Assert.isTrue(!result.getTitle().equals(title));

	}

	@Test
	public void deletePhase() {
		super.authenticate("handyWorker1");
		final Phase phase = this.phaseService.findOne(super.getEntityId("phase6"));

		this.phaseService.delete(phase);

		final Collection<Phase> phases = this.phaseService.findAll();
		Assert.isTrue(!phases.contains(phase));

	}

	@Test
	public void findAllPhase() {
		Collection<Phase> result = new ArrayList<>();
		result = this.phaseService.findAll();
		Assert.isTrue(!result.isEmpty());
	}

	@Test
	public void findOnePhase() {
		Phase result = null;
		result = this.phaseService.findOne(super.getEntityId("phase1"));
		Assert.isTrue(result != null);
	}
}
