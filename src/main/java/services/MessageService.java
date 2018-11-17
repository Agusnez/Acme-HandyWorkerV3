
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.MessageRepository;

@Service
@Transactional
public class MessageService {

	//Managed Repository

	@Autowired
	private MessageRepository	messageRepository;

	//Supporting services

	//Simple CRUD methods

}
