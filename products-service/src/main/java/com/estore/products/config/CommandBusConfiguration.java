package com.estore.products.config;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.estore.products.command.interceptor.CreateProductCommandInterceptor;

@Configuration
public class CommandBusConfiguration {

    private final CreateProductCommandInterceptor myCommandDispatchInterceptor;
    
    public CommandBusConfiguration(CreateProductCommandInterceptor myCommandDispatchInterceptor) {
        this.myCommandDispatchInterceptor = myCommandDispatchInterceptor;
    }

    @Bean
    CommandBus configureCommandBus() {
        CommandBus commandBus = SimpleCommandBus.builder().build();
        commandBus.registerDispatchInterceptor(myCommandDispatchInterceptor);
        return commandBus;
    }
    
}