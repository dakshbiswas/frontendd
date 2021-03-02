package in.stack.boot.serviceTest;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import in.stack.boot.exception.UserAlreadyExistsException;
import in.stack.boot.exception.UserNotFoundException;
import in.stack.boot.model.User;
import in.stack.boot.repository.UserRepository;
import in.stack.boot.service.UserService;

public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	private User user;

	@InjectMocks
	private UserService userServiceImpl;

	Optional<User> options;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		  user = new User("biswasdaksh007@gmail.com","Daksh","Biswas","messi","imageUrl");
		options = Optional.of(user);
	}

	@Test
	public void testSaveUserSuccess() throws UserNotFoundException, UserAlreadyExistsException {
		when(userRepository.save(user)).thenReturn(user);
		boolean flag = userServiceImpl.saveUser(user);
		verify(userRepository, times(1)).save(user);
	}

	@Test()
	public void testSaveUserfailure() throws UserNotFoundException, UserAlreadyExistsException {
		when(userRepository.findById(user.getUserId())).thenReturn(options);
		when(userRepository.save(user)).thenReturn(user);
	}

	@Test
	public void testValidateSuccess() throws UserNotFoundException {
		when(userRepository.findByUserIdAndPassword(user.getUserId(), user.getPassword())).thenReturn(user);
		User userResult = userServiceImpl.findByUserIdAndPassword(user.getUserId(), user.getPassword());
		assertNotNull(userResult);
		assertEquals("biswasdaksh007@gmail.com", userResult.getUserId());
		verify(userRepository, times(1)).findByUserIdAndPassword(user.getUserId(), user.getPassword());

	}

//	@Test()
//	public void testValidateFailure() throws UserNotFoundException {
//		when(userRepository.findByUserIdAndPassword("biswasdaksh007@gmail.com","messi")).thenReturn(null);
//		userServiceImpl.findByUserIdAndPassword(user.getUserId(), user.getPassword());
//	}


}
