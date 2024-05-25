package com.estore.products.query.rest;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductRestModel implements Serializable{
	
	private static final long serialVersionUID = 7859238856938771838L;
	
	private String productId;
	private String title;
	private BigDecimal price;
	private Integer quantity;

}
