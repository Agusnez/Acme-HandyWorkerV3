
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;
import domain.FixUpTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest {

	//Service under test ------------------------------------------
	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	//Tests -------------------------------------------------------

	// Simple CRUD methods -----------------------
	@Test
	public void testCreateCategory() {
		super.authenticate("admin");

		Category c;
		c = this.categoryService.create();
		Assert.notNull(c.getParent());
		Assert.isNull(c.getName());

		super.authenticate(null);
	}

	@Test
	public void testSaveCategory() {
		super.authenticate("admin");

		Category c, saved;
		c = this.categoryService.create();
		c.setName("zapatos"); /* NO POPULAR ESTA CATEGORY */

		saved = this.categoryService.save(c);

		final Collection<Category> categories = this.categoryService.findAll();
		Assert.isTrue(categories.contains(saved));

		super.authenticate(null);

	}

	@Test
	public void testSaveDuplicate() {
		super.authenticate("admin");

		Category c1, c2, saved1;

		c1 = this.categoryService.create();
		c1.setName("zapatos"); /* NO POPULAR ESTA CATEGORY */

		c2 = this.categoryService.create();
		c2.setName("zapatos");

		saved1 = this.categoryService.save(c1);

		final Collection<Category> categories = this.categoryService.findAll();

		Assert.isTrue(categories.contains(saved1));

		try {
			this.categoryService.save(c2);
		} catch (final Exception e) {

		}
		final Collection<Category> categories2 = this.categoryService.findAll();

		for (final Category c : categories2)
			Assert.isTrue(!(c.getName() == saved1.getName() && c.getId() != saved1.getId()));

		super.authenticate(null);

	}

	@Test
	public void testDeleteCategory() {
		super.authenticate("admin");

		Category c, saved;
		c = this.categoryService.create();
		c.setName("zapatos"); /* NO POPULAR ESTA CATEGORY */

		saved = this.categoryService.save(c);

		this.categoryService.delete(saved);

		super.authenticate(null);

	}

	@Test
	public void testDeleteCATEGORY() {
		super.authenticate("admin");
		Category c;

		c = this.categoryService.findByName("CATEGORY");

		try {

			this.categoryService.delete(c);

		} catch (final Exception e) {

		}

		final Collection<Category> categories = this.categoryService.findAll();
		Assert.isTrue(categories.contains(c));

		super.authenticate(null);

	}

	@Test
	public void testDeleteCategoryUnlogged() { /* Con este test vamos a comprobar la seguridad de autenticación */
		super.authenticate("customer1"); /* para poder acceder al findOne */

		final Category category2 = this.categoryService.findOne(2594); /* Category 2 en el populate */

		super.authenticate(null);
		/* ahora estando deslogeado */

		try {
			this.categoryService.delete(category2);
		} catch (final Exception e) {

		}

		/* un personaje se logea y busca nuestra category víctima */
		super.authenticate("admin");
		final Category categoryFind = this.categoryService.findOne(2594);

		Assert.notNull(categoryFind);

		super.authenticate(null);

	}

	/* TODO: Da error, los id's son de la BBDD y se corresponden bien... */
	@Test
	public void testDeleteCategoryWithChildrenAndFixUpAssociated() {
		super.authenticate("admin");

		final Category category2 = this.categoryService.findOne(super.getEntityId("category2"));/* categoria padre */
		final Category category4 = this.categoryService.findOne(super.getEntityId("category4")); /* categoría hija */
		final FixUpTask fixUpTask = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask1")); /* FixUp que tiene como categoría la categor2 */

		this.categoryService.delete(category2);

		Assert.isTrue(category4.getParent().equals(category2.getParent()));
		Assert.isTrue(fixUpTask.getCategory().equals(category2.getParent()));

		super.authenticate(null);

	}

}
