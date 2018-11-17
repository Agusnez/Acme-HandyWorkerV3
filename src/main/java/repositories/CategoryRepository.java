package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
