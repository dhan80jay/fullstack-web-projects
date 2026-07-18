package com.dhananjay.hospitalmanagement.exceptions;

public class PrescriptionExistException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -46899417678799305L;

	public PrescriptionExistException(String msg) {
		 super(msg);
	}
}
