package com.estore.products;

import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.estore.products.core.exception.ProductsServiceEventErrorHandler;

//@EnableDiscoveryClient - It's not required anymore
@SpringBootApplication
public class ProductsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsServiceApplication.class, args);
	}

	
	@Autowired
	public void configure(EventProcessingConfigurer config) {
		
		config.registerListenerInvocationErrorHandler("product-group", conf-> new ProductsServiceEventErrorHandler());
		
	}
	
}
