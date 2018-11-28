
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import security.Authority;
import domain.Actor;
import domain.HandyWorker;
import domain.Section;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {

	// Managed Repository ------------------------
	@Autowired
	private TutorialRepository	tutorialRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods -----------------------

	public Tutorial create() {
		/* Compruebo que está logeado un HandyWorker */
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		Tutorial t;

		t = new Tutorial();
		final Collection<Section> sections = new HashSet<>();
		t.setSections(sections);

		return t;
	}

	public Collection<Tutorial> findAll() {
		Collection<Tutorial> res;

		Assert.notNull(this.tutorialRepository);
		res = this.tutorialRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	public Tutorial findOne(final int tutorialId) {
		Tutorial t;

		Assert.isTrue(tutorialId != 0);
		t = this.tutorialRepository.findOne(tutorialId);
		Assert.notNull(t);

		return t;
	}

	public Tutorial save(final Tutorial tutorial) {
		Assert.notNull(tutorial);
		/* Compruebo que está logeado el HandyWorker de ese Tutorial */
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		if (tutorial.getId() != 0) {
			final int idHWLogged = actor.getId();
			final int idHWOwner = tutorial.getHandyWorker().getId();
			Assert.isTrue(idHWLogged == idHWOwner);
		}

		Tutorial t;

		t = this.tutorialRepository.save(tutorial);

		return t;

	}

	public void delete(final Tutorial tutorial) {
		Assert.notNull(tutorial);
		/* Compruebo que está logeado el HandyWorker de ese Tutorial */
		final Actor actor = this.actorService.findByPrincipal();
		final int idHWLogged = actor.getId();
		final int idHWOwner = tutorial.getHandyWorker().getId();
		Assert.isTrue(idHWLogged == idHWOwner);

		/* compruebo que existe en BBDD */
		Assert.isTrue(tutorial.getId() != 0);

		this.tutorialRepository.delete(tutorial);

	}

	// Other business methods -----------------------

	/* Del requisito 47.2 */
	public Collection<Tutorial> findTutorialsForHW(final HandyWorker handyworker) {
		/* Compruebo que está logeado un Actor */
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		Collection<Tutorial> tutorials;

		tutorials = this.tutorialRepository.findTutorialForHW(handyworker);
		Assert.notNull(tutorials);

		return tutorials;

	}
}
