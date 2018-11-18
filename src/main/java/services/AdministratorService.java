package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Administrator;

import repositories.AdministratorRepository;

@Service
@Transactional
public class AdministratorService {

	// Managed Repository ------------------------
	@Autowired
	private AdministratorRepository administratorRepository;
	
	// Suporting services ------------------------
	
	// Simple CRUD methods -----------------------
	
	public Administrator create(){
		return null;
	}
	
	public Collection<Administrator> findAll(){
		return null;
	}
	
	public Administrator findOne(int administratorId){
		return null;
	}
	
	public Administrator save(Administrator administrator){
		return null;
	}
	
	public void delete(Administrator administrator){
		
	}
	
	// Other business methods -----------------------
}
