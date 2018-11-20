
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.LoginService;
import security.UserAccount;
import domain.HandyWorker;

@Service
@Transactional
public class HandyWorkerService {

	//Managed repository---------------------------------
	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;


	//Suporting services---------------------------------

	//Simple CRUD methods--------------------------------
	public HandyWorker create() { //Lo de los 4 Boxes   y crear finder
		HandyWorker hw;
		hw = new HandyWorker();
		return hw;
	}

	public Collection<HandyWorker> findAll() {
		Collection<HandyWorker> result;
		result = this.handyWorkerRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public HandyWorker findOne(final int handyWorkerId) {
		HandyWorker hw;
		hw = this.handyWorkerRepository.findOne(handyWorkerId);
		Assert.notNull(hw);
		return hw;
	}

	public HandyWorker save(final HandyWorker handyWorker) {
		Assert.notNull(handyWorker);
		HandyWorker hw;
		hw = this.handyWorkerRepository.save(handyWorker);
		return hw;
	}

	public void delete(final HandyWorker handyWorker) {
		Assert.notNull(handyWorker);
		Assert.isTrue(handyWorker.getId() != 0);
		//Faltan cosas con respecto a las aplication y todo lo demas
		this.handyWorkerRepository.delete(handyWorker);
	}

	//Other business methods----------------------------

	public HandyWorker findByPrincipal() {
		HandyWorker hw;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		hw = this.findByUserAccount(userAccount);
		Assert.notNull(hw);

		return hw;
	}

	public HandyWorker findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		HandyWorker result;

		result = this.handyWorkerRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	//MIRAR LOS DOS ULTIMOS DE CUSTOMER DE CERITIFICATION

}
