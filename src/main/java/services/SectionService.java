package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import security.Authority;
import domain.Actor;
import domain.HandyWorker;
import domain.Section;

@Service
@Transactional
public class SectionService {
	
	// Managed Repository ------------------------
	@Autowired
	private SectionRepository sectionRepository;
	
	// Suporting services ------------------------
	@Autowired
	private ActorService actorService;
	
	// Simple CRUD methods -----------------------
	
	public Section create(){
		/*Compruebo que está logeado un HandyWorker*/
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
		
		Section s;
		
		s = new Section();
		int size = findAll().size();
		s.setNumero(size+1);
		
		return s;
	}
	
	public Collection<Section> findAll(){
		Collection<Section> sections;
		
		Assert.notNull(sectionRepository);
		sections = sectionRepository.findSectionsOrdered();
		Assert.notNull(sections);
		
		return sections;
	}
	
	public Section findOne(int sectionId){
		Section s;
		
		Assert.isTrue(sectionId!=0);
		s = sectionRepository.findOne(sectionId);
		Assert.notNull(s);
		
		return s;
	}
	
	public Section save(Section section){
		/*Compruebo que está logeado un HandyWorker*/
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
		
		/*TODO: HAY QUE MIRARLO, NO HAY FORMA DE ENCONTRAR EL TUTORIAL ESPECÍFICO DE LA SECTION*/
		
		Assert.notNull(section);
		
		Section s;
		
		s = sectionRepository.save(section);
		
		return s;
	}
	
	public void delete(Section section){
		
		
		Collection<Section> sections = findAll();
		
		/*reordeno las secciones reasignando el valor correcto de 'numero'*/
		for(Section s : sections){
			
			if(s.getNumero()>section.getNumero()){
				s.setNumero(s.getNumero()-1);
			}
		}
		sectionRepository.delete(section);
		
	}
	
	// Other business methods -----------------------

}
