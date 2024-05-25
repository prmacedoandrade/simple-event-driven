package com.estore.products.core;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.estore.products.command.CreateProductCommand;
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
		
	}
	 
	//Avoid put bussines logic
	@EventSourcingHandler
	public void on(ProductCreateEvent productCreateEvent) {
		this.productId = productCreateEvent.getProductId();
		this.title = productCreateEvent.getTitle();
		this.price = productCreateEvent.getPrice();
		this.quantity = productCreateEvent.getQuantity();
		
	}
	
}
