package ojt_final_project.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ojt_final_project.demo.dto.MovieLikeCountDTO;
import ojt_final_project.demo.entity.Trend_movie;
import ojt_final_project.demo.repo.TrendMovieRepo;

@Service
public class TrendMovieServiceImplement implements TrendMovieService{
	
	@Autowired
	TrendMovieRepo trendMovieRepo;

	@Override
	public Trend_movie save(Trend_movie trendMovie) {
		// TODO Auto-generated method stub
		return trendMovieRepo.save(trendMovie);
	}

	@Override
	public List<MovieLikeCountDTO> getTopLikedMovies() {
		List<Object[]> result = trendMovieRepo.findTopLikedMovies();
        List<MovieLikeCountDTO> topMovies = new ArrayList<>();

        for (Object[] row : result) {
            int movieId = (Integer) row[0];
            long likeCount = ((Number) row[1]).longValue(); 
            topMovies.add(new MovieLikeCountDTO(movieId,likeCount));
        }

        return topMovies;
	}

	@Override
	public Trend_movie updateTrendMovie(int id, Trend_movie trendMovie) {
	    Optional<Trend_movie> existingOpt = trendMovieRepo.findById(id);
	    
	    if (existingOpt.isPresent()) {
	        Trend_movie existing = existingOpt.get();

	        // Update only the necessary fields
	        existing.setLike(trendMovie.getLike());

	        // Optional: update these only if you're sending user/movie again
	        if (trendMovie.getUser() != null) {
	            existing.setUser(trendMovie.getUser());
	        }
	        if (trendMovie.getMovie() != null) {
	            existing.setMovie(trendMovie.getMovie());
	        }

	        System.out.println("Updating Trend_movie ID: " + id);
	        System.out.println("like is :"+trendMovie.getLike());
	        return trendMovieRepo.save(existing);
	    } else {
	        return null;
	    }
	}


	@Override
	public Trend_movie checkTrendMove(int userId, int movieId) {

		return trendMovieRepo.findByUser_UserIdAndMovie_Id(userId, movieId).orElse(null);
	}

	@Override
	public List<Trend_movie> getTrendMovieAll() {
		// TODO Auto-generated method stub
		return trendMovieRepo.getTrendMovieAll();
	}


}
