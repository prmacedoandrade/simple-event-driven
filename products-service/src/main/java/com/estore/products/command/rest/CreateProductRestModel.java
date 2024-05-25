package com.estore.products.command.rest;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateProductRestModel implements Serializable{
	
	private static final long serialVersionUID = 5398651485197408970L;
	
	@NotBlank(message = "Product title is required")
	private String title;
	
	@NotNull(message = "Price is required")
	@Min(value=1, message = "Price cannot be lower than 1")
	private BigDecimal price;
	
	//Just to showcase max validation
	@NotNull(message = "Quantity is required")
	@Min(value=1, message = "Quantity cannot be lower than 1")
	@Max(value=5, message = "Quantity cannot be lager than 5")
	private Integer quantity;

}
