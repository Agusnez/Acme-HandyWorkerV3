
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Box;
import domain.Finder;
import domain.HandyWorker;

@Service
@Transactional
public class HandyWorkerService {

	//Managed repository---------------------------------
	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;

	//Suporting services---------------------------------
	@Autowired
	private FinderService			finderService;

	@Autowired
	private BoxService				boxService;

	@Autowired
	private ActorService			actorService;


	//Simple CRUD methods--------------------------------
	public HandyWorker create() {
		HandyWorker hw;
		Finder find;

		find = this.finderService.create();
		hw = new HandyWorker();
		final UserAccount user = new UserAccount();

		find.setHandyWorker(hw);

		hw.setUserAccount(user);

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

		if (handyWorker.getId() != 0) {

			final int id = LoginService.getPrincipal().getId();

			Assert.isTrue(id == handyWorker.getId());
		}

		HandyWorker hw;
		hw = this.handyWorkerRepository.save(handyWorker);

		if (handyWorker.getId() == 0) {
			Box inBox, outBox, trashBox, spamBox;

			inBox = this.boxService.create();
			outBox = this.boxService.create();
			trashBox = this.boxService.create();
			spamBox = this.boxService.create();

			inBox.setName("inBox");
			outBox.setName("outBox");
			trashBox.setName("trashBox");
			spamBox.setName("spamBox");

			inBox.setByDefault(true);
			outBox.setByDefault(true);
			trashBox.setByDefault(true);
			spamBox.setByDefault(true);

			inBox.setActor(hw);
			outBox.setActor(hw);
			trashBox.setActor(hw);
			spamBox.setActor(hw);

			final Collection<Box> boxes = new ArrayList<>();
			boxes.add(spamBox);
			boxes.add(trashBox);
			boxes.add(inBox);
			boxes.add(outBox);

			inBox = this.boxService.saveNewActor(inBox);
			outBox = this.boxService.saveNewActor(outBox);
			trashBox = this.boxService.saveNewActor(trashBox);
			spamBox = this.boxService.saveNewActor(spamBox);

		}
		return hw;
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

	public Collection<HandyWorker> handyWorkersTenPerCentMore() {

		final Collection<HandyWorker> result = this.handyWorkerRepository.handyWorkersTenPerCentMore();
		Assert.notNull(result);
		return result;

	}

	public Collection<HandyWorker> topThreeHandyWorkersComplaints() {

		final Collection<HandyWorker> handyWorkers = this.handyWorkerRepository.rankingHandyWorkersComplaints();
		Assert.notNull(handyWorkers);

		final List<HandyWorker> ranking = new ArrayList<HandyWorker>();
		ranking.addAll(handyWorkers);
		final Collection<HandyWorker> result = ranking.subList(0, 3);

		return result;

	}

}
