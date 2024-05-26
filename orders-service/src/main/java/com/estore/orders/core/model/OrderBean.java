package com.estore.orders.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name= "order_tb")
public class OrderBean {

	@Id
	@Column(name = "order_id", unique = true)
	private String orderId;
	
	private String userId;
	private String productId;
	private int quantity;
	private String addressId;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
}
