
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.SponsorshipRepository;

@Service
@Transactional
public class SponsorshipService {

	//Managed Repository

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

}
