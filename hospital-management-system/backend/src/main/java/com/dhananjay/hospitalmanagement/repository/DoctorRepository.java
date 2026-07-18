package com.dhananjay.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhananjay.hospitalmanagement.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{

}
