package ojt_final_project.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ojt_final_project.demo.entity.Category;
import ojt_final_project.demo.entity.Movie;

@Repository
public interface MovieReposity extends JpaRepository<Movie, Integer> {

	public List<Movie> findByCategory(Category category);
	
	public Movie findByName(String name);
	
	@Query("SELECT m FROM Movie m JOIN m.movieCrews mc JOIN mc.crew c WHERE LOWER(c.name) = LOWER(:crewName)")
	List<Movie> findMoviesByCrewName(@Param("crewName") String crewName);
	
}
