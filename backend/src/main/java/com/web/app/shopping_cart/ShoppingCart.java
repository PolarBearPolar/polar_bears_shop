package com.web.app.shopping_cart;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.app.product.Product;
import com.web.app.user.User;

@Entity
@Table(name="shopping_cart_table")
public class ShoppingCart {
	@Id
	@GeneratedValue(
		strategy = GenerationType.AUTO
	)
	private Long id;
	@Column(nullable=false)
	private Long userId;
	@Column(nullable=false)
	private Long productId;
	@Column(nullable=false)
	private Long quantity;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_fk", nullable=false)
	@JsonIgnore
	private User user;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="product_fk", nullable=false)
	@JsonIgnore
	private Product product;
	
	ShoppingCart (){}
			
	ShoppingCart (Long userId, Long productId, Long quantity) {
		this.userId = userId;
		this.productId = productId;
		this.quantity = quantity;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
}
