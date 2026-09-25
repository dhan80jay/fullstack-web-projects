package com.dhananjay.hospitalmanagement.security;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dhananjay.hospitalmanagement.model.Doctor;
import com.dhananjay.hospitalmanagement.model.Patient;
import com.dhananjay.hospitalmanagement.service.DoctorService;
import com.dhananjay.hospitalmanagement.service.PatientService;
import com.dhananjay.hospitalmanagement.service.UsersService;

@RestController
@RequestMapping("api/auth")
public class UsersController {
	UsersService userService;
	DoctorService doctorService;
	PatientService patientService;
	
	public UsersController(UsersService userService, DoctorService doctorService, PatientService patientService) {
		super();
		this.userService = userService;
		this.doctorService = doctorService;
		this.patientService = patientService;
	}

	@PostMapping("/login")
	public Map<String, String> verifyUser(@RequestBody Users user) {
	    return userService.verifyUser(user);
	}
	
	@PutMapping("/forgot")
	public ResponseEntity<String> forgotPassword(@RequestBody Users user) {

	    userService.resetPassword(user);

	    return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
	}


}
