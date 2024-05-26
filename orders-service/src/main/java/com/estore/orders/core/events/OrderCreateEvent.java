package com.estore.orders.core.events;

import java.math.BigDecimal;

import com.estore.orders.core.model.OrderStatus;

import lombok.Data;

@Data
public class OrderCreateEvent {
	
	private String orderId;
	private String productId;
	private String userId;
	private int quantity;
	private String addressId;
	private OrderStatus orderStatus;

}
