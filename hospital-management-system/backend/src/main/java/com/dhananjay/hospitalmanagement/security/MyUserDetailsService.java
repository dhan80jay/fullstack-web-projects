package com.dhananjay.hospitalmanagement.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dhananjay.hospitalmanagement.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{

	UserRepository userRepository;

	public MyUserDetailsService(UserRepository userRepository) {
 		this.userRepository = userRepository;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userRepository.findByusername(username);
 		if(user == null)
			throw new UsernameNotFoundException("User not found ");
		 
		return user;
	}

}
