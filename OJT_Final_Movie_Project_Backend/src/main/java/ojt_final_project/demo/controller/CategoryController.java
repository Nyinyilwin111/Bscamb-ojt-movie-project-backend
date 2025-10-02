package ojt_final_project.demo.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ojt_final_project.demo.dto.CategoryDTO;
import ojt_final_project.demo.dto.CategoryWithMoviesDTO;
import ojt_final_project.demo.entity.Category;
import ojt_final_project.demo.service.CategoryService;
import ojt_final_project.demo.service.MovieService;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    
    @Autowired
    MovieService movieService;

    @GetMapping("/getId")
    public int getCategoryIdWithName(@RequestParam String name) {
        Category category = categoryService.findByCategoryName(name);
        if (category != null) {
            System.out.println("In category controller, ID is: " + category.getC_id());
            return category.getC_id();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with name: " + name);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Category> saveCategory(@RequestBody Category newCate) {
        System.out.println("Saving category: " + newCate.getCategoryName() + ", ID: " + newCate.getC_id());
        Category result = categoryService.save(newCate);
        
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.CREATED); // 201 Created
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // 400 Bad Request
        }
    }
    
    @GetMapping("/getAllCategory")
    public ResponseEntity<List<Category>> getAll() {
        List<Category> categories = categoryService.getAll();
        
        if (categories == null || categories.isEmpty()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
        }

        categories.forEach(category -> System.out.println("Category: " + category));

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    
    @PostMapping("/get/categoryId")  // not use 
    public ResponseEntity<String> getCategoryId(@RequestBody CategoryDTO request) {
        String id = categoryService.getId(request.getCategoryName());
        return id != null 
            ? new ResponseEntity<>(id, HttpStatus.OK)
            : new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
    }
    
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable int id) {
		 Category category = categoryService.get(id);
		    if (category == null) {
		        return ResponseEntity.notFound().build();
		    }
		    

		    boolean isDeleted = categoryService.delete(id);
		    if (!isDeleted) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete movie");
		    }

		    return ResponseEntity.ok("Movie and associated files deleted successfully");
		
	}
	
	@GetMapping("/movie/{movieName}")
    public List<CategoryWithMoviesDTO> getCategoriesByMovieName(@PathVariable String movieName) {
		System.out.println("movie name is : "+movieName);
        return categoryService.getCategoriesByMovieName(movieName);
    }
}
