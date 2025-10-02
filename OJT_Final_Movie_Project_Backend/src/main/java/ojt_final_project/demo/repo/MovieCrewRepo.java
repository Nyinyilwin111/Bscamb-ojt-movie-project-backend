package ojt_final_project.demo.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ojt_final_project.demo.entity.MovieCrew;

@Repository
public interface MovieCrewRepo extends JpaRepository<MovieCrew, Integer> {
	
	List<MovieCrew> findByMovie_Id(int movieId);

	Optional<MovieCrew> findByMovieIdAndCrewId(int movieId, int crewId);

	boolean existsByMovieIdAndRoleIgnoreCase(int id, String string);
}
