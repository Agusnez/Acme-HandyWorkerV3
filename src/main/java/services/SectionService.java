package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import domain.HandyWorker;
import domain.Section;

@Service
@Transactional
public class SectionService {
	
	// Managed Repository ------------------------
	@Autowired
	private SectionRepository sectionRepository;
	
	// Suporting services ------------------------
	
	private HandyWorkerService handyWorkerService;
	
	// Simple CRUD methods -----------------------
	
	public Section create(){
		HandyWorker handyWorker = handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);
		
		Section s;
		
		s = new Section();
		
		return s;
	}
	
	public Collection<Section> findAll(){
		HandyWorker handyWorker = handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);
		
		Collection<Section> sections;
		
		Assert.notNull(sectionRepository);
		sections = sectionRepository.findAll();
		Assert.notNull(sections);
		
		return sections;
	}
	
	public Section findOne(int sectionId){
		HandyWorker handyWorker = handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);
		
		Section s;
		
		Assert.isTrue(sectionId!=0);
		s = sectionRepository.findOne(sectionId);
		Assert.notNull(s);
		
		return s;
	}
	
	public Section save(Section section){
		HandyWorker handyWorker = handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);
		
		Section s;
		
		s = sectionRepository.save(section);
		
		return s;
	}
	
	public void delete(Section section){
		HandyWorker handyWorker = handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);
		
		sectionRepository.delete(section);
		
	}
	
	// Other business methods -----------------------

}
