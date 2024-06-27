package train.reservation.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import train.reservation.system.entity.LoginRequest;
import train.reservation.system.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	@Query(value = "select user from user where mail_id=:loginRequest.mail_id and password=:loginRequest.password", nativeQuery = true)
	User loginUser(LoginRequest loginRequest);

	@Transactional
	@Modifying
	@Query(value = "update user set password=:password where mail_id=:mail", nativeQuery = true)
	void updatePassword(String mail, String password);
}
