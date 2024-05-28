package com.estore.orders.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApprovedOrderCommand {
	
	@TargetAggregateIdentifier
	private final String orderId;
	
}
