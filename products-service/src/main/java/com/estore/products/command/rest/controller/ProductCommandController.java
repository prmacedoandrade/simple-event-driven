package com.estore.products.command.rest.controller;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.products.command.CreateProductCommand;
import com.estore.products.command.rest.CreateProductRestModel;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductCommandController {
	
	private final CommandGateway commandGateway;
	
	public ProductCommandController(CommandGateway commandGateway) {
		super();
		this.commandGateway = commandGateway;
	}

	@PostMapping
	public String createProduct(@RequestBody @Valid CreateProductRestModel createProductRestModel) {
		
		CreateProductCommand productCommand = CreateProductCommand.builder()
			.price(createProductRestModel.getPrice())
			.quantity(createProductRestModel.getQuantity())
			.title(createProductRestModel.getTitle())
			.productId(UUID.randomUUID().toString()).build();
		
		return commandGateway.sendAndWait(productCommand);
	}

}
