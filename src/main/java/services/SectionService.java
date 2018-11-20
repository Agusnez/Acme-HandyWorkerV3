package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Section;
import domain.Tutorial;

import repositories.SectionRepository;

@Service
@Transactional
public class SectionService {
	
	// Managed Repository ------------------------
	@Autowired
	private SectionRepository sectionRepository;
	
	// Suporting services ------------------------
	
	
	
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
		
		Assert.isTrue(sectionId!=0);
		s = sectionRepository.findOne(sectionId);
		Assert.notNull(s);
		
		return s;
	}
	
	public Section save(Section section){
		Section s;
		
		s = sectionRepository.save(section);
		
		return s;
	}
	
	public void delete(Section section){
		
	}
	
	// Other business methods -----------------------

}
