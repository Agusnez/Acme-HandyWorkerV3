package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Tutorial;

public interface TutorialRepository extends JpaRepository<Tutorial, Integer>{

}
