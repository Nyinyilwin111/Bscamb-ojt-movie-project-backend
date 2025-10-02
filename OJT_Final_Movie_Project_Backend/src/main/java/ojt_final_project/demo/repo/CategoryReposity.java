package ojt_final_project.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ojt_final_project.demo.dto.GetIdWithName;
import ojt_final_project.demo.entity.Category;

@Repository
public interface CategoryReposity extends JpaRepository<Category, Integer> {

	 Category findByCategoryName(String categoryName);
	 
	 @Query(value = "SELECT c_id AS id FROM category WHERE LOWER(category_name) = LOWER(:category_name)", nativeQuery = true)
	 List<GetIdWithName> getCategoryID(@Param("category_name") String category_name);

	 @Query("SELECT c FROM Category c JOIN c.movies m WHERE m.name = :movieName")
	    List<Category> findCategoriesByMovieName(@Param("movieName") String movieName);

}

