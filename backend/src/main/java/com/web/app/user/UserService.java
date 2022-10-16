package com.web.app.user;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.app.shopping_cart.ShoppingCartService;


@Service
public class UserService {
	private final UserRepository userRepository;
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public UserDTO convertUserToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setEmail(user.getEmail());
		user.getOrders().forEach(x -> userDTO.setOrders(shoppingCartService.convertOrderToUserDTO(x)));
		return userDTO;
	}
	
	public List<UserDTO> getAllUsers() {
		return userRepository.findAll()
				.stream()
				.map(this::convertUserToDTO)
				.collect(Collectors.toList());
	}
	
	public UserDTO getUserById(Long id) {
		return convertUserToDTO(userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("There is no user with id - " + id + ".")));
	}
	
	public List<UserDTO> getUserByEmailContaining(String email) {
		return userRepository.findByEmailContaining(email)
				.stream()
				.map(this::convertUserToDTO)
				.collect(Collectors.toList());
	}
	
	public UserDTO addUser(User user) {
		return convertUserToDTO(userRepository.save(user));
	}
	
	public void deleteUser(Long id) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("There is no user with id - " + id + "."));
		userRepository.deleteById(user.getId());
	}
}
