package ojt_final_project.demo.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ojt_final_project.demo.entity.User;
import ojt_final_project.demo.repo.UserReporisity;
import option.AESUtil;

@Service
public class UserServiceImplement implements UserService {

	@Autowired
	UserReporisity userRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	
	List<User> users=new ArrayList<>();
	
	@Override
	public User get(int id) {
		// TODO Auto-generated method stub
		return userRepo.findById(id).orElse(null);
	}

	@Override
	public List<User> get() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		return userRepo.save(user);
	}

	@Override
	public String getInfo(User user) {
		Period p = Period.between(LocalDate.now(), user.getJoined_date());
																						
		return "ID is : " + user.getUserId() + "\nName : " + user.getName()+
				(p.getYears()+1) + System.lineSeparator();
	}

	@Override
	public String delete(int id) {
		if(userRepo.existsById(id)) {
			userRepo.deleteByUserId(id);
		}
		return null;
	}

	@Override
	public User update(User user) {
		User us = userRepo.findById(user.getUserId()).orElse(null);
		if (us != null) {
			us.setName(user.getName());
			us.setGmail(user.getGmail());
			us.setPassword(user.getPassword());
			us.setImage(user.getImage());
		}
		return userRepo.save(us);
	}

	@Override
	public Optional<User> findByGmail(String gmail) {
		// TODO Auto-generated method stub
		return userRepo.findByGmail(gmail);
	}
	
	@Override
	  public User loginCheck(String email, String encryptedPassword) {
	      try {
	          // Find user
	          Optional<User> userOpt = userRepo.findByGmail(email);

	          if (userOpt.isEmpty()) {
	              System.out.println("User not found: " + email);
	              throw new ResponseStatusException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND");
	          }

	          User user = userOpt.get();

	          // Check account
	          if ("block".equalsIgnoreCase(user.getStatus())) {
	              System.out.println("User is blocked: " + email);
	              throw new ResponseStatusException(HttpStatus.FORBIDDEN, "ACCOUNT_BLOCKED");
	          }

	          // Decrypt password
	          String rawPassword = AESUtil.decrypt(encryptedPassword);
	          System.out.println("Decrypted password: " + rawPassword);

	          // Validate password
	          if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
	              System.out.println("Password mismatch for user: " + email);
	              throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "INVALID_PASSWORD");
	          }

	          return user;

	      } catch (ResponseStatusException e) {
	          // rethrow known errors
	          throw e;
	      } catch (Exception e) {
	          e.printStackTrace();
	          throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "LOGIN_ERROR");
	      }
	  }

	@Override
	public User updateStatus(User user) {
		User us = userRepo.findById(user.getUserId()).orElse(null);
		System.out.println("in us service "+us);
		System.out.println("this is user"+ user);
		if (us != null) {
			us.setStatus(user.getStatus());
		}
		return userRepo.save(us);
	}

	@Override
	public User updateProfileImage(int userId, String imageUrl) {
		 Optional<User> optionalUser = userRepo.findById(userId);
		    if (optionalUser.isPresent()) {
		        User user = optionalUser.get();
		        user.setImage(imageUrl);
		        return userRepo.save(user);
		    } else {
		        throw new RuntimeException("User not found with ID: " + userId);
		    }
	}
	
	 @Override
	    public User changePassword(int userId, String oldPassword, String newPassword) {
	        User user = userRepo.findById(userId)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
	            throw new RuntimeException("Old password is incorrect");
	        }

	        user.setPassword(passwordEncoder.encode(newPassword));
	        return userRepo.save(user);
	    }

	    public void migratePlainPasswordsToBCrypt() {
	        List<User> users = userRepo.findAll();
	        for (User u : users) {
	            if (!u.getPassword().startsWith("$2a$")) { // Not bcrypt
	                u.setPassword(passwordEncoder.encode(u.getPassword()));
	                userRepo.save(u);
	            }
	        }
	    }

		@Override
		public User checkWithGmail(User user) {
			
			return userRepo.findByGmail(user.getGmail()).orElse(null);
		}
		
		@Override
		public boolean existsByEmail(String email) {    // otp amil
		    return userRepo.existsByGmail(email);
		}

		@Override
		public int updatePasswordByEmail(String email, String encodedPassword) {     // otp amil
		    return userRepo.updatePasswordByEmail(email, encodedPassword);
		}

}
