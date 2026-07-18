package com.dhananjay.hospitalmanagement.exceptions;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> noSuchElementFoundException(NoSuchElementException noSuchElementException){
 		return new ResponseEntity<String>(noSuchElementException.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DoctorNotAvailableException.class)
	public ResponseEntity<String> doctorNotAvailableException(DoctorNotAvailableException doctorNotAvailable){
 		return new ResponseEntity<String>(doctorNotAvailable.getMessage(),HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(AppointmentNotCompletedException.class)
	public ResponseEntity<String> appointmentNotCompletedException(AppointmentNotCompletedException appointmentNotCompleted){
 		return new ResponseEntity<String>(appointmentNotCompleted.getMessage(),HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(PrescriptionExistException.class)
	public ResponseEntity<String> prescriptionExistException(PrescriptionExistException prescriptionExist){
 		return new ResponseEntity<String>(prescriptionExist.getMessage(),HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(AppointmentInPastException.class)
	public ResponseEntity<String> appointmentInPastException(AppointmentInPastException appointmentInPast){
		return new ResponseEntity<String> (appointmentInPast.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AppointmentNotFoundException.class)
	public ResponseEntity<String> appointmentNotFoundException(AppointmentNotFoundException appointmentNotFound){
		return new ResponseEntity<String> (appointmentNotFound.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BillNotFoundException.class)
	public ResponseEntity<String> billNotFoundException(BillNotFoundException billNotFound){
		return new ResponseEntity<String> (billNotFound.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserExistsException.class)
	public ResponseEntity<String> billNotFoundException(UserExistsException userExist){
		return new ResponseEntity<String> (userExist.getMessage(),HttpStatus.CONFLICT);
	}
	
	//
 }
