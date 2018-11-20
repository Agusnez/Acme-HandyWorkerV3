package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Complaint;

import repositories.ComplaintRepository;

@Service
@Transactional
public class ComplaintService {
	
	// Managed repository -------------------------------------------
	
	@Autowired
	private ComplaintRepository complaintRepository;
	
	// Supporting services ------------------------------------------
	
	// Constructor --------------------------------------------------
	
	public ComplaintService() {
		super();
	}
	
	// Simple CRUD methods ------------------------------------------
	
	public Complaint create() {
		return new Complaint();

	}

	public Collection<Complaint> findAll() {
		Assert.notNull(this.complaintRepository);
		Collection<Complaint> complaints = this.complaintRepository.findAll();
		Assert.notNull(complaints);
		return complaints;

	}

	public Complaint findOne(final int complaintId) {
		Assert.isTrue(complaintId != 0);
		Complaint result = this.complaintRepository.findOne(complaintId);
		Assert.notNull(result);
		return result;

	}

	public Complaint save(final Complaint complaint) {
		Assert.notNull(complaint);
		
		Date currentMoment = new Date();
		complaint.setMoment(currentMoment);
		
		Complaint result = this.complaintRepository.save(complaint);
		
		return result;

	}
	
	// Other business methods -----------------------------------------

}
