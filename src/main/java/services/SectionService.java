package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	@Autowired
	private HandyWorkerService handyWorkerService;
	
	// Simple CRUD methods -----------------------
	
	public Section create(){
		HandyWorker handyWorker = handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);
		
		Section s;
		
		s = new Section();
		int size = findAll().size();
		s.setNumero(size+1);
		
		return s;
	}
	
	public Collection<Section> findAll(){
		HandyWorker handyWorker = handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);
		
		Collection<Section> sections;
		
		Assert.notNull(sectionRepository);
		sections = sectionRepository.findSectionsOrdered();
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
		
		Collection<Section> sections = findAll();
		
		/*reordeno las secciones reasignando el valor correcto de 'numero'*/
		for(Section s : sections){
			
			if(s.getNumero()>section.getNumero()){
				s.setNumero(s.getNumero()-1);
			}
		}
		sectionRepository.delete(section);
		
	}
	
	// Other business methods -----------------------

}
