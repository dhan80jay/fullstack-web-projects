package com.dhananjay.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhananjay.hospitalmanagement.security.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{

	Users findByusername(String username);
	
}
