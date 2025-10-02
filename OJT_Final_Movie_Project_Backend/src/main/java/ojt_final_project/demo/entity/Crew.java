package ojt_final_project.demo.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
//@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "movieCrews")
@Table(name = "crew")
public class Crew {
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

	public LocalDate getDate_of_birth() {
		return date_of_birth;
	}

	public void setDate_of_birth(LocalDate date_of_birth) {
		this.date_of_birth = date_of_birth;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<MovieCrew> getMovieCrews() {
		return movieCrews;
	}

	public void setMovieCrews(List<MovieCrew> movieCrews) {
		this.movieCrews = movieCrews;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false)
    private String name;

    private LocalDate date_of_birth;

    @Column(length = 1000)
    private String image;

    private String link;

    @Column(length = 3000, nullable = false)
    private String description;

    @OneToMany(mappedBy = "crew", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "crew-movieCrew")
    private List<MovieCrew> movieCrews = new ArrayList<>();

}
