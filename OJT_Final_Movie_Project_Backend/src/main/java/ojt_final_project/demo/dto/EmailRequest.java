package ojt_final_project.demo.dto;

import lombok.Data;

//@Data
public class EmailRequest {
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private String email;
}
