package com.estore.orders.command.rest.controller;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.orders.command.CreateOrderCommand;
import com.estore.orders.command.rest.CreateOrderRestModel;

@RestController
@RequestMapping("/orders")
public class OrderCommandController {

	private CommandGateway commandGateway;

	public OrderCommandController(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
	}
	
	@PostMapping
	public String createOrder(@RequestBody CreateOrderRestModel createOrderRestModel) {
		
		CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
			.orderId(UUID.randomUUID().toString())
			.productId(createOrderRestModel.getProductId())
			.quantity(createOrderRestModel.getQuantity())
			.userId("27b95829-4f3f-4ddf-8983-151ba010e35b")
			.addressId(createOrderRestModel.getAddressId()).build();
		
		return commandGateway.sendAndWait(createOrderCommand);
	}
	
}
