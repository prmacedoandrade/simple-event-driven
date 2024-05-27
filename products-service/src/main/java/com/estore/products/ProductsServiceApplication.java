package com.estore.products;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import com.estore.core.config.XStreamConfig;
import com.estore.products.command.interceptor.CreateProductCommandInterceptor;
import com.estore.products.core.exception.ProductsServiceEventErrorHandler;

@EnableDiscoveryClient
@SpringBootApplication
@Import({ XStreamConfig.class })
public class ProductsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsServiceApplication.class, args);
	}

	public void registerCreateProductCommandInterceptor(ApplicationContext context, CommandBus bus) {
		bus.registerDispatchInterceptor(context.getBean(CreateProductCommandInterceptor.class));
	}
	
	@Autowired
	public void configure(EventProcessingConfigurer config) {
		config.registerListenerInvocationErrorHandler("product-group", conf-> new ProductsServiceEventErrorHandler());
	}
	
}
