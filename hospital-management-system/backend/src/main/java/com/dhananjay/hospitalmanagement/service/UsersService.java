package com.dhananjay.hospitalmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dhananjay.hospitalmanagement.enums.Role;
import com.dhananjay.hospitalmanagement.exceptions.UserExistsException;
import com.dhananjay.hospitalmanagement.repository.UserRepository;
import com.dhananjay.hospitalmanagement.security.JwtService;
import com.dhananjay.hospitalmanagement.security.Users;

@Service
public class UsersService {

	@Autowired
	UserRepository userRepository;

	AuthenticationManager authenticationManager;
	JwtService jwtService;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public UsersService(UserRepository userRepository,AuthenticationManager authenticationManager
			,JwtService jwtService) {
 		this.userRepository = userRepository;
 		this.authenticationManager = authenticationManager;
 		this.jwtService = jwtService;
	}
	
	//Save users
	public Users registerDoctor(Users user) {
		
	   Users users =	userRepository.findByusername(user.getUsername());
		if(users == null) {
		user.setRole(Role.DOCTOR);
  		user.setPassword(encoder.encode(user.getPassword()));
		}
		else {
			throw new UserExistsException("User Already exist");
		}
		return user;
	}
	
	public Users registerPatient(Users user) {
		   Users users =	userRepository.findByusername(user.getUsername());
			if(users == null) {
				user.setRole(Role.PATIENT);
				user.setPassword(encoder.encode(user.getPassword()));
			}
			else {
				throw new UserExistsException("User Already exist");
			}
		return user;

	}
	
	public Users registerAdmin(Users user) {

	    user.setRole(Role.ADMIN);
	    user.setPassword(encoder.encode(user.getPassword()));
 	    return userRepository.save(user);
	}
	
	//Verify user
	public String verifyUser(Users user) {
 		
 		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
 
		if(authentication.isAuthenticated()) {
 			return jwtService.generateToken(user);
		}
		else {
			return "fail";
		}
	}
	
	
	//Get all users
	
	public List<Users> getAllUsers (){
		return userRepository.findAll();
	}
	
}
