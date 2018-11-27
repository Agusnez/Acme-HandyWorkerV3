
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import security.Authority;
import security.LoginService;
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

		Customer customer = null;
		Referee referee = null;
		HandyWorker handyWorker = null;

		final Authority authority1 = new Authority();
		authority1.setAuthority(Authority.CUSTOMER);
		final Authority authority2 = new Authority();
		authority2.setAuthority(Authority.REFEREE);
		final Authority authority3 = new Authority();
		authority3.setAuthority(Authority.HANDYWORKER);

		if (LoginService.getPrincipal().getAuthorities().contains(authority1))
			customer = this.customerService.findByPrincipal();
		else if (LoginService.getPrincipal().getAuthorities().contains(authority2))
			referee = this.refereeService.findByPrincipal();
		else if (LoginService.getPrincipal().getAuthorities().contains(authority3))
			handyWorker = this.handyWorkerService.findByPrincipal();

		Assert.isTrue(customer != null || referee != null || handyWorker != null);

		Note result;

		result = new Note();

		return result;
	}

	public Collection<Note> findAll() {

		Customer customer = null;
		Referee referee = null;
		HandyWorker handyWorker = null;

		final Authority authority1 = new Authority();
		authority1.setAuthority(Authority.CUSTOMER);
		final Authority authority2 = new Authority();
		authority2.setAuthority(Authority.REFEREE);
		final Authority authority3 = new Authority();
		authority3.setAuthority(Authority.HANDYWORKER);

		if (LoginService.getPrincipal().getAuthorities().contains(authority1))
			customer = this.customerService.findByPrincipal();
		else if (LoginService.getPrincipal().getAuthorities().contains(authority2))
			referee = this.refereeService.findByPrincipal();
		else if (LoginService.getPrincipal().getAuthorities().contains(authority3))
			handyWorker = this.handyWorkerService.findByPrincipal();

		Assert.isTrue(customer != null || referee != null || handyWorker != null);

		Collection<Note> result;

		result = this.noteRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Note findOne(final int noteId) {

		Customer customer = null;
		Referee referee = null;
		HandyWorker handyWorker = null;

		final Authority authority1 = new Authority();
		authority1.setAuthority(Authority.CUSTOMER);
		final Authority authority2 = new Authority();
		authority2.setAuthority(Authority.REFEREE);
		final Authority authority3 = new Authority();
		authority3.setAuthority(Authority.HANDYWORKER);

		if (LoginService.getPrincipal().getAuthorities().contains(authority1))
			customer = this.customerService.findByPrincipal();
		else if (LoginService.getPrincipal().getAuthorities().contains(authority2))
			referee = this.refereeService.findByPrincipal();
		else if (LoginService.getPrincipal().getAuthorities().contains(authority3))
			handyWorker = this.handyWorkerService.findByPrincipal();

		Assert.isTrue(customer != null || referee != null || handyWorker != null);

		Note result;

		result = this.noteRepository.findOne(noteId);

		return result;
	}

	public Note save(final Note note) {
		Assert.notNull(note);

		Customer customer = null;
		Referee referee = null;
		HandyWorker handyWorker = null;

		final Authority authority1 = new Authority();
		authority1.setAuthority(Authority.CUSTOMER);
		final Authority authority2 = new Authority();
		authority2.setAuthority(Authority.REFEREE);
		final Authority authority3 = new Authority();
		authority3.setAuthority(Authority.HANDYWORKER);

		if (LoginService.getPrincipal().getAuthorities().contains(authority1))
			customer = this.customerService.findByPrincipal();
		else if (LoginService.getPrincipal().getAuthorities().contains(authority2))
			referee = this.refereeService.findByPrincipal();
		else if (LoginService.getPrincipal().getAuthorities().contains(authority3))
			handyWorker = this.handyWorkerService.findByPrincipal();

		Assert.isTrue(customer != null || referee != null || handyWorker != null);

		if (note.getId() != 0) {

			final Note find = this.noteRepository.findOne(note.getId());

			final String notehw = note.getCommentHandyWorker();
			final String findhw = find.getCommentHandyWorker();
			final String notere = note.getCommentReferee();
			final String findre = find.getCommentReferee();
			final String notecu = note.getCommentCustomer();
			final String findcu = find.getCommentCustomer();

			if (LoginService.getPrincipal().getAuthorities().contains(authority1))
				Assert.isTrue(((findhw == null && notehw == null) || (findhw.equals(notehw))) && ((findre == null && notere == null) || (findre.equals(notere))));
			else if (LoginService.getPrincipal().getAuthorities().contains(authority2))
				Assert.isTrue(((findhw == null && notehw == null) || (((findhw == null && notehw != null)) && ((findhw != null && notehw == null))) || (findhw.equals(notehw)))
					&& ((findcu == null && notecu == null) || (((findcu == null && notecu != null)) && ((findcu != null && notecu == null))) || (findcu.equals(notecu))));
			else if (LoginService.getPrincipal().getAuthorities().contains(authority3))
				Assert.isTrue(((findcu == null && notecu == null) || (findhw.equals(notehw))) && ((findre == null && notere == null) || (findre.equals(notere))));

		}

		Note result;

		result = this.noteRepository.save(note);

		return result;
	}

	// Other business methods -----------------------
}
