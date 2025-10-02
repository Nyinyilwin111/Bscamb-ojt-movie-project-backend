package ojt_final_project.demo.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
//@Setter
//@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor 
@Table(name="user")
public class User {

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public LocalDate getJoined_date() {
		return joined_date;
	}

	public void setJoined_date(LocalDate joined_date) {
		this.joined_date = joined_date;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Long getOtpExpiry() {
		return otpExpiry;
	}

	public void setOtpExpiry(Long otpExpiry) {
		this.otpExpiry = otpExpiry;
	}

	public List<Trend_movie> getTrend_movies() {
		return trend_movies;
	}

	public void setTrend_movies(List<Trend_movie> trend_movies) {
		this.trend_movies = trend_movies;
	}

	@Id // for primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // for auto increament
	@Column(name = "user_id")
	private int userId;
	private String name;
	
	@Column(unique=true,length=100)
	private String gmail;
	
	private String password;
	
    @Column(length = 1000)
    private String image;

	
	@Column(nullable = true)
	private LocalDate joined_date;
	
    @Column(nullable = false)
    private String role = "user";
    
    @Column(nullable = false, length = 50)
    private String status="active";
	
    // Add OTP fields here:
    @Column(length = 6)
    private String otp;

    private Long otpExpiry; 
    	
	@ToString.Exclude
	@JsonManagedReference("user-trend")
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Trend_movie> trend_movies = new ArrayList<>();


}
