package ojt_final_project.demo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
//@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"category", "movieCrews", "trend_movies"})
@Table(name = "movie")
public class Movie {
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getDiscuss() {
		return discuss;
	}

	public void setDiscuss(String discuss) {
		this.discuss = discuss;
	}

	public Date getMovie_created_date() {
		return movie_created_date;
	}

	public void setMovie_created_date(Date movie_created_date) {
		this.movie_created_date = movie_created_date;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<MovieCrew> getMovieCrews() {
		return movieCrews;
	}

	public void setMovieCrews(List<MovieCrew> movieCrews) {
		this.movieCrews = movieCrews;
	}

	public List<Trend_movie> getTrend_movies() {
		return trend_movies;
	}

	public void setTrend_movies(List<Trend_movie> trend_movies) {
		this.trend_movies = trend_movies;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "m_id") // keep this to match the DB column
	private int id;

    private String name;

    @Column(length = 200)
    private String movie_poster;

    @Column(length = 1000)
    private String trailer;

    @Column(length = 1000)
    private String movies;

    @Column(length = 3000)
    private String discuss;

    @Temporal(TemporalType.DATE)
    private Date movie_created_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "c_id")
    @JsonBackReference
    private Category category;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "movie-movieCrew")
    private List<MovieCrew> movieCrews = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "movie-trend")
    private List<Trend_movie> trend_movies = new ArrayList<>();
}
