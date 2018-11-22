package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import domain.HandyWorker;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {
	
	// Managed Repository ------------------------
	@Autowired
	private TutorialRepository tutorialRepository;
	
	// Suporting services ------------------------
	
	private HandyWorkerService handyWorkerService;

	// Simple CRUD methods -----------------------
	
	public Tutorial create(){
		/*Compruebo que lo haga un HandyWorker*/
		HandyWorker handyWorker = handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);
		
		Tutorial t;
		
		t = new Tutorial();
		
		return t;
	}
	
	public Collection<Tutorial> findAll(){
		/*Compruebo que lo haga un HandyWorker*/
		HandyWorker handyWorker = handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);
		
		Collection<Tutorial> res;
		
		Assert.notNull(tutorialRepository);
		res = tutorialRepository.findAll();
		Assert.notNull(res);
		
		return res;
	}
	
	public Tutorial findOne(int tutorialId){
		/*Compruebo que lo haga un HandyWorker*/
		HandyWorker handyWorker = handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);
		
		Tutorial t;
		
		// delete Assert.isTrue(tutorialId!=0);
		t = tutorialRepository.findOne(tutorialId);
		Assert.notNull(t);
		
		return t;
	}
	
	public Tutorial save(Tutorial tutorial){
		/*Compruebo que lo haga un HandyWorker*/
		HandyWorker handyWorker = handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);
		
		Tutorial t;
		
		Assert.notNull(tutorial);
		t = tutorialRepository.save(tutorial);
		
		return t;
		
	}
	
	public void delete(Tutorial tutorial){
		/*Compruebo que lo haga un HandyWorker*/
		HandyWorker handyWorker = handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);
		
		Assert.isTrue(tutorialRepository.exists(tutorial.getId()));
		Assert.isTrue(tutorial.getSections().isEmpty());
		
		tutorialRepository.delete(tutorial);
		
	}
	
	// Other business methods -----------------------
	
	/*Requisito 49.1*/
	public Collection<Tutorial> tutorialForHW(HandyWorker hw){
		/*Compruebo que lo haga un HandyWorker*/
		HandyWorker handyWorker = handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);
		
		Collection<Tutorial> tutorials = 
				tutorialRepository.tutorialForHW(handyWorker);
		Assert.notNull(tutorials);
		
		return tutorials;
		
	}
}
