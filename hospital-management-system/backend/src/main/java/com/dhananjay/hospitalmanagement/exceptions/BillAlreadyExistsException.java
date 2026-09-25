package com.dhananjay.hospitalmanagement.exceptions;

public class BillAlreadyExistsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BillAlreadyExistsException(String message) {
		super(message);
	}

}
