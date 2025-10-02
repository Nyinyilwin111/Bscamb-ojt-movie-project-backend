package ojt_final_project.demo.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ojt_final_project.demo.entity.User;
import ojt_final_project.demo.repo.UserReporisity;

@Service
@RequiredArgsConstructor
public class OtpService {

	@Autowired
	 UserReporisity userRepository;
	
	@Autowired
	 JavaMailSender mailSender;
	    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	    public String sendOtp(String email) {
	        String otp = String.format("%06d", new Random().nextInt(999999));
	        long expiryTime = System.currentTimeMillis() + (1 * 60 * 1000); // 5 minutes

	        User user = userRepository.findByGmail(email)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        user.setOtp(otp);
	        user.setOtpExpiry(expiryTime);
	        userRepository.save(user);

	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(email);
	        message.setSubject("Your OTP Code");
	        message.setText("Your OTP is: " + otp + "\nValid for 1 minutes.");
	        mailSender.send(message);

	        return "OTP sent to " + email;
	    }

	    public boolean verifyOtp(String email, String otp) {
	        return userRepository.findByGmail(email)
	                .filter(user -> user.getOtp() != null
	                        && user.getOtpExpiry() >= System.currentTimeMillis()
	                        && user.getOtp().equals(otp))
	                .isPresent();
	    }

	    public String resetPassword(String email, String otp, String newPassword) {
	        if (!verifyOtp(email, otp)) {
	            return "Invalid or Expired OTP";
	        }

	        User user = userRepository.findByGmail(email)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        user.setPassword(passwordEncoder.encode(newPassword));
	        user.setOtp(null);
	        user.setOtpExpiry(null);
	        userRepository.save(user);

	        return "Password reset successfully";
	    }

}
