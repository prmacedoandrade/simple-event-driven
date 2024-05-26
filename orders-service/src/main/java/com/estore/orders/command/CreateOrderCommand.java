package com.estore.orders.command;

import java.io.Serializable;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.estore.orders.core.model.OrderStatus;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateOrderCommand implements Serializable{
	
	private static final long serialVersionUID = 9188889953717123094L;
	
	@TargetAggregateIdentifier
	private final String orderId;
	private final String userId;
	private final String productId;
	private final int quantity;
	private final String addressId;
	private final OrderStatus orderStatus;
	
}
