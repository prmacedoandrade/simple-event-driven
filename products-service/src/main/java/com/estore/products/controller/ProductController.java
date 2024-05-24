package com.estore.products.controller;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.products.command.CreateProductCommand;
import com.estore.products.model.CreateProductRestModel;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	private final Environment env;
	private final CommandGateway commandGateway;
	private String port;
	
	public ProductController(Environment env, CommandGateway commandGateway) {
		super();
		this.env = env;
		this.commandGateway = commandGateway;
	}

	@PostMapping
	public String createProduct(@RequestBody CreateProductRestModel createProductRestModel) {
		
		CreateProductCommand productCommand = CreateProductCommand.builder()
			.price(createProductRestModel.getPrice())
			.quantity(createProductRestModel.getQuantity())
			.title(createProductRestModel.getTitle())
			.productId(UUID.randomUUID().toString()).build();
		
		String returnValue;
		try {
			returnValue = commandGateway.sendAndWait(productCommand);
		} catch (Exception e) {
			returnValue = e.getLocalizedMessage();
		}

		return returnValue;
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
