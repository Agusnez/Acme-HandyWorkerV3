package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Warranty;

import repositories.WarrantyRepository;

@Service
@Transactional
public class WarrantyService {

	// Managed Repository ------------------------
	@Autowired
	private WarrantyRepository warrantyRepository;
	
	// Suporting services ------------------------
	
	// Simple CRUD methods -----------------------
	
	public Warranty create(){
		return null;
	}
	
	public Collection<Warranty> findAll(){
		return null;
	}
	
	public Warranty findOne(int warrantyId){
		return null;
	}
	
	public Warranty save(Warranty warranty){
		return null;
	}
	
	public void delete(Warranty warranty){
		
	}
	
	// Other business methods -----------------------
}
