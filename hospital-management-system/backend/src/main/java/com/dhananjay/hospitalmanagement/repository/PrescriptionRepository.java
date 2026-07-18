package com.dhananjay.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dhananjay.hospitalmanagement.model.Prescription;

import jakarta.transaction.Transactional;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long>{
	@Modifying
	@Transactional
	@Query("DELETE FROM Prescription p WHERE p.id = :id")
	void deletePrescriptionById(@Param("id") Long id);
	
	boolean existsByAppointmentId(Long appointmentId);
	
 	
	Prescription findByAppointmentIdAndAppointmentPatientId(Long patientId,Long appointmentId);

}
