package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Tutorial;

import repositories.TutorialRepository;

@Service
@Transactional
public class TutorialService {
	
	// Managed Repository ------------------------
	@Autowired
	private TutorialRepository tutorialRepository;
	
	// Suporting services ------------------------

	// Simple CRUD methods -----------------------
	
	public Tutorial create(){
		return null;
	}
	
	public Collection<Tutorial> findAll(){
		return null;
	}
	
	public Tutorial findOne(int tutorialId){
		return null;
	}
	
	public Tutorial save(Tutorial tutorial){
		return null;
	}
	
	public void delete(Tutorial tutorial){
		
	}
	
	// Other business methods -----------------------
}
