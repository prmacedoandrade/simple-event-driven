package com.estore.orders.query;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.estore.orders.core.events.OrderCreateEvent;
import com.estore.orders.core.model.OrderBean;
import com.estore.orders.core.repository.OrderRepository;

@Component
public class OrderEventHandler {

	private OrderRepository orderRepository;

	public OrderEventHandler(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	@EventHandler
	public void on(OrderCreateEvent createOrderEvent) {
		
		OrderBean orderBean = new OrderBean();
		
		BeanUtils.copyProperties(createOrderEvent, orderBean);
		orderRepository.save(orderBean);
		
	}
	
}
