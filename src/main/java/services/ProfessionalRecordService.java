package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ProfessionalRecordRepository;

import domain.ProfessionalRecord;

@Service
@Transactional
public class ProfessionalRecordService {

	// Managed Repository ------------------------
	@Autowired
	private ProfessionalRecordRepository professionalRecordRepository;
	
	// Suporting services ------------------------
	
	// Simple CRUD methods -----------------------
	
	public ProfessionalRecord create(){
		return null;
	}
	
	public Collection<ProfessionalRecord> findAll(){
		return null;
	}
	
	public ProfessionalRecord findOne(int professionalRecordId){
		return null;
	}
	
	public ProfessionalRecord save(ProfessionalRecord professionalRecord){
		return null;
	}
	
	public void delete(ProfessionalRecord professionalRecord){
		
	}
	
	// Other business methods -----------------------
}
