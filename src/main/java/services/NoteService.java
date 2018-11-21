
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import domain.Note;

@Service
@Transactional
public class NoteService {

	// Managed Repository ------------------------
	@Autowired
	private NoteRepository	noteRepository;


	// Suporting services ------------------------

	// Simple CRUD methods -----------------------

	public Note create() {

		Note result;

		result = new Note();

		return result;
	}

	public Collection<Note> findAll() {
		Collection<Note> result;

		result = this.noteRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Note findOne(final int noteId) {

		Note result;

		result = this.noteRepository.findOne(noteId);

		return result;
	}

	public Note save(final Note note) {
		Assert.notNull(note);
		Note result;

		result = this.noteRepository.save(note);
		return result;
	}

	// Other business methods -----------------------
}
