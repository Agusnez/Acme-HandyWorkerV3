
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PhaseRepository;
import security.Authority;
import domain.Actor;
import domain.Application;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Phase;

@Service
@Transactional
public class PhaseService {

	//Managed repository---------------------------------
	@Autowired
	private PhaseRepository		phaseRepository;

	//Suporting services---------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private ApplicationService	applicationService;


	//Simple CRUD methods--------------------------------
	public Phase create() {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		Phase phase;
		phase = new Phase();
		phase.setDescription("");
		phase.setTitle("");
		//		phase.setEndMoment();
		//		phase.setStartMoment(startMoment);
		//		phase.setFixUpTask("");

		return phase;
	}

	public Collection<Phase> findAll() {
		Collection<Phase> result;
		result = this.phaseRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Phase findOne(final int phaseId) {
		Phase phase;
		phase = this.phaseRepository.findOne(phaseId);
		Assert.notNull(phase);
		return phase;
	}

	public Phase save(final Phase phase) {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		//		final HandyWorker hw1 = (HandyWorker) actor;
		//		final FixUpTask f1 = phase.getFixUpTask();
		//
		//		final Collection<Application> applicationsFixUpPhase1 = f1.getApplications();
		//		final Collection<Application> applicationHandyWorker1 = hw1.getApplications();
		//		Application appAccepted1 = null;
		//		for (final Application application : applicationsFixUpPhase1)
		//			if (application.getStatus().equals("ACCEPTED"))
		//				appAccepted1 = application;
		//		Assert.isTrue(applicationHandyWorker1.contains(appAccepted1));

		//if (phase.getId() != 0) {
		final HandyWorker hw = (HandyWorker) actor;
		final FixUpTask f = phase.getFixUpTask();

		final Collection<Application> applicationsFixUpPhase = f.getApplications();
		final Collection<Application> applicationHandyWorker = hw.getApplications();
		Application appAccepted = null;
		for (final Application application : applicationsFixUpPhase)
			if (application.getStatus().equals("ACCEPTED"))
				appAccepted = application;
		Assert.isTrue(applicationHandyWorker.contains(appAccepted));

		//}
		final Date phaseStart = phase.getStartMoment();
		final Date phaseEnd = phase.getEndMoment();
		final Date taskStart = phase.getFixUpTask().getStartDate();
		final Date taskEnd = phase.getFixUpTask().getEndDate();
		System.out.println("----------------------------------------------");
		System.out.println(phaseStart);
		System.out.println(phaseEnd);
		System.out.println(taskStart);
		System.out.println(taskEnd);

		Assert.notNull(phase);

		Assert.isTrue(!phase.getEndMoment().before(phase.getStartMoment()));
		Assert.isTrue(!phase.getStartMoment().before(phase.getFixUpTask().getStartDate()));
		//Assert.isTrue(!phase.getStartMoment().after(phase.getFixUpTask().getEndDate()));
		Assert.isTrue(phaseStart.before(taskEnd));
		Assert.isTrue(!phase.getEndMoment().before(phase.getFixUpTask().getStartDate()));
		//Assert.isTrue(!phase.getEndMoment().after(phase.getFixUpTask().getEndDate()));
		Assert.isTrue(phaseEnd.before(taskEnd));

		Phase result;

		result = this.phaseRepository.save(phase);

		return result;
	}
	public void delete(final Phase phase) {
		Assert.notNull(phase);
		Assert.isTrue(phase.getId() != 0);

		final HandyWorker hw = this.handyWorkerService.findByPrincipal();
		final Application ap = this.applicationService.findApplicationByFixUpTaskId(phase.getFixUpTask().getId());
		Assert.isTrue(hw.getApplications().contains(ap));

		this.phaseRepository.delete(phase);

	}

	//Other business methods----------------------------
	public Collection<Phase> findPhasesByFixUpTaskId(final int fixUpTaskId) {
		final Collection<Phase> result = this.phaseRepository.findPhasesByFixUpTaskId(fixUpTaskId);
		Assert.notNull(result);
		return result;
	}
}
