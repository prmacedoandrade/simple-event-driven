package com.estore.core.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data 
@Builder
@AllArgsConstructor
public class ReserveProductCommand {
	
	@TargetAggregateIdentifier
	private final String productId;
	
	private final int quantity;
	private final String orderId;
	private final String userId;
}
