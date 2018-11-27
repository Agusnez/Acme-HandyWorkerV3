
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import security.Authority;
import domain.Actor;
import domain.Section;

@Service
@Transactional
public class SectionService {

	// Managed Repository ------------------------
	@Autowired
	private SectionRepository	sectionRepository;

	// Suporting services ------------------------
	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods -----------------------

	public Section create() {
		/* Compruebo que está logeado un HandyWorker */
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		Section s;

		s = new Section();
		final int size = this.findAll().size();
		s.setNumero(size + 1);

		return s;
	}

	public Collection<Section> findAll() {
		Collection<Section> sections;

		Assert.notNull(this.sectionRepository);
		sections = this.sectionRepository.findSectionsOrdered();
		Assert.notNull(sections);

		return sections;
	}

	public Section findOne(final int sectionId) {
		Section s;

		Assert.isTrue(sectionId != 0);
		s = this.sectionRepository.findOne(sectionId);
		Assert.notNull(s);

		return s;
	}

	public Section save(final Section section) {
		/* Compruebo que está logeado un HandyWorker */
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		/* TODO: HAY QUE MIRARLO, NO HAY FORMA DE ENCONTRAR EL TUTORIAL ESPECÍFICO DE LA SECTION */

		Assert.notNull(section);

		Section s;

		s = this.sectionRepository.save(section);

		return s;
	}

	public void delete(final Section section) {

		final Collection<Section> sections = this.findAll();

		/* reordeno las secciones reasignando el valor correcto de 'numero' */
		for (final Section s : sections)
			if (s.getNumero() > section.getNumero())
				s.setNumero(s.getNumero() - 1);
		this.sectionRepository.delete(section);

	}

	// Other business methods -----------------------

}
