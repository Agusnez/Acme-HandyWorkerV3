package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Note;

import repositories.NoteRepository;

@Service
@Transactional
public class NoteService {

	// Managed Repository ------------------------
	@Autowired
	private NoteRepository noteRepository;
	
	// Suporting services ------------------------
	
	// Simple CRUD methods -----------------------
	
	public Note create(){
		return null;
	}
	
	public Collection<Note> findAll(){
		return null;
	}
	
	public Note findOne(int noteId){
		return null;
	}
	
	public Note save(Note note){
		return null;
	}
	
	public void delete(Note note){
		
	}
	
	// Other business methods -----------------------
}
