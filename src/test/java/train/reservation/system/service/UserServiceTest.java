package train.reservation.system.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import train.reservation.system.entity.ChangePasswordRequest;
import train.reservation.system.entity.LoginRequest;
import train.reservation.system.entity.User;
import train.reservation.system.exception.UnauthorizedException;
import train.reservation.system.exception.UnprocessableEntityException;
import train.reservation.system.repository.UserRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	private User user;

	@BeforeEach
	private void setup() {
		MockitoAnnotations.initMocks(this);
		user = new User("Sunny", "Patel", "Testabc123#", "suny@gmail.com");
	}

	@Test
	public void whenUserLoginAndValidDetails_thenCorrectResponse() throws Exception {
		Mockito.when(userRepository.loginUser(Mockito.any(), Mockito.any())).thenReturn(user);

		LoginRequest request = new LoginRequest("suny@gmail.com", "testabc123#");

		User userLoggedIn = userService.login(request);

		Assertions.assertEquals(user, userLoggedIn);
	}

	@Test
	public void whenUserLoginAndInvalidDetails_thenUnauthorizedException() throws Exception {
		Mockito.when(userRepository.loginUser(Mockito.any(), Mockito.any()))
				.thenThrow(new UnauthorizedException("Invalid Credentials, Try Again"));
		LoginRequest request = new LoginRequest("suny@gmail.com", "testabc123#");
		Assertions.assertThrows(UnauthorizedException.class, () -> userService.login(request));
	}

	@Test
	public void whenChangePasswordAndValidDetails_thenCorrectResponse() throws Exception {
		Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
		Mockito.doNothing().when(userRepository).updatePassword(Mockito.any(), Mockito.any());

		ChangePasswordRequest request = new ChangePasswordRequest("Testabc1234$", "Testabc123#", "suny@gmail.com");

		userService.changePassword(request);

		Mockito.verify(userRepository, Mockito.times(1)).updatePassword(request.getMailId(), request.getNewPassword());
	}

	@Test
	public void whenChangePasswordAndWrongPassword_thenThrowException() throws Exception {
		Mockito.doThrow((new UnprocessableEntityException("Wrong old password."))).when(userRepository)
				.updatePassword(Mockito.any(), Mockito.any());
		ChangePasswordRequest request = new ChangePasswordRequest("Testabc1234$", "Testabc123#", "suny@gmail.com");

		Assertions.assertThrows(UnprocessableEntityException.class, () -> userService.changePassword(request));
	}

	@Test
	public void whenChangePasswordAndWrongEmail_thenThrowException() throws Exception {
		Mockito.doThrow((new UnprocessableEntityException("Invalid email."))).when(userRepository)
				.updatePassword(Mockito.any(), Mockito.any());
		ChangePasswordRequest request = new ChangePasswordRequest("Testabc1234$", "Testabc123#", "suny@gmail.com");

		Assertions.assertThrows(UnprocessableEntityException.class, () -> userService.changePassword(request));

	}

	@Test
	public void whenUpdateUserAndValidUser_thenCorrectResponse() throws Exception {
		Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
		Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

		User updatedUser = userService.updateUser(user);
		Assertions.assertEquals(user, updatedUser);
	}

	@Test
	public void whenUpdateUserAndInvalidEmail_thenThrowException() throws Exception {
		Mockito.when(userRepository.findById(Mockito.any()))
				.thenThrow(new UnprocessableEntityException("Invalid email."));

		Assertions.assertThrows(UnprocessableEntityException.class, () -> userService.updateUser(user));
	}

	@Test
	public void whenRegisterUserAndValidDetails_thenCorrectResponse() throws Exception {
		Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
		User registeredUser = userService.registerUser(user);

		Assertions.assertEquals(user, registeredUser);
	}

	@Test
	public void whenRegisterUserAndInvalidEmail_thenThrowException() throws Exception {
		Mockito.when(userRepository.findById(Mockito.any()))
				.thenThrow(new UnprocessableEntityException("Email is already registered."));

		Assertions.assertThrows(UnprocessableEntityException.class, () -> userService.registerUser(user));
	}
}
