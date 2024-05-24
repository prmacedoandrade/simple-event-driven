package com.estore.products.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class CreateProductDTO implements Serializable{
	
	private static final long serialVersionUID = 5398651485197408970L;
	
	private String title;
	private BigDecimal price;
	private Integer quantity;

}
