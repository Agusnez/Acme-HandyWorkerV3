package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import domain.Section;

@Service
@Transactional
public class SectionService {
	
	// Managed Repository ------------------------
	@Autowired
	private SectionRepository sectionRepository;
	
	// Suporting services ------------------------
	
	private TutorialService tutorialService;
	
	// Simple CRUD methods -----------------------
	
	public Section create(){
		Section s;
		
		s = new Section();
		
		return s;
	}
	
	public Collection<Section> findAll(){
		Collection<Section> sections;
		
		Assert.notNull(sectionRepository);
		sections = sectionRepository.findAll();
		Assert.notNull(sections);
		
		return sections;
	}
	
	public Section findOne(int sectionId){
		Section s;
		
		s = sectionRepository.findOne(sectionId);
		Assert.notNull(s);
		
		return s;
	}
	
	public Section save(Section section){
		Section s;
		
		/*entiendo que una section se puede repetir por
		 tanto no compruebo lo contrario*/
		
		s = sectionRepository.save(section);
		
		return s;
	}
	
	public void delete(Section section){
		
	}
	
	// Other business methods -----------------------

}
