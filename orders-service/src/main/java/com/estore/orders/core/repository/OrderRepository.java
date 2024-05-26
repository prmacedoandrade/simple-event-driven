package com.estore.orders.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estore.orders.core.model.OrderBean;

public interface OrderRepository extends JpaRepository<OrderBean, String> {

}
