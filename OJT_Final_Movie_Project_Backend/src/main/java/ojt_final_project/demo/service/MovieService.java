package ojt_final_project.demo.service;

import java.util.List;

import ojt_final_project.demo.entity.Category;
import ojt_final_project.demo.entity.Movie;

public interface MovieService {

	public List<Movie> getAll();

	public Movie get(int id);

	public Movie create(Movie movie);

	public Movie update(int id, Movie movie);

	public boolean delete(int id);

	public List<Movie> getAllByCategory(Category category);

	public Movie getByTitle(String title);
	
	List<Movie> getMoviesByCrewName(String crewName);
	


	
}
