package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.EndorsementRepository;
import domain.Complaint;
import domain.Endorsement;

@Service
@Transactional
public class EndorsementService {
	// Managed repository -------------------------------------------
	
		@Autowired
		private EndorsementRepository endorsementRepository;
		
		// Supporting services ------------------------------------------
		
		// Simple CRUD methods ------------------------------------------
		
		public Endorsement create() {

			return null;

		}

		public Collection<Endorsement> findAll() {

			return null;

		}

		public Endorsement findOne(final int endorsementId) {

			return null;

		}

		public Endorsement save(final Endorsement endorsement) {

			return null;

		}

		public void delete(final Endorsement endorsement) {

		}
		
		// Other business methods -----------------------------------------

}


