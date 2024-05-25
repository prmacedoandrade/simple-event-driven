package com.estore.products.command;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.estore.products.core.events.ProductCreateEvent;
import com.estore.products.core.model.ProductLookupBean;
import com.estore.products.core.repository.ProductLookupRepository;

@Component
@ProcessingGroup("product-group")
public class ProductLookupEventsHandler {
	
	private ProductLookupRepository productLookupRepository;
	
	public ProductLookupEventsHandler(ProductLookupRepository productLookupRepository) {
		this.productLookupRepository = productLookupRepository;
	}

	@EventHandler
	public void on(ProductCreateEvent createEvent) {
		
		ProductLookupBean productLookupBean = new ProductLookupBean(createEvent.getProductId(), createEvent.getTitle());
		productLookupRepository.save(productLookupBean);
		
	}
	
}
