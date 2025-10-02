package ojt_final_project.demo.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ojt_final_project.demo.entity.Trend_movie;

@Repository
public interface TrendMovieRepo extends JpaRepository<Trend_movie, Integer> {
	@Query(value = "SELECT movie_m_id AS movieId, COUNT(*) AS likeCount FROM trend_movie WHERE `like` = 1 " +
	        "GROUP BY movie_m_id " +
	        "ORDER BY likeCount DESC " +
	        "LIMIT 10", nativeQuery = true)
	List<Object[]> findTopLikedMovies();
	
	Optional<Trend_movie> findByUser_UserIdAndMovie_Id(int userId, int movieId);
	
	 @Query(value = "SELECT * FROM trend_movie WHERE `like` = 1", nativeQuery = true)
	 List<Trend_movie> getTrendMovieAll();


}
