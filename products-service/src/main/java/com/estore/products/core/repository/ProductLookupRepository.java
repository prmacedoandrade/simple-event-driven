package com.estore.products.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estore.products.core.model.ProductLookupBean;
import com.google.common.base.Optional;

public interface ProductLookupRepository extends JpaRepository<ProductLookupBean, String>{
	
	Optional<ProductLookupBean> findByProductIdOrTitle(String productId, String title);
}
