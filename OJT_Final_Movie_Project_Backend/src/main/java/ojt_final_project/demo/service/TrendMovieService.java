package ojt_final_project.demo.service;

import java.util.List;

import ojt_final_project.demo.dto.MovieLikeCountDTO;
import ojt_final_project.demo.entity.Trend_movie;

public interface TrendMovieService {
	public Trend_movie save(Trend_movie trendMovie);
	
	public List<MovieLikeCountDTO> getTopLikedMovies();
	
	public Trend_movie updateTrendMovie(int id, Trend_movie trendMovie);
	
	public Trend_movie checkTrendMove(int userId, int movieId);
	
	public List<Trend_movie> getTrendMovieAll();
	 
}
