package com.web.app.user;

import java.util.ArrayList;
import java.util.List;
import com.web.app.shopping_cart.ShoppingCartUserDTO;

public class UserDTO {

	private Long id;
	private String name;
	private String email;
	private List<ShoppingCartUserDTO> orders = new ArrayList<>();
	
	UserDTO () {}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<ShoppingCartUserDTO> getOrders() {
		return orders;
	}
	
	public void setOrders(ShoppingCartUserDTO shoppingCartUserDTO) {
		orders.add(shoppingCartUserDTO);
	}
}
