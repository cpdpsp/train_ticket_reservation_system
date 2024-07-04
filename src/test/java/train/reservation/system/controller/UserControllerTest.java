package train.reservation.system.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import train.reservation.system.entity.ChangePasswordRequest;
import train.reservation.system.entity.LoginRequest;
import train.reservation.system.entity.User;
import train.reservation.system.exception.GlobalExceptionHandler;
import train.reservation.system.exception.UnauthorizedException;
import train.reservation.system.exception.UnprocessableEntityException;
import train.reservation.system.service.UserService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerTest {

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	@Autowired
	private ObjectMapper objectMapper;

	private MockMvc mockMvc;

	private User user;

	@BeforeEach
	private void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(new GlobalExceptionHandler())
				.build();
		user = new User("Sunny", "Patel", "Testabc123#", "suny@gmail.com");

	}

	@Test
	public void whenPostUserAndValidUser_thenCorrectResponse() throws Exception {
		Mockito.when(userService.registerUser(Mockito.any())).thenReturn(user);

		mockMvc.perform(MockMvcRequestBuilders.post("/registerUser").content(objectMapper.writeValueAsString(user))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$", aMapWithSize(4))).andExpect(jsonPath("$.firstName", is(user.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(user.getLastName())))
				.andExpect(jsonPath("$.password", is(user.getPassword())))
				.andExpect(jsonPath("$.mailId", is(user.getMailId())));
	}

	@Test
	public void whenPostUserAndInvalidUser_thenThrowException() throws Exception {
		Mockito.when(userService.registerUser(Mockito.any()))
				.thenThrow(new UnprocessableEntityException("Email is already registered."));

		mockMvc.perform(MockMvcRequestBuilders.post("/registerUser").content(objectMapper.writeValueAsString(user))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	public void whenUpdateUserAndValidUser_thenCorrectResponse() throws Exception {
		Mockito.when(userService.updateUser(Mockito.any())).thenReturn(user);

		mockMvc.perform(MockMvcRequestBuilders.put("/updateUserProfile").content(objectMapper.writeValueAsString(user))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$", aMapWithSize(4))).andExpect(jsonPath("$.firstName", is(user.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(user.getLastName())))
				.andExpect(jsonPath("$.password", is(user.getPassword())))
				.andExpect(jsonPath("$.mailId", is(user.getMailId())));
	}

	@Test
	public void whenLoginAndValidDetails_thenCorrectResponse() throws Exception {
		Mockito.when(userService.login(Mockito.any())).thenReturn(user);

		mockMvc.perform(MockMvcRequestBuilders.get("/login").content(objectMapper.writeValueAsString(user))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$", aMapWithSize(4))).andExpect(jsonPath("$.firstName", is(user.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(user.getLastName())))
				.andExpect(jsonPath("$.password", is(user.getPassword())))
				.andExpect(jsonPath("$.mailId", is(user.getMailId())));

	}

	@Test
	public void whenLoginAndInvalidDetails_thenThrowException() throws Exception {
		Mockito.when(userService.login(Mockito.any()))
				.thenThrow(new UnauthorizedException("Invalid Credentials, Try Again"));

		LoginRequest request = new LoginRequest("suny@gmail.com", "testabc123#");

		mockMvc.perform(MockMvcRequestBuilders.get("/login").content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is4xxClientError());

	}

	@Test
	public void whenChangePasswordAndValidDetails_thenCorrectResponse() throws Exception {
		Mockito.doNothing().when(userService).changePassword(Mockito.any());

		ChangePasswordRequest request = new ChangePasswordRequest("Testabc1234$", "Testabc123#", "suny@gmail.com");

		mockMvc.perform(MockMvcRequestBuilders.patch("/changePassword")
				.content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
