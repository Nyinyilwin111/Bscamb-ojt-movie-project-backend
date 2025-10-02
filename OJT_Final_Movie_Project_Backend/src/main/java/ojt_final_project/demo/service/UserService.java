package ojt_final_project.demo.service;

import java.util.List;
import java.util.Optional;

import ojt_final_project.demo.entity.User;

public interface UserService {

	public User get(int id);
	
	public List<User>get();
	
	public User save(User user);
	
	public String getInfo(User user);
	
	public String delete(int id);
	
	public User update(User user);
	
	public User updateStatus(User user);
	
	public Optional<User> findByGmail(String gmail);
	
	public User loginCheck(String email, String password);

	public User updateProfileImage(int userId, String imageUrl);
	
	public User changePassword(int userId, String oldPassword, String newPassword);
	
	public User checkWithGmail(User user);

	boolean existsByEmail(String email);

	int updatePasswordByEmail(String email, String encodedPassword);

}
