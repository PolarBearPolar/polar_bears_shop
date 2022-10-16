package com.web.app.shopping_cart;
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
@RequestMapping(path="/api/shoppingCart")
public class ShoppingCartController {
	private final ShoppingCartService shoppingCartService;
	
	@Autowired
	ShoppingCartController(ShoppingCartService shoppingCartService) {
		this.shoppingCartService = shoppingCartService;
	}
	
	@GetMapping
	public ResponseEntity<List<ShoppingCartDTO>> getOrders() {
		List<ShoppingCartDTO> orders = shoppingCartService.getAllOrders();
		if (orders.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(orders, HttpStatus.OK);
		}
	}
	
	@GetMapping(path="/get/{shoppingCartId}")
	public ResponseEntity<ShoppingCartDTO> getOrder(@PathVariable("shoppingCartId") Long shoppingCartId) {
		return new ResponseEntity<>(shoppingCartService.getOrderById(shoppingCartId), HttpStatus.OK);
	}
	
	@PostMapping(path="/add")
	public ResponseEntity<ShoppingCartDTO> addOrder(@RequestBody ShoppingCart shoppingCart) {
		ShoppingCartDTO _shoppingCart = shoppingCartService.addOrder(shoppingCart);
		return new ResponseEntity<>(_shoppingCart, HttpStatus.CREATED);
	}
	
	@DeleteMapping(path="/delete/{shoppingCartId}")
	public ResponseEntity<ShoppingCart> deleteOrder(@PathVariable("shoppingCartId") Long shoppingCartId) {
		shoppingCartService.deleteOrder(shoppingCartId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
