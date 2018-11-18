package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import repositories.FinderRepository;
import domain.Finder;

@Service
@Transactional
public class FinderService {
	
	// Managed repository -------------------------------------------
	
		@Autowired
		private FinderRepository finderRepository;
		
		// Supporting services ------------------------------------------
		
		// Simple CRUD methods ------------------------------------------
		
		public Finder create() {

			return null;

		}

		public Collection<Finder> findAll() {

			return null;

		}

		public Finder findOne(final int finderId) {

			return null;

		}

		public Finder save(final Finder finder) {

			return null;

		}

		public void delete(final Finder finder) {

		}
		
		// Other business methods -----------------------------------------
		
}


