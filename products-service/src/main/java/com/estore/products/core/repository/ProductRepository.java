package com.estore.products.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estore.products.core.model.ProductBean;

public interface ProductRepository  extends JpaRepository<ProductBean, String>{

}
