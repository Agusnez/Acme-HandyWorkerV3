package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Section;

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
		return null;
	}
	
	public Collection<Section> findAll(){
		return null;
	}
	
	public Section findOne(int sectionId){
		return null;
	}
	
	public Section save(Section section){
		return null;
	}
	
	public void delete(Section section){
		
	}
	
	// Other business methods -----------------------

}
