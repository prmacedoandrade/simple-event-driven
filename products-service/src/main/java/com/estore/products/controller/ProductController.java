package com.estore.products.controller;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	private Environment env;
	private String port;
	
	public ProductController(Environment env) {
		this.env = env;
		this.port = env.getProperty("local.server.port");
	}

	@PostMapping
	public String createProduct() {
		return "HTTP POST HANDELED " + port;
	}
	
	@GetMapping
	public String getProduct() {
		return "HTTP GET HANDELED " + port;
	}
	
	@PutMapping
	public String updateProduct() {
		return "HTTP PUT HANDELED " + port;
	}
	
	@DeleteMapping
	public String deleteProduct() {
		return "HTTP DELETE HANDELED " + port;
	}
	
}
