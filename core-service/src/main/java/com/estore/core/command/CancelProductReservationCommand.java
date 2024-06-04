package com.estore.core.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

/**
 * Cancel product reservation and increase the number of items in stock
 * 
 */
@Data
@Builder
public class CancelProductReservationCommand {
	
	@TargetAggregateIdentifier
	private final String productId;
	private final int quantity;
	private final String orderId;
	private final String userId;
	private final String message;
	
}
