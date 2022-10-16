package com.web.app.user;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin
@RequestMapping(path="/api/user")
public class UserController {
	private final UserService userService;
	
	@Autowired
	UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> getUsers() {
		List<UserDTO> users = userService.getAllUsers();
		if (users.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(users, HttpStatus.OK);
		}
	}
	
	@GetMapping(path="/get/{userId}")
	public ResponseEntity<UserDTO> getUser(@PathVariable("userId") Long userId) {
		return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
	}
	
	@GetMapping(path="/getbyemail/{userEmail}")
	public ResponseEntity<List<UserDTO>> getUserByEmail(@PathVariable("userEmail") String userEmail) {
		List<UserDTO> users = userService.getUserByEmailContaining(userEmail);
		if (users.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(users, HttpStatus.OK);
		}
	}
	
	@PostMapping(path="/add")
	public ResponseEntity<UserDTO> addUser(@RequestBody User user) {
		UserDTO _user = userService.addUser(user);
		return new ResponseEntity<>(_user, HttpStatus.CREATED);
	}
	
	@DeleteMapping(path="/delete/{userId}")
	public ResponseEntity<User> deleteUser(@PathVariable("userId") Long userId) {
		userService.deleteUser(userId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
