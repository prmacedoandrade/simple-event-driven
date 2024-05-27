package com.estore.payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Import;

import com.estore.core.config.XStreamConfig;


@SpringBootApplication
@Import({ XStreamConfig.class })
public class PaymentsSeviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentsSeviceApplication.class, args);
	}

}
