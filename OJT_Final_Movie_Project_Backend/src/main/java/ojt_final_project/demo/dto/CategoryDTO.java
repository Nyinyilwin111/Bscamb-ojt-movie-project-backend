package ojt_final_project.demo.dto;

import lombok.Data;

//@Data
public class CategoryDTO {
    private String categoryName;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
