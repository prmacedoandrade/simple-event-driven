package com.estore.products.query.rest.controller;

import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.products.query.FindProductsQuery;
import com.estore.products.query.rest.ProductRestModel;


@RestController
@RequestMapping("/products")
public class ProductQueryController {
	
	private QueryGateway queryGateway;
	
	public ProductQueryController(QueryGateway queryGateway) {
		this.queryGateway = queryGateway;
	}

	@GetMapping
	public List<ProductRestModel> getProducts() {
		
		FindProductsQuery findProductsQuery = new FindProductsQuery();
		return queryGateway.query(findProductsQuery, ResponseTypes.multipleInstancesOf(ProductRestModel.class)).join();
		
	}
	

}
