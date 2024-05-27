package com.estore.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.estore.core.config.XStreamConfig;

@SpringBootApplication
@Import({ XStreamConfig.class })
public class UsersServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(UsersServiceApplication.class, args);
	}

}
