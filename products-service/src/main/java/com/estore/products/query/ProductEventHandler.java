package com.estore.products.query;

import java.util.Optional;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.estore.core.event.ProductReservationCanceledEvent;
import com.estore.core.event.ProductReserveEvent;
import com.estore.products.core.events.ProductCreateEvent;
import com.estore.products.core.model.ProductBean;
import com.estore.products.core.repository.ProductRepository;

/**
 * On query side, update read DB
 * 
 */
// Common name ProductProjection
@Component
@ProcessingGroup("product-group")
public class ProductEventHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductEventHandler.class);
	
	private final ProductRepository productRepository;
	
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
		// Log error msg
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
		//if (true) {
		//	throw new Exception("Forcing Exception in Event Handler class");
		//}

	}
	
	@EventHandler
	public void on(ProductReserveEvent productReserveEvent) {

		Optional<ProductBean> opt = productRepository.findById(productReserveEvent.getProductId());
		
		LOGGER.info("ProductReserveEvent in ProductEventHandler is called for order id {} and product id {}",
				productReserveEvent.getOrderId(), productReserveEvent.getProductId());

		if (opt.isPresent()) {
			ProductBean bean = opt.get();
			
			LOGGER.debug("ProductReserveEvent: current product quantity {}", bean.getQuantity());
			
			bean.setQuantity(bean.getQuantity() - productReserveEvent.getQuantity());
			productRepository.save(bean);
			
			LOGGER.debug("ProductReserveEvent: new product quantity {}", bean.getQuantity());
		}

	}
	
	@EventHandler
	public void on(ProductReservationCanceledEvent canceledEvent) {
		
		Optional<ProductBean> opt = productRepository.findById(canceledEvent.getProductId());

		LOGGER.info("ProductReservationCanceledEvent in ProductEventHandler is called for order id {} and product id {}",
				canceledEvent.getOrderId(), canceledEvent.getProductId());

		if (opt.isPresent()) {
			ProductBean bean = opt.get();
			
			LOGGER.debug("ProductReservationCanceledEvent: current product quantity {}", bean.getQuantity());
			
			bean.setQuantity(bean.getQuantity() + canceledEvent.getQuantity());
			productRepository.save(bean);
			
			LOGGER.debug("ProductReservationCanceledEvent: new product quantity {}", bean.getQuantity());
			
		}
		
	}

}
