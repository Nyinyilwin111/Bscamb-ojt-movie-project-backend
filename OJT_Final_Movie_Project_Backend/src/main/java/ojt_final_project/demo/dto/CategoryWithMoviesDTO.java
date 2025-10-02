package ojt_final_project.demo.dto;

import java.util.List;

public class CategoryWithMoviesDTO {
    private String categoryName;
    private List<String> movieNames;

    public CategoryWithMoviesDTO(String categoryName, List<String> movieNames) {
        this.categoryName = categoryName;
        this.movieNames = movieNames;
    }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public List<String> getMovieNames() { return movieNames; }
    public void setMovieNames(List<String> movieNames) { this.movieNames = movieNames; }
}
