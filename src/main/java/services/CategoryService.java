package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public Category create(){
		return null;
	}
	
	public Collection<Category> findAll(){
		return null;
	}
	
	public Category findOne(int categoryId){
		return null;
	}
	
	public Category save(Category category){
		return null;
	}
	
	public void delete(Category category){
		
	}
	
	// Other business methods -----------------------
	
	
}
