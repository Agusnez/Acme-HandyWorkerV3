
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import security.Authority;
import domain.Actor;
import domain.Complaint;
import domain.Customer;

@Service
@Transactional
public class ComplaintService {

	// Managed repository -------------------------------------------

	@Autowired
	private ComplaintRepository	complaintRepository;

	// Supporting services ------------------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CustomerService		customerservice;


	// Simple CRUD methods ------------------------------------------

	public Complaint create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);
		Assert.isTrue((actor.getUserAccount().getAuthorities().contains(authority)));

		Complaint result;

		result = new Complaint();

		final Collection<String> attachments = new HashSet<>();

		result.setAttachments(attachments);

		return result;

	}

	public Collection<Complaint> findAll() {

		final Collection<Complaint> complaints;

		Assert.notNull(this.complaintRepository);

		complaints = this.complaintRepository.findAll();

		Assert.notNull(complaints);

		return complaints;

	}

	public Complaint findOne(final int complaintId) {

		Assert.isTrue(complaintId != 0);

		Assert.notNull(this.complaintRepository);

		final Complaint result = this.complaintRepository.findOne(complaintId);

		Assert.notNull(result);

		return result;

	}

	public Complaint save(final Complaint complaint) {

		Assert.notNull(complaint);

		final Customer customer = (Customer) this.actorService.findByPrincipal();
		Assert.notNull(customer);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);
		Assert.isTrue((customer.getUserAccount().getAuthorities().contains(authority)));

		//TODO: No se puede actualmente obtener la relacion con la fixUpTask dado que se trata de una collecion

		final Date currentMoment = new Date();
		complaint.setMoment(currentMoment);

		final Complaint result = this.complaintRepository.save(complaint);

		Collection<Complaint> complaints;
		complaints = customer.getComplaints();
		complaints.add(result);
		customer.setComplaints(complaints);

		this.customerservice.save(customer);

		return result;

	}
	// Other business methods -----------------------------------------

}
