package com.estore.core.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReservationCanceledEvent {

	private String productId;
	private int quantity;
	private String orderId;
	private String userId;
	private String message;
	
}
