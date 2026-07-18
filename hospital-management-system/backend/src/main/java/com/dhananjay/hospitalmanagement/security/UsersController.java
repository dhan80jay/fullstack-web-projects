package com.dhananjay.hospitalmanagement.security;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dhananjay.hospitalmanagement.service.UsersService;

@RestController
public class UsersController {
	UsersService userService;

	public UsersController(UsersService userService) {
 		this.userService = userService;
	}
	
	
	@PostMapping("/login")
	public String verifyUser(@RequestBody Users user) {
 		System.out.println(user);
	   return	userService.verifyUser(user);
	}
	
}
