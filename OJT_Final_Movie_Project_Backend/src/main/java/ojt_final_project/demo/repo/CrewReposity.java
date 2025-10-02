package ojt_final_project.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ojt_final_project.demo.entity.Crew;

@Repository
public interface CrewReposity extends JpaRepository<Crew, Integer> {

    @Query(value = """
        SELECT c.* FROM crew c 
        JOIN crew_has_movie chm ON c.id = chm.crew_id 
        WHERE chm.movie_m_id = :movieId
        """, nativeQuery = true)
    List<Crew> findByMovieId(@Param("movieId") int movieId);

    Crew findByName(String name);
}
