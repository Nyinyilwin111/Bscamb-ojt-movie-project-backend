package ojt_final_project.demo.dto;

import java.util.List;
import lombok.Data;

//@Data
public class MovieDTO {
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMovie_poster() {
		return movie_poster;
	}
	public void setMovie_poster(String movie_poster) {
		this.movie_poster = movie_poster;
	}
	public String getTrailer() {
		return trailer;
	}
	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}
	public String getMovies() {
		return movies;
	}
	public void setMovies(String movies) {
		this.movies = movies;
	}
	public String getMovie_created_date() {
		return movie_created_date;
	}
	public void setMovie_created_date(String movie_created_date) {
		this.movie_created_date = movie_created_date;
	}
	public CategoryDTO getCategory() {
		return category;
	}
	public void setCategory(CategoryDTO category) {
		this.category = category;
	}
	public List<CrewDTO> getCrews() {
		return crews;
	}
	public void setCrews(List<CrewDTO> crews) {
		this.crews = crews;
	}
	private String name;
    private String description;
	private String movie_poster;
    private String trailer;
    private String movies;
    private String movie_created_date;
    private CategoryDTO category;
    private List<CrewDTO> crews;
}
