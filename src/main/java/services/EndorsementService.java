package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorsementRepository;
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

			return new Endorsement();

		}

		public Collection<Endorsement> findAll() {
			Assert.notNull(this.endorsementRepository);
			Collection<Endorsement> result = this.endorsementRepository.findAll();
			Assert.notNull(result);
			return result;

		}

		public Endorsement findOne(final int endorsementId) {
			Assert.isTrue(endorsementId != 0);
			Endorsement result = this.endorsementRepository.findOne(endorsementId);
			Assert.notNull(result);
			return result;

		}

		public Endorsement save(final Endorsement endorsement) {
			Assert.notNull(endorsement);
			
			return this.endorsementRepository.save(endorsement);

		}

		public void delete(final Endorsement endorsement) {
			Assert.notNull(endorsement);
			Assert.isTrue(endorsement.getId() != 0);
			Assert.isTrue(this.endorsementRepository.exists(endorsement.getId()));
			
			this.endorsementRepository.delete(endorsement);
			
		}
		
		// Other business methods -----------------------------------------

}


