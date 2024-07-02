package train.reservation.system.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import train.reservation.system.entity.ChangePasswordRequest;
import train.reservation.system.entity.LoginRequest;
import train.reservation.system.entity.User;
import train.reservation.system.exception.UnauthorizedException;
import train.reservation.system.exception.UnprocessableEntityException;
import train.reservation.system.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public User login(LoginRequest loginRequest) {
		User user = userRepository.loginUser(loginRequest.getMailId(), loginRequest.getPassword());
		if (user != null)
			return user;
		else
			throw new UnauthorizedException("Invalid Credentials, Try Again");
	}

	public void changePassword(ChangePasswordRequest request) {
		Optional<User> originalUser = userRepository.findById(request.getMailId());
		if (originalUser.isPresent()) {
			if (originalUser.get().getPassword().equals(request.getOldPassword())) {
				userRepository.updatePassword(request.getMailId(), request.getNewPassword());
			} else {
				throw new UnprocessableEntityException("Wrong old password.");
			}
		} else {
			throw new UnprocessableEntityException("Invalid email.");
		}
	}

	public User updateUser(User user) {
		Optional<User> originalUser = userRepository.findById(user.getMailId());
		if (originalUser.isPresent()) {
			return userRepository.save(user);
		} else {
			throw new UnprocessableEntityException("Invalid email.");
		}
	}

	public User registerUser(User user) {
		Optional<User> originalUser = userRepository.findById(user.getMailId());
		if (originalUser.isPresent()) {
			throw new UnprocessableEntityException("Email is already registered.");
		} else {
			return userRepository.save(user);
		}
	}
}
