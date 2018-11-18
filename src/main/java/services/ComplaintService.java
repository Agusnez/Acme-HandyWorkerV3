package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Complaint;

import repositories.ComplaintRepository;

@Service
@Transactional
public class ComplaintService {
	
	// Managed repository -------------------------------------------
	
	@Autowired
	private ComplaintRepository complaintRepository;
	
	// Supporting services ------------------------------------------
	
	// Simple CRUD methods ------------------------------------------
	
	public Complaint create() {

		return null;

	}

	public Collection<Complaint> findAll() {

		return null;

	}

	public Complaint findOne(final int complaintId) {

		return null;

	}

	public Complaint save(final Complaint complaint) {

		return null;

	}

	public void delete(final Complaint complaint) {

	}
	
	// Other business methods -----------------------------------------

}
