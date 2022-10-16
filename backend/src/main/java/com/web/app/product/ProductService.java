package com.web.app.product;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductService {
	private final ProductRepository productRepository;
	
	@Autowired
	ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	public Product getProductById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("There is no product with id - " + id + "."));
	}
	
	public List<Product> getProductByProductCodeContaining(String productCode) {
		return productRepository.findByProductCodeContaining(productCode);
	}
	
	public Product addProduct(Product product) {
		return productRepository.save(product);
	}
	
	public void deleteProduct(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("There is no product with id - " + id + "."));
		productRepository.deleteById(product.getId());
	}
}
