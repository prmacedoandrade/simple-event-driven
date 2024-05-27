package com.estore.payments.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estore.payments.core.model.PaymentBean;

public interface PaymentRepositoy extends JpaRepository<PaymentBean, String> {

}
