
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import domain.Complaint;

@Service
@Transactional
public class ComplaintService {

	// Managed repository -------------------------------------------

	@Autowired
	private ComplaintRepository	complaintRepository;


	// Supporting services ------------------------------------------

	// Simple CRUD methods ------------------------------------------

	public Complaint create() {

		Complaint result;

		result = new Complaint();

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

		final Date currentMoment = new Date();
		complaint.setMoment(currentMoment);

		final Complaint result = this.complaintRepository.save(complaint);

		return result;

	}
	// Other business methods -----------------------------------------

}
