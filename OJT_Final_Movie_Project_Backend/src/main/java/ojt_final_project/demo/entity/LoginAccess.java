package ojt_final_project.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Getter
//@Setter
@ToString
public class LoginAccess {
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private String email;
	private String password;

}
