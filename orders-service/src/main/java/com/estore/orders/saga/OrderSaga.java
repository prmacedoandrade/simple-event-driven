package com.estore.orders.saga;


import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.estore.core.command.ReserveProductCommand;
import com.estore.core.event.ProductReserveEvent;
import com.estore.orders.core.events.OrderCreateEvent;

@Saga
public class OrderSaga {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderSaga.class);
	
	// Saga is serializable so we put command gateway as transient  
	@Autowired
	private transient CommandGateway commandGateway;
	
	@StartSaga
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(OrderCreateEvent createEvent) {
		
		ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
			.orderId(createEvent.getOrderId())
			.productId(createEvent.getProductId())
			.quantity(createEvent.getQuantity())
			.userId(createEvent.getUserId()).build();
		
		LOGGER.info("OrderCreateEvent for order id {} and product id {}", createEvent.getOrderId(), createEvent.getProductId());
		
		commandGateway.send(reserveProductCommand, new CommandCallback<ReserveProductCommand, Object>() {

			@Override
			public void onResult(CommandMessage<? extends ReserveProductCommand> commandMessage,
					CommandResultMessage<? extends Object> commandResultMessage) {
				
			    if (commandResultMessage.isExceptional()) {
                    // START COMPENSATION TRANSACTION
                    LOGGER.error("Error handling ReserveProductCommand: {}", commandResultMessage.exceptionResult().getMessage());
                } else {
                    LOGGER.info("ReserveProductCommand handled successfully for order id {}", createEvent.getOrderId());
                }
					
			}
			
		});
		
	}
	
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(ProductReserveEvent productReserveEvent) {
		LOGGER.info("ProductReserveEvent in OrderSaga is called for order id {} and product id {}", productReserveEvent.getOrderId(), productReserveEvent.getProductId());
		//Process user payment				
	}
	
}
