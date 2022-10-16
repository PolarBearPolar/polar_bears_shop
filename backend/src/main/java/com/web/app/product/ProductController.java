package com.web.app.product;
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
@RequestMapping(path="/api/product")
public class ProductController {
	private final ProductService productService;
	
	@Autowired
	ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping
	public ResponseEntity<List<Product>> getProducts() {
		List<Product> products = productService.getAllProducts();
		if (products.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(products, HttpStatus.OK);
		}
	}
	
	@GetMapping(path="/get/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable("productId") Long productId) {
		return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
	}
	
	@GetMapping(path="/getbycode/{productCode}")
	public ResponseEntity<List<Product>> getProduct(@PathVariable("productCode") String productCode) {
		List<Product> products = productService.getProductByProductCodeContaining(productCode);
		if (products.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(products, HttpStatus.OK);
		}
	}
	
	@PostMapping(path="/add")
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		Product _product = productService.addProduct(product);
		return new ResponseEntity<>(_product, HttpStatus.CREATED);
	}
	
	@DeleteMapping(path="/delete/{productId}")
	public ResponseEntity<Product> deleteProduct(@PathVariable("productId") Long productId) {
		productService.deleteProduct(productId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
