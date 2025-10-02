package ojt_final_project.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
//@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie_crew")
public class MovieCrew {

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Movie getMovie() {
		return movie;
	}


	public void setMovie(Movie movie) {
		this.movie = movie;
	}


	public Crew getCrew() {
		return crew;
	}


	public void setCrew(Crew crew) {
		this.crew = crew;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonBackReference(value = "movie-movieCrew")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "crew_id")
    @JsonBackReference(value = "crew-movieCrew")
    private Crew crew;


    @Column(length = 100 )
    private String role;

}
