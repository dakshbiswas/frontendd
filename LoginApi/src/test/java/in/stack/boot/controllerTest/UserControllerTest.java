package in.stack.boot.controllerTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import in.stack.boot.controller.UserController;
import in.stack.boot.exception.UserAlreadyExistsException;
import in.stack.boot.exception.UserNotFoundException;
import in.stack.boot.model.User;
import in.stack.boot.service.UserService;

@WebMvcTest(UserController.class)
@ComponentScan(basePackages = {"in.stack.boot.service"})
public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;
	private User user;
	@MockBean
	private UserService userService;
	@InjectMocks
	private UserController userController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		  user = new User("biswasdaksh007@gmail.com","Daksh","Biswas","messi","imageUrl");
	}
	
	private static ObjectMapper mapper=new ObjectMapper();
	
	@Test
	void testLoginUser() throws Exception {
				when(userService.saveUser(user)).thenReturn(true);
		when(userService.findByUserIdAndPassword(user.getUserId(), user.getPassword())).thenReturn(user);
		mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(jsonToString(user))).andExpect(status().isOk());

		verify(userService, times(1)).findByUserIdAndPassword(user.getUserId(), user.getPassword());
		Mockito.verifyNoMoreInteractions(userService);
	
	}
	@Test
	public void testRegisterUser() throws Exception {
		when(userService.saveUser(user)).thenReturn(true);

		mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(jsonToString(user))).andExpect(status().isCreated());

		verify(userService, times(1)).saveUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userService);

	}
	@Test
	public void testRegisterUserFailure() throws Exception {
		when(userService.saveUser(Mockito.any(User.class))).thenThrow(new UserAlreadyExistsException("User ALready Exist"));

		mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(jsonToString(user))).andExpect(status().isConflict());

		verify(userService, times(1)).saveUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userService);

	}
	@Test
	public void testLoginUserFailure() throws Exception {
		User user1 = new User("biswasdaksh007@gmail.com","Daksh","Biswas","messi","imageUrl");
		//when(userService.saveUser(Mockito.any(User.class))).thenThrow(new UserNotFoundException("User not Found Exception"));
		when(userService.findByUserIdAndPassword(Mockito.any(String.class), Mockito.any(String.class))).thenThrow(new UserNotFoundException("User not Found Exception"));
		mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(jsonToString(user1))).andExpect(status().isUnauthorized());

		verify(userService, times(1)).findByUserIdAndPassword(user1.getUserId(), user1.getPassword());
		Mockito.verifyNoMoreInteractions(userService);

	}
	private static String jsonToString(final Object obj) throws JsonProcessingException {
		String result;
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			result = jsonContent;
		} catch (JsonProcessingException e) {
			result = "json processing error";
		}
		return result;
	}
}
