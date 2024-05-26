package com.estore.orders.command.rest;

import java.io.Serializable;

import lombok.Data;

@Data
public class CreateOrderRestModel implements Serializable{
	
	private static final long serialVersionUID = 9188889953717123094L;
	
	private final String productId;
	private final int quantity;
	private final String addressId;
	
}
