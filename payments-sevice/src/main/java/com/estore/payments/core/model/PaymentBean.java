package com.estore.payments.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "payment")
public class PaymentBean {

	@Id
	@Column(name = "id", unique = true)
	private String paymentId;

	@Column(name = "order_id")
	public String orderId;

}
