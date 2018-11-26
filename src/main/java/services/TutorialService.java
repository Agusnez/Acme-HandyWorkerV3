package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import security.Authority;
import domain.Actor;
import domain.HandyWorker;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {
	
	// Managed Repository ------------------------
	@Autowired
	private TutorialRepository tutorialRepository;
	
	// Suporting services ------------------------
	
	@Autowired
	private ActorService actorService;

	// Simple CRUD methods -----------------------
	
	public Tutorial create(){
		/*Compruebo que está logeado un HandyWorker*/
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(!(actor.getUserAccount().getAuthorities().contains(authority)));
		
		Tutorial t;
		
		t = new Tutorial();
		
		return t;
	}
	
	public Collection<Tutorial> findAll(){
		Collection<Tutorial> res;
		
		Assert.notNull(tutorialRepository);
		res = tutorialRepository.findAll();
		Assert.notNull(res);
		
		return res;
	}
	
	public Tutorial findOne(int tutorialId){
		Tutorial t;
		
		Assert.isTrue(tutorialId!=0);
		t = tutorialRepository.findOne(tutorialId);
		Assert.notNull(t);
		
		return t;
	}
	
	public Tutorial save(Tutorial tutorial){
		Assert.notNull(tutorial);
		/*Compruebo que está logeado el HandyWorker de ese Tutorial*/
		Actor actor = this.actorService.findByPrincipal();
		int idHWLogged = actor.getId();
		int idHWOwner = tutorial.getHandyWorker().getId();
		Assert.isTrue(idHWLogged == idHWOwner);
		
		Tutorial t;
		
	
		t = tutorialRepository.save(tutorial);
		
		return t;
		
	}
	
	public void delete(Tutorial tutorial){	
		Assert.notNull(tutorial);
		/*Compruebo que está logeado el HandyWorker de ese Tutorial*/
		Actor actor = this.actorService.findByPrincipal();
		int idHWLogged = actor.getId();
		int idHWOwner = tutorial.getHandyWorker().getId();
		Assert.isTrue(idHWLogged == idHWOwner);
		
		/*compruebo que existe en BBDD*/	
		Assert.isTrue(tutorial.getId()!=0);
		
		tutorialRepository.delete(tutorial);
		
	}
	
	// Other business methods -----------------------
	
	/*Del requisito 47.2*/
	public Collection<Tutorial> findTutorialsForHW(HandyWorker handyworker){
		/*Compruebo que está logeado un Actor*/
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		
		Collection<Tutorial> tutorials;
		
		tutorials = tutorialRepository.findTutorialForHW(handyworker);
		Assert.notNull(tutorials);
		
		return tutorials;
		
	}
}
