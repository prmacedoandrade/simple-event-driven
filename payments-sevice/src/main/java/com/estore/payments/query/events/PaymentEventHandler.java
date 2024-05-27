package com.estore.payments.query.events;

import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.estore.core.event.PaymentProcessedEvent;
import com.estore.payments.core.model.PaymentBean;
import com.estore.payments.core.repository.PaymentRepositoy;

@Component
public class PaymentEventHandler {

	private final Logger LOGGER = LoggerFactory.getLogger(PaymentEventHandler.class);
	
	private final PaymentRepositoy paymentRepositoy;

	public PaymentEventHandler(PaymentRepositoy paymentRepositoy) {
		this.paymentRepositoy = paymentRepositoy;
	}
	
	@EventHandler
	public void on(PaymentProcessedEvent event) {
		
		LOGGER.info("PaymentProcessedEvent is called for orderId: {}", event.getOrderId());
		
        PaymentBean paymentBean = new PaymentBean();
        BeanUtils.copyProperties(event, paymentBean);
		
        paymentRepositoy.save(paymentBean);
		
	}
	
}
