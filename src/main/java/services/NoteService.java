
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
		result.setCommentCustomer("-");
		result.setCommentHandyWorker("-");
		result.setCommentReferee("-");

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

		Assert.isTrue(result.size() != 0);

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
				Assert.isTrue(((findhw == null && notehw == null) || (findhw == notehw)) && ((findre == null && notere == null) || (findre == notere)));
			else if (LoginService.getPrincipal().getAuthorities().contains(authority2))
				Assert.isTrue(((findhw == null && notehw == null) || (findhw.equals(notehw))) && ((findcu == null && notecu == null) || (findcu.equals(notecu))));
			else if (LoginService.getPrincipal().getAuthorities().contains(authority3))
				Assert.isTrue(((findcu == null && notecu == null) || (findcu == notecu)) && ((findre == null && notere == null) || (findre == notere)));
		}else if(note.getId() == 0){
			if (LoginService.getPrincipal().getAuthorities().contains(authority1)){
				Assert.isTrue(note.getCommentHandyWorker().equals("-"));
				Assert.isTrue(note.getCommentReferee().equals("-"));
			}else if (LoginService.getPrincipal().getAuthorities().contains(authority2)){
				Assert.isTrue(note.getCommentHandyWorker().equals("-"));
				Assert.isTrue(note.getCommentCustomer().equals("-"));
			}else if (LoginService.getPrincipal().getAuthorities().contains(authority3)){
				Assert.isTrue(note.getCommentReferee().equals("-"));
				Assert.isTrue(note.getCommentReferee().equals("-"));
			}	
		}
		Note result;

		result = this.noteRepository.save(note);

		return result;

	}
	// Other business methods -----------------------
}
