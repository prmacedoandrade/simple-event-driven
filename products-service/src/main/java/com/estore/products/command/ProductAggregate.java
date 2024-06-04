package com.estore.products.command;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.estore.core.command.CancelProductReservationCommand;
import com.estore.core.command.ReserveProductCommand;
import com.estore.core.event.ProductReservationCanceledEvent;
import com.estore.core.event.ProductReserveEvent;
import com.estore.products.core.events.ProductCreateEvent;

@Aggregate
public class ProductAggregate {

	@AggregateIdentifier
	private String productId;
	private String title;
	private BigDecimal price;
	private Integer quantity;
	
	public ProductAggregate() {
	}

	@CommandHandler
	public ProductAggregate(CreateProductCommand createProductCommand) {
		
		if(createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Prece cannot be less or equal than zero");
		}
		
		if(!StringUtils.hasLength(createProductCommand.getTitle())) {
			throw new IllegalArgumentException("Title cannot be empty");
		}
		
		ProductCreateEvent productCreateEvent = new ProductCreateEvent();
		
		BeanUtils.copyProperties(createProductCommand, productCreateEvent);
		
		AggregateLifecycle.apply(productCreateEvent);
		
		//Any error generated here will become a CommandExecutionException error
		//To showcase error handling on Aggregate class
		//if(true) {
			//throw new Exception("Exception throw in ProductAggregate method");
		//}
		
	}
	
	//Avoid put bussines logic
	@EventSourcingHandler
	public void on(ProductCreateEvent productCreateEvent) {
		this.productId = productCreateEvent.getProductId();
		this.title = productCreateEvent.getTitle();
		this.price = productCreateEvent.getPrice();
		this.quantity = productCreateEvent.getQuantity();
	}
	
	@CommandHandler
	public void handle(ReserveProductCommand reserveProductCommand) {
		
		if(quantity < reserveProductCommand.getQuantity()) {
			throw new IllegalArgumentException("Insufficient number of items in stock");
		}
		
		ProductReserveEvent productReserveEvent = ProductReserveEvent.builder()
				.orderId(reserveProductCommand.getOrderId())
				.productId(reserveProductCommand.getProductId())
				.quantity(reserveProductCommand.getQuantity())
				.userId(reserveProductCommand.getUserId()).build();
		
		AggregateLifecycle.apply(productReserveEvent);
		
	}
	
	@EventSourcingHandler
	public void on(ProductReserveEvent productReserveEvent) {
		this.quantity -= productReserveEvent.getQuantity();
	}
	
	
	@CommandHandler
	public void handle(CancelProductReservationCommand cancelProductReservationCommand) {
		
		ProductReservationCanceledEvent cancelReserveEvent = new ProductReservationCanceledEvent();
		BeanUtils.copyProperties(cancelProductReservationCommand, cancelReserveEvent);
		AggregateLifecycle.apply(cancelReserveEvent);
		
	}
	
	@EventSourcingHandler
	public void on(ProductReservationCanceledEvent productReservationCanceledEvent) {
		this.quantity += productReservationCanceledEvent.getQuantity();
	}
	
}
