package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.RefereeRepository;
import domain.Referee;

@Service
@Transactional
public class RefereeService {
	// Managed repository -------------------------------------------
	
			@Autowired
			private RefereeRepository refereeRepository;
			
			// Supporting services ------------------------------------------
			
			// Simple CRUD methods ------------------------------------------
			
			public Referee create() {

				return null;

			}

			public Collection<Referee> findAll() {

				return null;

			}

			public Referee findOne(final int refereeId) {

				return null;

			}

			public Referee save(final Referee referee) {

				return null;

			}

			public void delete(final Referee referee) {

			}
			
			// Other business methods -----------------------------------------

}
