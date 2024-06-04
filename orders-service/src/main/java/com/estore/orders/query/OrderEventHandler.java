package com.estore.orders.query;

import java.util.Optional;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.estore.orders.core.events.OrderApprovedEvent;
import com.estore.orders.core.events.OrderCreateEvent;
import com.estore.orders.core.events.OrderRejectedEvent;
import com.estore.orders.core.model.OrderBean;
import com.estore.orders.core.model.OrderStatus;
import com.estore.orders.core.repository.OrderRepository;

@Component
@ProcessingGroup("order-group")
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
	
	@EventHandler
	public void on(OrderApprovedEvent approvedEvent) {
		
		Optional<OrderBean> optBean = orderRepository.findById(approvedEvent.getOrderId());
		
		if(optBean.isEmpty()) {
			//TODO deal with it
		}
		
		OrderBean orderBean = optBean.get();
		orderBean.setOrderStatus(approvedEvent.getOrderStatus());
		
		orderRepository.save(orderBean);
		
	}
	
	@EventHandler
	public void on(OrderRejectedEvent orderRejectedEvent) {
		
		Optional<OrderBean> optBean = orderRepository.findById(orderRejectedEvent.getOrderId());
		
		if(optBean.isEmpty()) {
			//TODO deal with it
		}
		
		OrderBean orderBean = optBean.get();
		orderBean.setOrderStatus(orderRejectedEvent.getOrderStatus());
		
		orderRepository.save(orderBean);
		
	}
	
}
