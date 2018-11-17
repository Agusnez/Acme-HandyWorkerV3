package repositories;



import org.springframework.data.jpa.repository.JpaRepository;

import domain.Section;

public interface SectionRepository extends JpaRepository<Section, Integer>{

}
