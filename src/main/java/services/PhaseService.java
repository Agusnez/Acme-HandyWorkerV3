
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PhaseRepository;
import domain.Phase;

@Service
@Transactional
public class PhaseService {

	//Managed repository---------------------------------
	@Autowired
	private PhaseRepository	phaseRepository;


	//Suporting services---------------------------------

	//Simple CRUD methods--------------------------------
	public Phase create() {//restricciones en el create?
		Phase phase;
		phase = new Phase();
		return phase;
	}

	public Collection<Phase> findAll() {
		Collection<Phase> result;
		result = this.phaseRepository.findAll();
		Assert.notNull(result); //Hay que ponerlo?
		return result;
	}

	public Phase findOne(final int phaseId) {
		Phase phase;
		phase = this.phaseRepository.findOne(phaseId);
		Assert.notNull(phase);
		return phase;
	}

	public Phase save(final Phase phase) {
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

	public void delete(final Phase phase) {//Que pasa si es la unica phase de una fix-up task?
		Assert.notNull(phase);			   //una fix-up taks tiene una o muchas, no puede tener 0
		Assert.isTrue(phase.getId() != 0);

		this.phaseRepository.delete(phase);
	}

	//Other business methods----------------------------
}
