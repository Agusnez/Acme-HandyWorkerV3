
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActorRepository;

@Service
@Transactional
public class ActorService {

	//Managed Repository

	@Autowired
	private ActorRepository	actorRepository;

	//Supporting services

	//Simple CRUD methods

}
