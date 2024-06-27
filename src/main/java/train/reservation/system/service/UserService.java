package train.reservation.system.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import train.reservation.system.entity.ChangePasswordRequest;
import train.reservation.system.entity.LoginRequest;
import train.reservation.system.entity.User;
import train.reservation.system.exception.UserException;
import train.reservation.system.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public User login(LoginRequest loginRequest) {
		User user = userRepository.loginUser(loginRequest);
		if (user != null)
			return user;
		else
			throw new UserException(HttpStatus.UNAUTHORIZED, "Invalid Credentials, Try Again");
	}

	public void changePassword(ChangePasswordRequest request) {
		Optional<User> originalUser = userRepository.findById(request.getMailId());
		if (originalUser.isPresent()) {
			if (originalUser.get().getPassword().equals(request.getOldPassword())) {
				userRepository.updatePassword(request.getMailId(), request.getNewPassword());
			} else {
				throw new UserException(HttpStatus.UNPROCESSABLE_ENTITY, "Wrong old password.");
			}
		} else {
			throw new UserException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid email.");
		}
	}
}
