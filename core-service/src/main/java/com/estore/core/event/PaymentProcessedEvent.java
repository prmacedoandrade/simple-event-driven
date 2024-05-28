package com.estore.core.event;

import lombok.Data;


@Data
public class PaymentProcessedEvent {

	private final String orderId;
	private final String paymentId;
	
	public PaymentProcessedEvent(String orderId, String paymentId) {
		super();
		this.orderId = orderId;
		this.paymentId = paymentId;
	}
	
}
