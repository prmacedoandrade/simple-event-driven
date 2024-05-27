package com.estore.users.query.rest.controller;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estore.core.model.User;
import com.estore.core.query.FechtUserPaymentDetailsQuery;

@RestController
@RequestMapping("/users")
public class UserQueryController {

	@Autowired
	private QueryGateway queryGateway;

	public UserQueryController(QueryGateway queryGateway) {
		this.queryGateway = queryGateway;
	}
	
	@GetMapping("{userId}/payment-details")
	public User getUserPaymentDetails(@PathVariable String userId) {
	
		FechtUserPaymentDetailsQuery query = new FechtUserPaymentDetailsQuery(userId);
		
		return queryGateway.query(query, ResponseTypes.instanceOf(User.class)).join();
		
	}
	
}
