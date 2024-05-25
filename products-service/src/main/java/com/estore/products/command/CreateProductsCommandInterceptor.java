package com.estore.products.command;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * This interceptor will intercept others commands in the bus
 * 
 */
@Component
public class CreateProductsCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateProductsCommandInterceptor.class);

	@Override
	public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
			List<? extends CommandMessage<?>> messages) {

		return (index, command) ->{
			
			LOGGER.info("Command intercepted: {}", command.getPayloadType());

			//Check command type
			if(CreateProductCommand.class.equals(command.getPayloadType())) {
				
				CreateProductCommand createProductCommand = (CreateProductCommand)command.getPayload();
				
				//Just to showo ff validation 
				if(createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
					throw new IllegalArgumentException("Prece cannot be less or equal than zero");
				}
				
				if(!StringUtils.hasLength(createProductCommand.getTitle())) {
					throw new IllegalArgumentException("Title cannot be empty");
				}
				
			}
			
			return command;
		};
	}
	
	

}
