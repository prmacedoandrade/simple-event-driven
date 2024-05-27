package com.estore.users.query;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import com.estore.core.model.PaymentDetails;
import com.estore.core.model.User;
import com.estore.core.query.FechtUserPaymentDetailsQuery;

@Component
public class UserQueryHandler {

	@QueryHandler
	public User findUserPaymentDetails(FechtUserPaymentDetailsQuery query) {

		PaymentDetails paymentDetails = PaymentDetails.builder()
				.cardNumber("123Card")
				.cvv("123")
				.name("Paulo Macedo")
				.validUntilMonth(12)
				.validUntilYear(2030).build();

		return User.builder()
				.firstName("Paulo")
				.lastName("Macedo")
				.userId(query.getUserId())
				.paymentDetails(paymentDetails).build();

	}

}
