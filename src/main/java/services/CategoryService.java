package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Category;

@Service
@Transactional
public class CategoryService {

	// Managed Repository ------------------------

	@Autowired
	private CategoryRepository categoryRepository;

	// Suporting services ------------------------

	// Simple CRUD methods -----------------------

	public Category create() {
		Category c = new Category();

		return c;
	}

	/* no sé si es necesario este método porque no nos aporta nada en principio */
	public Collection<Category> findAll() {
		Collection<Category> res;

		/* he visto este Assert en Acme-Certificacion (AnnouncementService) */
		Assert.notNull(this.categoryRepository);
		res = categoryRepository.findAll();
		Assert.notNull(res);

		return res;

	}

	public Category findOne(int categoryId) {
		Category c;

		c = categoryRepository.findOne(categoryId);
		Assert.notNull(c);

		return c;
	}

	public Category save(Category category) {
		Category c;

		/* compruebo que sea único */
		Assert.isTrue(!categoryRepository.exists(category.getId()));
		c = categoryRepository.save(category);

		return c;
	}

	public void delete(Category category) {
		/* compruebo que exista */
		Assert.isTrue(categoryRepository.exists(category.getId()));

		/* compruebo que no tenga hijos (hojas) */
		Collection<Category> children = categoryRepository
				.findChildren(category.getId());
		Assert.isTrue(children.isEmpty());

		categoryRepository.delete(category);

	}

	// Other business methods -----------------------

	/* me hace falta para el delete y considero además que es necesario */
	public Collection<Category> findChildren(int categoryId) {
		Collection<Category> res;

		res = categoryRepository.findChildren(categoryId);
		Assert.notNull(res);

		return res;
	}

}
