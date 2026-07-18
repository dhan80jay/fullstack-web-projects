package com.dhananjay.hospitalmanagement.exceptions;

public class AppointmentNotCompletedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2421248345580098484L;

	public AppointmentNotCompletedException(String msg) {
		 super(msg);
	}
	
}
