package ojt_final_project.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ojt_final_project.demo.entity.User;

@Repository
public interface UserReporisity extends JpaRepository<User, Integer> {

	void deleteByUserId(int userId);
	Optional<User> findByGmail(String gmail);	
	
	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.password = :password WHERE u.gmail = :email")
	int updatePasswordByEmail(@Param("email") String email, @Param("password") String password);

	boolean existsByGmail(String email);

}
