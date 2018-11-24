
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import security.Authority;
import domain.Actor;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class MessageService {

	//Managed Repository

	@Autowired
	private MessageRepository	messageRepository;

	//Supporting services

	@Autowired
	private BoxService			boxService;

	@Autowired
	private ActorService		actorService;


	//Simple CRUD methods

	public Message create() {

		Message result;

		Date momentSent;

		momentSent = new Date(System.currentTimeMillis() - 1000);

		result = new Message();
		result.setMoment(momentSent);
		result.setBoxes(new ArrayList<Box>());

		return result;

	}

	public Collection<Message> findAll() {

		final Collection<Message> messages = this.messageRepository.findAll();

		Assert.notNull(messages);

		return messages;
	}

	public Message findOne(final int messageId) {

		final Message message = this.messageRepository.findOne(messageId);

		Assert.notNull(message);

		return message;
	}

	public Message save(final Message message) {

		if (message.getId() != 0)
			Assert.isTrue((message.getSender() == this.actorService.findByPrincipal()) || message.getRecipient() == this.actorService.findByPrincipal());

		Assert.notNull(message);

		Message result;
		Date momentSent;
		momentSent = new Date();
		Assert.isTrue(message.getMoment().before(momentSent));

		result = this.messageRepository.save(message);

		return result;
	}

	public void delete(final Message message) {

		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(message.getRecipient().equals(actor) || message.getSender().equals(actor));

		final Box tb = this.boxService.findTrashBoxByActorId(actor.getId());
		if (message.getBoxes().contains(tb))
			this.messageRepository.delete(message);

		message.getBoxes().add(tb);

	}

	public void broadcastSystem(final Message message) {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(!(actor.getUserAccount().getAuthorities().contains(authority)));

		final Collection<Actor> actores = this.actorService.findAll();
		actores.remove(actor);

		for (final Actor a : actores) {

			final Box ib = this.boxService.findInBoxByActorId(a.getId());
			message.getBoxes().add(ib);

		}

		this.messageRepository.save(message);

	}

}
