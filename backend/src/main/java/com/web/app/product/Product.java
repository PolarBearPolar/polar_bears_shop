package com.web.app.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="product_table")
public class Product {
	@Id
	@GeneratedValue(
		strategy = GenerationType.AUTO
	)
	private Long id;
	@Column(unique=true, nullable=false)
	private String productCode;
	private String name;
	private Double price;
	
	Product (){}
			
	Product (String productCode, String name) {
		this.productCode = productCode;
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getproductCode() {
		return productCode;
	}
	
	@Column(name="product_code")
	public void setproductCode(String productCode) {
		this.productCode = productCode;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
