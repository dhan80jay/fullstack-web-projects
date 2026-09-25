package com.dhananjay.hospitalmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhananjay.hospitalmanagement.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{

	Optional<Doctor> findByUser_Username(String username);

}
