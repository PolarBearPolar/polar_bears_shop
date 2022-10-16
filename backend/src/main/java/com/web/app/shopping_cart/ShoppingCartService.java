package com.web.app.shopping_cart;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.app.product.Product;
import com.web.app.product.ProductRepository;
import com.web.app.user.User;
import com.web.app.user.UserRepository;


@Service
public class ShoppingCartService {
	private final ShoppingCartRepository shoppingCartRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	ShoppingCartService(ShoppingCartRepository shoppingCartRepository) {
		this.shoppingCartRepository = shoppingCartRepository;
	}
	
	public ShoppingCartDTO convertProductToDTO(ShoppingCart shoppingCart) {
		ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
		shoppingCartDTO.setId(shoppingCart.getId());
		shoppingCartDTO.setUserId(shoppingCart.getUserId());
		shoppingCartDTO.setProductId(shoppingCart.getProductId());
		shoppingCartDTO.setPrice(shoppingCart.getProduct().getPrice());
		return shoppingCartDTO;
	}
	
	public ShoppingCartUserDTO convertOrderToUserDTO(ShoppingCart shoppingCart) {
		ShoppingCartUserDTO shoppingCartUserDTO = new ShoppingCartUserDTO();
		shoppingCartUserDTO.setId(shoppingCart.getId());
		shoppingCartUserDTO.setProductCode(shoppingCart.getProduct().getproductCode());
		shoppingCartUserDTO.setProductName(shoppingCart.getProduct().getName());
		shoppingCartUserDTO.setQuantity(shoppingCart.getQuantity());
		shoppingCartUserDTO.setPrice(shoppingCart.getProduct().getPrice());
		return shoppingCartUserDTO;
	}
	
	public List<ShoppingCartDTO> getAllOrders() {
		List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAll();
		return shoppingCarts
				.stream()
				.map(this::convertProductToDTO)
				.collect(Collectors.toList());
	}
	
	public ShoppingCartDTO getOrderById(Long id) {		
		ShoppingCart shoppingCart = shoppingCartRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("There is no order with id - " + id + "."));
		return convertProductToDTO(shoppingCart);
	}
	
	public ShoppingCartDTO addOrder(ShoppingCart shoppingCart) {
		User user = userRepository.findById(shoppingCart.getUserId())
				.orElseThrow(() -> new IllegalArgumentException("You are trying to make an order for a non-existent user."));
		shoppingCart.setUser(user);
		Product product = productRepository.findById(shoppingCart.getProductId())
				.orElseThrow(() -> new IllegalArgumentException("You are trying to order a non-existent product."));
		shoppingCart.setProduct(product);
		return convertProductToDTO(shoppingCartRepository.save(shoppingCart));
	}
	
	public void deleteOrder(Long id) {
		ShoppingCart shoppingCart = shoppingCartRepository.findById(id)
		.orElseThrow(() -> new IllegalArgumentException("There is no order with id - " + id + "."));
		shoppingCartRepository.deleteById(shoppingCart.getId());
	}
}
