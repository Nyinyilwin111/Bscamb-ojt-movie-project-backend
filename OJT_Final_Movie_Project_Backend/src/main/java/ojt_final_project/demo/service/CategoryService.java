package ojt_final_project.demo.service;

import java.util.List;

import ojt_final_project.demo.dto.CategoryWithMoviesDTO;
import ojt_final_project.demo.entity.Category;

public interface CategoryService {
	
	public Category save(Category catName);
	
	public Category findByCategoryName(String name);
	
	public List<Category> getAll();
	
	public String getId(String category);
	
	public Category get(int id);

	public boolean delete(int id);
	
	public List<CategoryWithMoviesDTO> getCategoriesByMovieName(String movieName);
}
