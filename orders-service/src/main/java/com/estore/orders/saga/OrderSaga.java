package com.estore.orders.saga;


import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.estore.core.command.CancelProductReservationCommand;
import com.estore.core.command.CancelProductReservationCommand.CancelProductReservationCommandBuilder;
import com.estore.core.command.ProcessPaymentCommand;
import com.estore.core.command.ReserveProductCommand;
import com.estore.core.event.PaymentProcessedEvent;
import com.estore.core.event.ProductReservationCanceledEvent;
import com.estore.core.event.ProductReserveEvent;
import com.estore.core.model.User;
import com.estore.core.query.FechtUserPaymentDetailsQuery;
import com.estore.orders.command.ApprovedOrderCommand;
import com.estore.orders.command.RejectOrderCommnad;
import com.estore.orders.core.events.OrderApprovedEvent;
import com.estore.orders.core.events.OrderCreateEvent;
import com.estore.orders.core.events.OrderRejectedEvent;

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
			cancelProductReservation(productReserveEvent, ex.getMessage());
	        return;
		}
		
		if(user == null) {
			// Start a compensation transaction
			cancelProductReservation(productReserveEvent, "Could not fetch user payment details");
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
			result = commandGateway.sendAndWait(processPaymentCommand,20,TimeUnit.SECONDS);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			// Start a compensation transaction
			cancelProductReservation(productReserveEvent, ex.getMessage());
			return;
		}
		
		if(result == null) {
			LOGGER.error("The ProcessPaymentCommand result is NULL. Initiating a compensating transaction");
			// Start a compensation transaction
			cancelProductReservation(productReserveEvent, "Could not provide user payment");
		}
		
	}
	
	private void cancelProductReservation(ProductReserveEvent productReserveEvent, String message) {
		
		CancelProductReservationCommand cancelProductReservationCommand = CancelProductReservationCommand.builder()
			.productId(productReserveEvent.getProductId())
			.orderId(productReserveEvent.getOrderId())
			.quantity(productReserveEvent.getQuantity())
			.userId(productReserveEvent.getUserId())
			.message(message).build();
		
		commandGateway.send(cancelProductReservationCommand);
		
	}
	
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(PaymentProcessedEvent paymentProcessedEvent) {
		ApprovedOrderCommand approvedOrderCommand = new ApprovedOrderCommand(paymentProcessedEvent.getOrderId());
		commandGateway.send(approvedOrderCommand);
	}

	@EndSaga
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(OrderApprovedEvent orderApprovedEvent) {
		LOGGER.info("Order is approved. Order Saga is complete for order id: {}", orderApprovedEvent.getOrderId());
		//SagaLifecycle.end(); with this you can end programmatically
	}
	
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(ProductReservationCanceledEvent productReservationCanceledEvent) {

		RejectOrderCommnad rejectOrderCommnad = new RejectOrderCommnad(productReservationCanceledEvent.getOrderId(),
				productReservationCanceledEvent.getMessage());

		commandGateway.send(rejectOrderCommnad);

	}
	
	@EndSaga
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(OrderRejectedEvent orderRejectedEvent) {
		LOGGER.info("Order is rejected. Order Saga is complete for order id: {}", orderRejectedEvent.getOrderId());
	}
	
}
