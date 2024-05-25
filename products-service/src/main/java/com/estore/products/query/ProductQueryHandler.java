package com.estore.products.query;

import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.estore.products.core.repository.ProductRepository;
import com.estore.products.query.rest.ProductRestModel;

@Component
public class ProductQueryHandler {
	
	private final ProductRepository productRepository;

	public ProductQueryHandler(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@QueryHandler
	public List<ProductRestModel> findProducts(FindProductsQuery findProductsQuery){
		
		return productRepository.findAll().stream().map(o->{
			ProductRestModel productDTO = new ProductRestModel();
			BeanUtils.copyProperties(o, productDTO);
			return productDTO;
		}).toList();
		
	}
	
}
