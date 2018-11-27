
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import domain.Customer;
import domain.HandyWorker;
import domain.Note;
import domain.Referee;

@Service
@Transactional
public class NoteService {

	// Managed Repository ------------------------

	@Autowired
	private NoteRepository		noteRepository;

	// Suporting services ------------------------

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private RefereeService		refereeService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Simple CRUD methods -----------------------

	public Note create() {

		final Customer customer = null;
		final Referee referee = null;
		final HandyWorker handyWoeker = null;

		if (this.customerService.findByPrincipal() != null) {

		}

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
