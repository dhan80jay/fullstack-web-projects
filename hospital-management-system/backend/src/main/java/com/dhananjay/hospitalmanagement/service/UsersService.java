package com.dhananjay.hospitalmanagement.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
		user.setEmail(user.getEmail());  		
		user.setPassword(encoder.encode(user.getPassword()));
		}
		else {
			throw new UserExistsException("User Already exist");
		}
		return user;
	}
	
	
	public Users updateDoctorUser(Users updatedUser, Long id) {

	    Users user = userRepository.findById(id)
	            .orElseThrow(() ->
	                new NoSuchElementException("User not found with id " + id)
	            );

	    user.setUsername(updatedUser.getUsername());
	    user.setEmail(updatedUser.getEmail());

	    if (updatedUser.getPassword() != null &&
	        !updatedUser.getPassword().isBlank()) {

	        user.setPassword(
	            encoder.encode(updatedUser.getPassword())
	        );
	    }

	    user.setRole(Role.DOCTOR);

	    return userRepository.save(user);
	}
	
	public Users updateAdminUser(Users updatedUser, Long id) {

	    Users user = userRepository.findById(id)
	            .orElseThrow(() ->
	                new NoSuchElementException("User not found with id " + id)
	            );

	    user.setUsername(updatedUser.getUsername());
	    user.setEmail(updatedUser.getEmail());

	    if (updatedUser.getPassword() != null &&
	        !updatedUser.getPassword().isBlank()) {

	        user.setPassword(
	            encoder.encode(updatedUser.getPassword())
	        );
	    }

	    user.setRole(Role.ADMIN);

	    return userRepository.save(user);
	}
	
	public Users updatePatientUser(Users updatedUser, Long id) {

	    Users user = userRepository.findById(id)
	            .orElseThrow(() ->
	                new NoSuchElementException("User not found with id " + id)
	            );

	    user.setUsername(updatedUser.getUsername());
	    user.setEmail(updatedUser.getEmail());

	    if (updatedUser.getPassword() != null &&
	        !updatedUser.getPassword().isBlank()) {

	        user.setPassword(
	            encoder.encode(updatedUser.getPassword())
	        );
	    }

	    user.setRole(Role.PATIENT);

	    return userRepository.save(user);
	}
	
	
	public Users registerPatient(Users user) {
		   Users users =	userRepository.findByusername(user.getUsername());
			if(users == null) {
				user.setRole(Role.PATIENT);
				user.setEmail(user.getEmail());  	
 				user.setPassword(encoder.encode(user.getPassword()));
			}
			else {
				throw new UserExistsException("User Already exist");
			}
			System.out.println("Inside register Patient");
		
		return userRepository.save(user);

	}
	
	public Users registerAdmin(Users user) {

	    user.setRole(Role.ADMIN);
		user.setEmail(user.getEmail());  		
	    user.setPassword(encoder.encode(user.getPassword()));
 	    return userRepository.save(user);
	
	}
	
	//forgot Password
	public Users resetPassword(Users user) {
		Users dbUser = userRepository.findByusername(user.getUsername());
		
		if(dbUser == null) {
			 throw new UsernameNotFoundException("User not found !");
		}
		
		 if (!dbUser.getEmail().equals(user.getEmail())) {
		     throw new IllegalArgumentException("Invalid email address !");
		 }

		
		dbUser.setPassword(encoder.encode(user.getPassword()));
		return userRepository.save(dbUser);
	}
	
	//Verify user
	public Map<String, String> verifyUser(Users user) {

	    Authentication authentication =
	            authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                    user.getUsername(),
	                    user.getPassword()
	                )
	            );

	    if (authentication.isAuthenticated()) {

	        String token = jwtService.generateToken(user);

	        String role = authentication.getAuthorities()
	                .stream()
	                .findFirst()
	                .map(GrantedAuthority::getAuthority)
	                .orElse(null);

	        Map<String, String> response = new HashMap<>();

	        response.put("token", token);
	        response.put("role", role);

	        return response;
	    }

	    return null;
	}	
	
	//Get all users
	
	public List<Users> getAllUsers (){
		return userRepository.findAll();
	}
	
}
