package com.estore.payments.command;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.estore.core.command.ProcessPaymentCommand;
import com.estore.core.event.PaymentProcessedEvent;

@Aggregate
public class PaymentAggregate {

	@AggregateIdentifier
	private String paymentId;

	private String orderId;
	
	public PaymentAggregate() {
	}

	@CommandHandler
    public PaymentAggregate(ProcessPaymentCommand processPaymentCommand) {
		
		if(processPaymentCommand.getPaymentDetails() == null) {
    		throw new IllegalArgumentException("Missing payment details");
    	}
    	
    	if(processPaymentCommand.getOrderId() == null) {
    		throw new IllegalArgumentException("Missing orderId");
    	}
    	
    	if(processPaymentCommand.getPaymentId() == null) {
    		throw new IllegalArgumentException("Missing paymentId");
    	}
    	
    	PaymentProcessedEvent event = new PaymentProcessedEvent(processPaymentCommand.getOrderId(),
    			processPaymentCommand.getPaymentId());
    	
    	AggregateLifecycle.apply(event);
    	
	}
	
	
	@EventSourcingHandler
	private void on(PaymentProcessedEvent paymentProcessedEvent) {
		this.paymentId = paymentProcessedEvent.getPaymentId();
		this.orderId = paymentProcessedEvent.getOrderId();
	}
		
}
