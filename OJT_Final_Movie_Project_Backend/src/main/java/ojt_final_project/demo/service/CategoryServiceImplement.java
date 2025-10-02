package ojt_final_project.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ojt_final_project.demo.dto.CategoryWithMoviesDTO;
import ojt_final_project.demo.dto.GetIdWithName;
import ojt_final_project.demo.entity.Category;
import ojt_final_project.demo.repo.CategoryReposity;

@Service
public class CategoryServiceImplement implements CategoryService{
	
	@Autowired
	CategoryReposity categoryRepo;

	List<Category> categories=new ArrayList<>();
	
	@Override
	public Category save(Category catName) {
		return categoryRepo.save(catName);
	}

	@Override
	public Category findByCategoryName(String name) {
		System.out.println("in category ser imple(name) :"+name);
		return categoryRepo.findByCategoryName(name);
	}

	@Override
	public List<Category> getAll() {
		return categoryRepo.findAll();
	}

	 @Override
	    public String getId(String category) {
	        List<GetIdWithName> categories = categoryRepo.getCategoryID(category);
	        if (!categories.isEmpty()) {
	            return String.valueOf(categories.get(0).getId());
	        }
	        return null;
	    }

	@Override
	public Category get(int id) {
		// TODO Auto-generated method stub
		return categoryRepo.findById(id).orElse(null);
	}

	@Override
	public boolean delete(int id) {
	    Category category = this.get(id);
	    if (category == null) {
	        return false;
	    }   
	    categoryRepo.deleteById(id);
	    return true;
	}

	@Override
	public List<CategoryWithMoviesDTO> getCategoriesByMovieName(String movieName) {
        List<Category> categories = categoryRepo.findCategoriesByMovieName(movieName);

        return categories.stream()
                .map(category -> new CategoryWithMoviesDTO(
                        category.getCategoryName(),
                        category.getMovies().stream()
                                .map(movie -> movie.getName())
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

}
