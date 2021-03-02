package in.stack.boot.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.stack.boot.exception.UserNotFoundException;
import in.stack.boot.model.User;
import in.stack.boot.service.SecurityTokenGenerator;
import in.stack.boot.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private SecurityTokenGenerator tokenGen;
	
	
	@PostMapping("/register")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> registerUser(@RequestBody User user){
		try {
			userService.saveUser(user);
			return new ResponseEntity<User>(user,HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<String>("message: "+e.getMessage(),HttpStatus.CONFLICT);
		}
	}
	
	@PostMapping("/login")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> login(@RequestBody User loginInfo){
		try {
			if(loginInfo.getUserId()==null||loginInfo.getPassword()==null) {
				throw new UserNotFoundException("UserId or password cannot be empty");
			}
			User user = userService.findByUserIdAndPassword(loginInfo.getUserId(),loginInfo.getPassword());
			Map<String,String> map = tokenGen.generateToken(user);
			return new ResponseEntity<Map<String,String>>(map,HttpStatus.OK);
		}catch(UserNotFoundException e) {
			return new ResponseEntity<String>("message:"+e.getMessage(),HttpStatus.UNAUTHORIZED);
					
			}
	}
	
}
