package com.dhananjay.hospitalmanagement.exceptions;

public class AppointmentInPastException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7095336846391220371L;
	
	public AppointmentInPastException(String msg) {
		 super(msg);
	}
}
