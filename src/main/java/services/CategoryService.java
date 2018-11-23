
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import security.Authority;
import domain.Actor;
import domain.Category;
import domain.FixUpTask;

@Service
@Transactional
public class CategoryService {

	// Managed Repository ------------------------

	@Autowired
	private CategoryRepository	categoryRepository;

	// Suporting services ------------------------

	@Autowired
	private FixUpTaskService	fixUpTaskService;

	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods -----------------------

	public Category create() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(!(actor.getUserAccount().getAuthorities().contains(authority)));

		Category result;

		final Category root = this.findRoot("CATEGORY");

		result = new Category();

		result.setParent(root);

		return result;

	}

	public Collection<Category> findAll() {

		Assert.notNull(this.categoryRepository);

		final Collection<Category> res = this.categoryRepository.findAll();

		Assert.notNull(res);

		return res;

	}

	public Category findOne(final int categoryId) {

		Assert.isTrue(categoryId != 0);

		Assert.notNull(this.categoryRepository);

		final Category result = this.categoryRepository.findOne(categoryId);

		Assert.notNull(result);

		return result;
	}

	public Category save(final Category category) {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(!(actor.getUserAccount().getAuthorities().contains(authority)));

		Assert.isTrue(category.getId() != 0);
		Assert.isTrue(!(category.getName().equals("CATEGORY")));
		Assert.notNull(category);

		final Category result = this.categoryRepository.save(category);

		return result;
	}

	public void delete(final Category category) {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(!(actor.getUserAccount().getAuthorities().contains(authority)));

		//compruebo que exista
		Assert.isTrue(category.getId() != 0);
		//compruebo que no es la categoria "CATEGORY" 
		Assert.isTrue(!(category.getName().equals("CATEGORY")));

		final Collection<Category> children = this.categoryRepository.findChildren(category.getId());
		final Collection<FixUpTask> fixUp = this.fixUpTaskService.findFixUpTaskPerCategory(category.getId());

		if (children != null || !(children.isEmpty()))
			for (final Category c : children) {
				final Category cparent = category.getParent();
				c.setParent(cparent);
				this.save(c);
			}

		if (fixUp != null || !(fixUp.isEmpty()))
			for (final FixUpTask f : fixUp) {
				final Category cfix = category.getParent();
				f.setCategory(cfix);
				this.fixUpTaskService.save(f);
			}

		this.categoryRepository.delete(category);

	}
	// Other business methods -----------------------

	public Collection<Category> findChildren(final int categoryId) {

		final Collection<Category> result = this.categoryRepository.findChildren(categoryId);

		Assert.notNull(result);

		return result;
	}

	public Category findRoot(final String categoryName) {

		final Category result = this.categoryRepository.findRoot(categoryName);

		Assert.notNull(result);

		return result;
	}
}
