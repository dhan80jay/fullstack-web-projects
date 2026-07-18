package com.dhananjay.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhananjay.hospitalmanagement.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{

}
