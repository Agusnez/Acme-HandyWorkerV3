package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.EndorserRecord;

import repositories.EndorserRecordRepository;

@Service
@Transactional
public class EndorserRecordService {

	// Managed Repository ------------------------
	@Autowired
	private EndorserRecordRepository endorserRecordRepository;
	
	// Suporting services ------------------------
	
	// Simple CRUD methods -----------------------
	
	public EndorserRecord create(){
		return null;
	}
	
	public Collection<EndorserRecord> findAll(){
		return null;
	}
	
	public EndorserRecord findOne(int endorserRecordId){
		return null;
	}
	
	public EndorserRecord save(EndorserRecord endorserRecord){
		return null;
	}
	
	public void delete(EndorserRecord endorserRecord){
		
	}
	
	// Other business methods -----------------------
}
