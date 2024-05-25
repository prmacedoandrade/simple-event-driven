package com.estore.products.query;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.estore.products.core.events.ProductCreateEvent;
import com.estore.products.core.model.ProductBean;
import com.estore.products.core.repository.ProductRepository;

// or ProductEventsHandler
@Component
public class ProductEventsHandler {
	
	private ProductRepository productRepository;
	
	public ProductEventsHandler(ProductRepository productRepository) {
		super();
		this.productRepository = productRepository;
	}

	@EventHandler
	public void on(ProductCreateEvent createEvent) {
		
		ProductBean prodBean = new ProductBean();
		BeanUtils.copyProperties(createEvent, prodBean);
		
		productRepository.save(prodBean);
			
	}
	
}
