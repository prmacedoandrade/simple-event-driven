package com.estore.orders.core.events;

import com.estore.orders.core.model.OrderStatus;

import lombok.Value;

@Value
public class OrderRejectedEvent {

	private final String orderId;
	private final String message;
	private final  OrderStatus orderStatus = OrderStatus.REJECTED;
	
}
