package ojt_final_project.demo.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;
import lombok.ToString;
@Entity
//@Getter
//@Setter
@ToString(exclude = {"movies"})
@NoArgsConstructor
@AllArgsConstructor 
@Table(name="category")
public class Category {

	public int getC_id() {
		return c_id;
	}


	public void setC_id(int c_id) {
		this.c_id = c_id;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public List<Movie> getMovies() {
		return movies;
	}


	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}


	@Id // for primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // for auto increament
	private int c_id;
	
	@Column(name = "category_name", unique = true, length = 100, nullable = false)
	private String categoryName;

	
	@JsonManagedReference
	@OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
	private List<Movie> movies=new ArrayList<>();
}
