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
	
	@Autowired
	private HandyWorkerService handyWorkerService; 

	// Simple CRUD methods -----------------------
	
	public Tutorial create(){
		HandyWorker handyWorker = handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);
		
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
		
		// delete Assert.isTrue(tutorialId!=0);
		t = tutorialRepository.findOne(tutorialId);
		Assert.notNull(t);
		
		return t;
	}
	
	public Tutorial save(Tutorial tutorial){
		HandyWorker handyWorker = handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);
		
		Tutorial t;
		
		Assert.notNull(tutorial);
		t = tutorialRepository.save(tutorial);
		
		return t;
		
	}
	
	public void delete(Tutorial tutorial){	
		HandyWorker handyWorker = handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);
		
		Assert.notNull(tutorial);	
		Assert.isTrue(tutorial.getId()!=0);
		
		/*Comprobamos que el tutorial sea del HandyWorker logeado
		  es este momento*/
		Collection<Tutorial> tutorials = findTutorialForHW();
		Assert.isTrue(tutorials.contains(tutorial));
		
		tutorialRepository.delete(tutorial);
		
	}
	
	// Other business methods -----------------------
	
	public Collection<Tutorial> findTutorialForHW(){
		HandyWorker handyWorker = handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);
		
		Collection<Tutorial> tutorials;
		
		tutorials = tutorialRepository.findTutorialForHW(handyWorker);
		Assert.notNull(tutorials);
		
		return tutorials;
		
	}
}
