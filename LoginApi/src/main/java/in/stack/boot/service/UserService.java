package in.stack.boot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.stack.boot.exception.UserAlreadyExistsException;
import in.stack.boot.exception.UserNotFoundException;
import in.stack.boot.model.User;
import in.stack.boot.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	 private UserRepository userRepo;
	 
	public UserService(UserRepository userRepo) {
		super();
		this.userRepo=userRepo;
		
	}

	public boolean saveUser(User user) throws UserAlreadyExistsException {
		Optional<User> existUser=userRepo.findById(user.getUserId());
		if(existUser.isPresent()) {
			throw new UserAlreadyExistsException("User ALready exist");
		}
		userRepo.save(user);
		return true;
	}
	
	public User findByUserIdAndPassword(String userId, String password) throws UserNotFoundException {
		// TODO Auto-generated method stub
		
		User user = userRepo.findByUserIdAndPassword(userId, password);
		if(user == null) {
			throw new UserNotFoundException("User not Found Please check userid and password");
		}
		return user;
	}
	
}
