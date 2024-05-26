package com.estore.products.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.estore.products.core.events.ProductCreateEvent;
import com.estore.products.core.model.ProductBean;
import com.estore.products.core.repository.ProductRepository;

// or ProductEventsHandler
@Component
@ProcessingGroup("product-group")
public class ProductEventHandler {
	
	private ProductRepository productRepository;
	
	public ProductEventHandler(ProductRepository productRepository) {
		super();
		this.productRepository = productRepository;
	}
	
	@ExceptionHandler(resultType = Exception.class)
	public void handle(Exception exception) throws Exception {
		throw exception;
	}
	
	@ExceptionHandler(resultType = IllegalArgumentException.class)
	public void handle(IllegalArgumentException illegalArgumentException) {
		
	}

	@EventHandler
	public void on(ProductCreateEvent createEvent) throws Exception {
		
		ProductBean prodBean = new ProductBean();
		BeanUtils.copyProperties(createEvent, prodBean);
		
		try {
			productRepository.save(prodBean);
		
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		// Any error generated here will become a CommandExecutionException error
		// To showcase error handling on Aggregate class
		if (true) {
			throw new Exception("Forcing Exception in Event Handler class");
		}

	}
	
}
