package com.dhananjay.hospitalmanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig { 

	 
	private MyUserDetailsService myUserDetailsService;
	private JwtFilter jwtFilter;
	
 	
	public SecurityConfig(MyUserDetailsService myUserDetailsService,JwtFilter jwtFilter) {
 		this.myUserDetailsService = myUserDetailsService;
 		this.jwtFilter = jwtFilter;
	}


	@Bean
	public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
		http.csrf(customizer -> customizer.disable());
		http.authenticationProvider(authenticationProvider());

 		http.authorizeHttpRequests(
 				auth -> 
 				auth.requestMatchers("/login").permitAll()
 				.requestMatchers("/admin/register").permitAll()
 				.requestMatchers("/api/v1/patients/register").permitAll()
 				.requestMatchers("/api/v1/doctors/register").permitAll()
 				.requestMatchers("/admin/**").hasRole("ADMIN")
 				.requestMatchers("/api/v1/patients/**").hasAnyRole("PATIENT","ADMIN","DOCTOR")
 				.requestMatchers("/api/v1/doctors/**").hasAnyRole("DOCTOR","ADMIN")
 				.requestMatchers("/api/v1/medicines/**").hasAnyRole("DOCTOR","ADMIN")
 				.requestMatchers("/api/v1/prescriptions/**").hasAnyRole("DOCTOR","ADMIN","PATIENT")
 				.requestMatchers("/api/v1/bills/**").hasAnyRole("DOCTOR","ADMIN","PATIENT")
  				);
//		http.formLogin(Customizer.withDefaults());
//		http.httpBasic(Customizer.withDefaults());
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager (AuthenticationConfiguration configurer) throws Exception {
		return configurer.getAuthenticationManager();
	}
	
	
	//Default UserDetailsService
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		
//		UserDetails user1 = User
//				.withDefaultPasswordEncoder()
//				.username("akash")
//				.password("8080")
//				.roles("USER")
//				.build();
//		
//		UserDetails user2 = User
//				.withDefaultPasswordEncoder()
//				.username("prakash")
//				.password("pkash")
//				.roles("ADMIN")
//				.build();
//			
//		
//		return new InMemoryUserDetailsManager(user1,user2);
//	}
	
	//Custom Authentication Provider
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
 		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(myUserDetailsService);
		return provider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}