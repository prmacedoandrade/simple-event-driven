package com.estore.orders.saga;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;

import com.estore.core.command.ReserveProductCommand;
import com.estore.orders.core.events.OrderCreateEvent;

@Saga
public class OrderSaga {

	// Saga is serializable so we put command gateway as transient  
	private transient CommandGateway commandGateway;

	public OrderSaga(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
	}
	
	@StartSaga
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(OrderCreateEvent createEvent) {
		
		ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
			.orderId(createEvent.getOrderId())
			.productId(createEvent.getProductId())
			.quantity(createEvent.getQuantity())
			.userId(createEvent.getUserId()).build();
		
		commandGateway.send(reserveProductCommand, new CommandCallback<ReserveProductCommand, Object>() {

			@Override
			public void onResult(CommandMessage<? extends ReserveProductCommand> commandMessage,
					CommandResultMessage<? extends Object> commandResultMessage) {
				
				if(commandResultMessage.isExceptional()) {
					//START COMPENSATION TRANSACTION
				}
				
				
			}
			
		});
		
		
	}
	
}
