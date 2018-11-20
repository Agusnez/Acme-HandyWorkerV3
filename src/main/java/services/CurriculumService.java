
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import domain.Curriculum;

@Service
@Transactional
public class CurriculumService {

	//Managed repository---------------------------------
	@Autowired
	private CurriculumRepository	curriculumRepository;


	//Suporting services---------------------------------

	//Simple CRUD methods--------------------------------
	public Curriculum create() {
		Curriculum c;
		c = new Curriculum();
		return c;
	}

	public Collection<Curriculum> findAll() {
		Collection<Curriculum> result;
		result = this.curriculumRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Curriculum findOne(final int curriculumId) {
		Curriculum c;
		c = this.curriculumRepository.findOne(curriculumId);
		Assert.notNull(c);
		return c;
	}

	public Curriculum save(final Curriculum curriculum) {
		Assert.notNull(curriculum);
		Curriculum c;
		c = this.curriculumRepository.save(curriculum);
		return c;
	}

	public void delete(final Curriculum curriculum) {
		Assert.notNull(curriculum);
		Assert.isTrue(curriculum.getId() != 0);
		//Faltan cosas con respecto a los RECORDS
		this.curriculumRepository.delete(curriculum);
	}

	//Other business methods----------------------------

}
