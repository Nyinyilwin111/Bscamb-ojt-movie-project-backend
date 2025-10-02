package ojt_final_project.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
//@Getter
//@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name="trend_movie")
public class Trend_movie {

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getLike() {
		return like;
	}


	public void setLike(int like) {
		this.like = like;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Movie getMovie() {
		return movie;
	}


	public void setMovie(Movie movie) {
		this.movie = movie;
	}


	@Id // for primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // for auto increament
	private int id;
	
    @Column(name = "`like`") // or better: rename to likes or like_count
    private int like;
	
    @ToString.Exclude
    @JsonBackReference("user-trend")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_user_id", referencedColumnName = "user_id")
    private User user;

	
    @JsonBackReference(value = "movie-trend")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="movie_m_id", referencedColumnName = "m_id")
    private Movie movie;

}
