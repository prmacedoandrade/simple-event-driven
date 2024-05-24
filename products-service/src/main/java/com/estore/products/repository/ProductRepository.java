package com.estore.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estore.products.model.ProductBean;

public interface ProductRepository  extends JpaRepository<ProductBean, String>{

}
