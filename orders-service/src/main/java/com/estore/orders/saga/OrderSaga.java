package com.estore.orders.saga;


import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.estore.core.command.ProcessPaymentCommand;
import com.estore.core.command.ReserveProductCommand;
import com.estore.core.event.ProductReserveEvent;
import com.estore.core.model.User;
import com.estore.core.query.FechtUserPaymentDetailsQuery;
import com.estore.orders.core.events.OrderCreateEvent;

@Saga
public class OrderSaga {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderSaga.class);
	
	// Saga is serializable so we put command gateway as transient  
	@Autowired
	private transient CommandGateway commandGateway;
	
	@Autowired
	private transient QueryGateway queryGateway;
	
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
                    // Start a compensation transaction
                    LOGGER.error("Error handling ReserveProductCommand: {}", commandResultMessage.exceptionResult().getMessage());
                } else {
                    LOGGER.info("ReserveProductCommand handled successfully for order id {}", createEvent.getOrderId());
                }
					
			}
			
		});
		
	}
	
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(ProductReserveEvent productReserveEvent) {
		LOGGER.info("ProductReserveEvent in OrderSaga is called for order id {} and product id {}", productReserveEvent.getOrderId(), 
				productReserveEvent.getProductId());
		
		//Process user payment 
		FechtUserPaymentDetailsQuery fechtUserPaymentDetailsQuery = 
				new FechtUserPaymentDetailsQuery(productReserveEvent.getUserId());
		
		User user = null;
		
		// If fails start a compensation transaction
		try {
			user = queryGateway.query(fechtUserPaymentDetailsQuery, ResponseTypes.instanceOf(User.class)).join();
		} catch (Exception ex) {
			LOGGER.info(ex.getMessage());
	        // Start a compensation transaction
			return;
		}
		
		if(user == null) {
			// Start a compensation transaction
			return;
		}
		
		LOGGER.info("Succecssfuly fetched user payment detais for user {}", user.getFirstName());
						
		
		ProcessPaymentCommand processPaymentCommand = ProcessPaymentCommand.builder()
			.orderId(productReserveEvent.getOrderId())
			.paymentDetails(user.getPaymentDetails())
			.paymentId(UUID.randomUUID().toString())
			.build();
		
		String result = null;
		
		try {
			result = commandGateway.sendAndWait(processPaymentCommand,10,TimeUnit.SECONDS);
		} catch (Exception ex) {
			// Start a compensation transaction
			LOGGER.error(ex.getMessage());

		}
		
		if(result == null) {
			// Start a compensation transaction
			LOGGER.error("The ProcessPaymentCommand result is NULL. Initiating a compensating transaction");
		}
		
		
	}
	
}
