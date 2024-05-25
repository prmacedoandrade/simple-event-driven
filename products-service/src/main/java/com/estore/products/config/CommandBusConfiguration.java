package com.estore.products.config;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.estore.products.command.CreateProductsCommandInterceptor;

@Configuration
public class CommandBusConfiguration {

    private final CreateProductsCommandInterceptor myCommandDispatchInterceptor;
    
    public CommandBusConfiguration(CreateProductsCommandInterceptor myCommandDispatchInterceptor) {
        this.myCommandDispatchInterceptor = myCommandDispatchInterceptor;
    }

    @Bean
    CommandBus configureCommandBus() {
        CommandBus commandBus = SimpleCommandBus.builder().build();
        commandBus.registerDispatchInterceptor(myCommandDispatchInterceptor);
        return commandBus;
    }
    
}