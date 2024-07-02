package train.reservation.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import train.reservation.system.entity.ChangePasswordRequest;
import train.reservation.system.entity.LoginRequest;
import train.reservation.system.entity.User;
import train.reservation.system.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/login")
	public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
		User userLoggedIn = userService.login(loginRequest);
		return new ResponseEntity<>(userLoggedIn, HttpStatus.OK);
	}

	@PatchMapping("/changePassword")
	public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
		userService.changePassword(request);
		return new ResponseEntity<>("Password has been updated successfully", HttpStatus.OK);
	}

	@PutMapping("/updateUserProfile")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		User updatedUser = userService.updateUser(user);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	@PostMapping("/registerUser")
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		User savedUser = userService.registerUser(user);
		return new ResponseEntity<>(savedUser, HttpStatus.OK);
	}
}
