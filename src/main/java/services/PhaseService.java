
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PhaseRepository;
import domain.FixUpTask;
import domain.Phase;

@Service
@Transactional
public class PhaseService {

	//Managed repository---------------------------------
	@Autowired
	private PhaseRepository	phaseRepository;


	//Suporting services---------------------------------

	//Simple CRUD methods--------------------------------
	public Phase create() {
		//Solo lo puede crear el handy worker que vaya a hacer esa fixUp task cuando esté aceptada
		//También en el save?
		Phase phase;
		phase = new Phase();
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
		//Poner autoridad
		Assert.notNull(phase);
		Assert.isTrue(!phase.getEndMoment().before(phase.getStartMoment()));
		Assert.isTrue(!phase.getStartMoment().before(phase.getFixUpTask().getStartDate()));
		Assert.isTrue(!phase.getStartMoment().after(phase.getFixUpTask().getEndDate()));
		Assert.isTrue(!phase.getEndMoment().before(phase.getFixUpTask().getStartDate()));
		Assert.isTrue(!phase.getEndMoment().after(phase.getFixUpTask().getEndDate()));

		Phase result;
		result = this.phaseRepository.save(phase);

		return result;
	}

	public void delete(final Phase phase) {
		//poner autoridad lo mismo que arriba
		Assert.notNull(phase);
		Assert.isTrue(phase.getId() != 0);
		//Restriccion: No borrar la última phase de una FixUpTask //SI CAMBIO LA CARDINALIDAD, QUITAR ESTO
		final FixUpTask task = phase.getFixUpTask();
		final Collection<Phase> phases = this.findPhasesByFixUpTaskId(task.getId());
		final int numPhases = phases.size();
		Assert.isTrue(numPhases > 1);
		this.phaseRepository.delete(phase);
	}

	//Other business methods----------------------------
	public Collection<Phase> findPhasesByFixUpTaskId(final int fixUpTaskId) {
		final Collection<Phase> result = this.phaseRepository.findPhasesByFixUpTaskId(fixUpTaskId);
		Assert.notNull(result);
		return result;
	}
}
