package com.estore.products.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductsController {

	@PostMapping
	public String createProduct() {
		return "HTTP POST HANDELED";
	}
	
	@GetMapping
	public String getProduct() {
		return "HTTP GET HANDELED";
	}
	
	@PutMapping
	public String updateProduct() {
		return "HTTP PUT HANDELED";
	}
	
	@DeleteMapping
	public String deleteProduct() {
		return "HTTP DELETE HANDELED";
	}
	
}
