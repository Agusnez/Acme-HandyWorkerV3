package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.MiscellaneousRecord;

import repositories.MiscellaneousRecordRepository;

@Service
@Transactional
public class MiscellaneousRecordService {

	// Managed Repository ------------------------
	@Autowired
	private MiscellaneousRecordRepository miscellaneousRecordRepository;
	
	// Suporting services ------------------------
	
	// Simple CRUD methods -----------------------
	
	public MiscellaneousRecord create(){
		return null;
	}
	
	public Collection<MiscellaneousRecord> findAll(){
		return null;
	}
	
	public MiscellaneousRecord findOne(int miscellaneousRecordId){
		return null;
	}
	
	public MiscellaneousRecord save(MiscellaneousRecord miscellaneousRecord){
		return null;
	}
	
	public void delete(MiscellaneousRecord miscellaneousRecord){
		
	}
	
	// Other business methods -----------------------
}
