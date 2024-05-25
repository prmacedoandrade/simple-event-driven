package com.estore.products.command.interceptor;

import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.estore.products.command.CreateProductCommand;
import com.estore.products.core.model.ProductLookupBean;
import com.estore.products.core.repository.ProductLookupRepository;

/**
 * This interceptor will intercept others commands in the bus
 * 
 */
@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateProductCommandInterceptor.class);
	
	private final ProductLookupRepository productLookupRepository;
	
	public CreateProductCommandInterceptor(ProductLookupRepository productLookupRepository) {
		this.productLookupRepository = productLookupRepository;
	}

	@Override
	public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
			List<? extends CommandMessage<?>> messages) {

		return (index, command) ->{
			
			LOGGER.info("Command intercepted: {}", command.getPayloadType());

			//Check command type
			if(CreateProductCommand.class.equals(command.getPayloadType())) {
				
				CreateProductCommand createProductCommand = (CreateProductCommand)command.getPayload();
				
				Boolean isPresent = productLookupRepository
						.findByProductIdOrTitle(createProductCommand.getProductId(), createProductCommand.getTitle()).isPresent();
				
				if(Boolean.TRUE.equals(isPresent)) {
					throw new IllegalStateException(String.format("Product with product id %s or title %s already exists", 
							createProductCommand.getProductId(), createProductCommand.getTitle()));
				}
				
			}
			
			return command;
		};
	}
	
	

}
