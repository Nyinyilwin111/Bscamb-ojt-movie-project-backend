package ojt_final_project.demo.dto;

import lombok.Data;

//@Data
public class PasswordChangeRequest {
	 public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	 private String oldPassword;
     private String newPassword;

}
