package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import repositories.MiscellaneousRecordRepository;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	// Managed Repository ------------------------
	@Autowired
	private MiscellaneousRecordRepository miscellaneousRecordRepository;
	
	// Suporting services ------------------------
	
	private HandyWorkerRepository handyWorkerRepository;
	
	// Simple CRUD methods -----------------------
	
	public MiscellaneousRecord create(){
		MiscellaneousRecord m;
		
		m = new MiscellaneousRecord();
		
		return m;
	}
	
	public Collection<MiscellaneousRecord> findAll(){
		Collection<MiscellaneousRecord> res;
		
		res = miscellaneousRecordRepository.findAll();
		
		return res;
	}
	
	public MiscellaneousRecord findOne(int miscellaneousRecordId){
		
		MiscellaneousRecord m; 
		
		m = miscellaneousRecordRepository.findOne(miscellaneousRecordId);
		Assert.notNull(m);
		
		return m;
		
		
	}
	
	public MiscellaneousRecord save(MiscellaneousRecord miscellaneousRecord){
		return null;
		//duda
	}
	
	public void delete(MiscellaneousRecord miscellaneousRecord){
		
		HandyWorker handyWorker = HandyWorkerService.findB
		
	}
	
	// Other business methods -----------------------
}
