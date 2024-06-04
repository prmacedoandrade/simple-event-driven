package com.estore.orders.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

@Value
public class RejectOrderCommnad {
	
	@TargetAggregateIdentifier
	private final String orderId;
	private final String message;

}
