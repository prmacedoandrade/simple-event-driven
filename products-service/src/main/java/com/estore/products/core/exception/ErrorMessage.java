package com.estore.products.core.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {

	private final Date timeStamp;
	private final String message;
	
}
