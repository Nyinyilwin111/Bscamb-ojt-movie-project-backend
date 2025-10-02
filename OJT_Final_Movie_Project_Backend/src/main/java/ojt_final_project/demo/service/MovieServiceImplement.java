 package ojt_final_project.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ojt_final_project.demo.entity.Category;
import ojt_final_project.demo.entity.Crew;
import ojt_final_project.demo.entity.Movie;
import ojt_final_project.demo.repo.CategoryReposity;
import ojt_final_project.demo.repo.CrewReposity;
import ojt_final_project.demo.repo.MovieReposity;

@Service
public class MovieServiceImplement implements MovieService {

	@Autowired
	MovieReposity movieRepo;
	
	@Autowired
	CategoryReposity categoryRepo;
	
	@Autowired
	CrewReposity crewRepo;

	@Override
	public List<Movie> getAll() {
		// TODO Auto-generated method stub
		return movieRepo.findAll();
	}

	@Override
	public Movie get(int id) {
		// TODO Auto-generated method stub
		return movieRepo.findById(id).orElse(null);
	}

	@Override
	public Movie create(Movie movie) {
		return movieRepo.save(movie);
	}

	@Override
	public Movie update(int id, Movie movie) {
		Movie toUpdateMovie = this.get(id);
		if (toUpdateMovie == null) {
			return null;
		}
		toUpdateMovie.setName(movie.getName());
		toUpdateMovie.setTrailer(movie.getTrailer());
		toUpdateMovie.setMovie_created_date(movie.getMovie_created_date());
		toUpdateMovie.setCategory(movie.getCategory());
		toUpdateMovie.setMovie_poster(movie.getMovie_poster());
		toUpdateMovie.setMovies(movie.getMovies());
		toUpdateMovie.setDiscuss(movie.getDiscuss());
		toUpdateMovie.setTrend_movies(movie.getTrend_movies());
		movieRepo.save(toUpdateMovie);
		return toUpdateMovie;
	}

	@Override
	public boolean delete(int id) {
	    Movie movie = this.get(id);
	    if (movie == null) {
	        return false;
	    }

	    movie.getMovieCrews().clear();  
	    movieRepo.save(movie);      

	    movieRepo.deleteById(id);
	    return true;
	}



	@Override
	public List<Movie> getAllByCategory(Category category) {
		return movieRepo.findByCategory(category);
	}

	@Override
	public Movie getByTitle(String name) {
		return movieRepo.findByName(name);
	}

	@Override
	public List<Movie> getMoviesByCrewName(String crewName) {
		// TODO Auto-generated method stub
		return movieRepo.findMoviesByCrewName(crewName);
	}

}
