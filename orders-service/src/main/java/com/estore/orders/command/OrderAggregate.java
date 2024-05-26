package com.estore.orders.command;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.estore.orders.core.events.CreateOrderEvent;
import com.estore.orders.core.model.OrderStatus;

@Aggregate
public class OrderAggregate {
	
	@AggregateIdentifier
	private String orderId;
	private String userId;
	private String productId;
	private int quantity;
	private String addressId;
	private OrderStatus orderStatus;
	
	@CommandHandler
	public OrderAggregate(CreateOrderCommand createOrderCommand) {
	
		//TODO: Validation
		CreateOrderEvent createOrderEvent = new CreateOrderEvent();
		BeanUtils.copyProperties(createOrderCommand, createOrderEvent);
		
		AggregateLifecycle.apply(createOrderEvent);
		
	}
	
	@EventSourcingHandler
	public void on(CreateOrderEvent createOrderEvent) {
		
		this.orderId = createOrderEvent.getOrderId();
		this.userId = createOrderEvent.getUserId();
		this.productId = createOrderEvent.getProductId();
		this.quantity = createOrderEvent.getQuantity();
		this.addressId = createOrderEvent.getAddressId();
		this.orderStatus = createOrderEvent.getOrderStatus();
		
	}
	
}
