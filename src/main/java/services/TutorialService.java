package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import repositories.TutorialRepository;
import domain.Section;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {
	
	// Managed Repository ------------------------
	@Autowired
	private TutorialRepository tutorialRepository;
	
	// Suporting services ------------------------
	
	SectionRepository sectionRepository;

	// Simple CRUD methods -----------------------
	
	public Tutorial create(){
		/*TODO: hacer que los haga solo el HandyWorker*/
		Tutorial t;
		
//		Assert.notNull(sections);
//		Assert.isTrue(sections.isEmpty());
//		
//		Date moment = new Date(System.currentTimeMillis());
//		
//		t = new Tutorial();
//		/*TODO: ordenar el collection*/
//		t.setSections(sections);
//		t.setMoment(moment);
		
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
		Tutorial t;
		
		Assert.notNull(tutorial);
		t = tutorialRepository.save(tutorial);
		
		return t;
		
	}
	
	public void delete(Tutorial tutorial){	
		Assert.isTrue(tutorialRepository.exists(tutorial.getId()));
		Assert.isTrue(tutorial.getSections().isEmpty());
		
		tutorialRepository.delete(tutorial);
		
	}
	
	// Other business methods -----------------------
}
