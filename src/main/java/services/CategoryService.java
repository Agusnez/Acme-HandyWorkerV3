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

//	/*Dado que al crear la raíz "CATEGORY" no se especifica el padre*/
//	public Category createRoot() {
//		Collection<Category> categories = findAll();
//		
//		/*comprobamos que no haya ninguna categoría todavía*/
//		Assert.isTrue(categories.isEmpty());
//		Category c = new Category();
//		
//		/*la raíz debe tener por nombre "CATEGORY"*/
//		c.setName("CATEGORY");
//		
//		return c;
//	}
	
	/*para las demás categorias que se creen se ha de especificar el padre*/
	public Category create(){
		Category c;
		
//		Assert.notNull(parent);
//		/*comprobamos que el padre este guardado como categoría*/
//		Assert.isTrue(categoryRepository.exists(parent.getId()));
//		Assert.isTrue(!name.isEmpty());
//		
//		c = new Category();
//		Assert.isTrue(!name.isEmpty());
//		c.setName(name);
//		c.setParent(parent);
//		
//		return c;
		
		c = new Category();
		
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

		/*Agustín pone este Assert*/
		// para delete Assert.isTrue(categoryId!=0);
		c = categoryRepository.findOne(categoryId);
		Assert.notNull(c);

		return c;
	}

	public Category save(Category category) {
		Category c;

		Assert.notNull(category);
		/* compruebo que sea único */
		// unico el nombre Assert.isTrue(!categoryRepository.exists(category.getId()));
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
