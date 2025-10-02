package ojt_final_project.demo.controller;

import java.time.LocalDate;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpSession;
import ojt_final_project.demo.dto.EmailRequest;
import ojt_final_project.demo.dto.OtpRequest;
import ojt_final_project.demo.dto.PasswordChangeRequest;
import ojt_final_project.demo.dto.ResetPasswordRequest;
import ojt_final_project.demo.entity.LoginAccess;
import ojt_final_project.demo.entity.User;
import ojt_final_project.demo.service.OtpService;
import ojt_final_project.demo.service.StorageService;
import ojt_final_project.demo.service.UserService;
import option.AESUtil;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:8085")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	StorageService storageService;
	
	@Autowired
	OtpService otpService;
	
	
	
	@PostMapping(value = "/save", consumes = "multipart/form-data")
	public ResponseEntity<?> save(
	    @RequestParam("name") String name,
	    @RequestParam("gmail") String gmail,
	    @RequestParam("password") String password,
	    @RequestParam(value = "joined_date", required = false) LocalDate joined_date,
	    @RequestParam(value = "image", required = false) MultipartFile image) throws Exception {

		if (name.length() > 254) {
	          return ResponseEntity
	                  .badRequest()
	                  .body(Map.of("error", "User Name is Maximum 255 characters allowed"));
	      }else if(name.length() < 3 ) {
	        return ResponseEntity
	                  .badRequest()
	                  .body(Map.of("error", "User Name at least 3 characters must  be add."));
	      }      
	      String gmailRegex = "^[A-Za-z0-9._%+-]+@gmail\\.com$";
	      if (!gmail.matches(gmailRegex)) {
	          return ResponseEntity
	                  .badRequest()
	                  .body(Map.of("error", "Invalid Gmail format. Must be a valid @gmail.com address"));
	      }      
	      if (password.length() < 6) {
	          return ResponseEntity
	                  .badRequest()
	                  .body(Map.of("error", "Password must be at least 6 characters long"));
	      }
	      
	    User user = new User();
	    user.setName(name);
	    user.setGmail(gmail);
        String rawPassword = AESUtil.decrypt(password);
        System.out.println("decrypted password in user controller of save:"+rawPassword);
	    String encodedPassword = passwordEncoder.encode(rawPassword);
	    user.setPassword(encodedPassword);

	    if (joined_date == null) {
	        joined_date = LocalDate.now();
	    }
	    user.setJoined_date(joined_date);

	    if (image != null && !image.isEmpty()) {
	        byte[] imageBytes = image.getBytes();
	        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
	        user.setImage(base64Image);
	    }

	    User result = userService.save(user);

	    return result != null
	        ? new ResponseEntity<>(result, HttpStatus.CREATED)
	        : new ResponseEntity<>(
	              Collections.singletonMap("error", "Unable to save user"),
	              HttpStatus.BAD_REQUEST
	          );
	}
	
	 @GetMapping("/check-email/{gmail}")
	    public boolean checkEmail(@PathVariable String gmail) {
	        return userService.existsByEmail(gmail);
	    }
	
	@PostMapping("/signin")
	  public ResponseEntity<User> check(@RequestBody LoginAccess login) {
	      System.out.println("Email: " + login.getEmail());
	      System.out.println("Encrypted Password: " + login.getPassword());

	      try {
	          User user = userService.loginCheck(login.getEmail(), login.getPassword());
	          System.out.println("Login successful for: " + user.getGmail());
	          return ResponseEntity.ok(user);
	      } 
	      catch (ResponseStatusException ex) {
	          // Let your service-defined status codes (403, 404, 401) propagate
	          throw ex;
	      } 
	      catch (Exception e) {
	          e.printStackTrace();
	          // Generic server error (500)
	          throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
	      }
	  }

	@GetMapping("/getuser") // for get all user
	public ResponseEntity<List<User>> getArr() {
		List<User> stds = userService.get();
		return new ResponseEntity<List<User>>(stds, HttpStatus.ACCEPTED);

	}
	
	@DeleteMapping("/delete/{id}")   // for user delete
	public ResponseEntity<String> delete(@PathVariable int id) {

		return new ResponseEntity<String>(userService.delete(id), HttpStatus.CREATED);
	}
	
	@PutMapping("/update")   // for user update
	public ResponseEntity<User> update(@RequestBody User user) {
		return new ResponseEntity<User>(userService.update(user), HttpStatus.ACCEPTED);

	}
	@PutMapping("/updatestatus")
	public ResponseEntity<User> updateStatus(@RequestBody User user) {
		return new ResponseEntity<User>(userService.updateStatus(user), HttpStatus.ACCEPTED);

	}

	@PutMapping("/{id}/image-upload")
	public ResponseEntity<User> uploadImage(
	        @PathVariable("id") int userId,
	        @RequestParam("image") MultipartFile imageFile) {

	    if (imageFile.isEmpty()) {
	        return ResponseEntity.badRequest().body(null);
	    }

	    try {
	        String fileType = imageFile.getContentType(); 
	        if (fileType == null) {
	            return ResponseEntity.badRequest().body(null);
	        }

	        String filePath = storageService.create(imageFile, fileType);

	        User updatedUser = userService.updateProfileImage(userId, filePath);
	        return ResponseEntity.ok(updatedUser);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}

	@GetMapping("/user/{id}/image")  // not use
	public ResponseEntity<?> getUserWithID(
			@PathVariable("id") int userID
	) {
		User user = userService.get(userID);
		if (user == null) {
			return ResponseEntity.badRequest().body("User ID is invalid");
		}
		return ResponseEntity.ok().body(user);
	}
	
	@GetMapping("/current-user")  // not use
	public ResponseEntity<User> getCurrentUser(HttpSession session) {
	    User user = (User) session.getAttribute("user");
	    if (user != null) {
	        return ResponseEntity.ok(user);
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
	}
	
	@PostMapping("/{userId}/change-password")
    public ResponseEntity<?> changePassword(
        @PathVariable int userId,
        @RequestBody PasswordChangeRequest request
    ) {
        try {
            User updatedUser = userService.changePassword(userId, request.getOldPassword(), request.getNewPassword());
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        }
    }

//	----------------------------otp mail--------------------------------------------
	@PostMapping("/send")
	public String sendOtp(@RequestBody EmailRequest request) {
	    if (!userService.existsByEmail(request.getEmail())) {
	        return "Email not found";
	    }
	    otpService.sendOtp(request.getEmail());
	    return "OTP sent to " + request.getEmail();
	}

	@PostMapping("/verify")
	public String verifyOtp(@RequestBody OtpRequest request) {
	    return otpService.verifyOtp(request.getEmail(), request.getOtp())
	            ? "OTP Verified"
	            : "Invalid or Expired OTP";
	}

	@PostMapping("/reset-password")
	public String resetPassword(@RequestBody ResetPasswordRequest request) {
	    if (!otpService.verifyOtp(request.getEmail(), request.getOtp())) {
	        return "Invalid or Expired OTP";
	    }
	    String encodedPassword = passwordEncoder.encode(request.getNewPassword());
	    userService.updatePasswordByEmail(request.getEmail(), encodedPassword);
	    return "Password updated successfully";
	}


}
