package com.dhananjay.hospitalmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhananjay.hospitalmanagement.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long>{
	
	void deleteByAppointment_Id(Long appointmentId);
	
	Bill findByAppointmentIdAndAppointmentPatientId(Long appointmentId, Long patientId);
	Boolean existsByAppointmentId(Long appointmentId);
	
	List<Bill> findByAppointment_Patient_User_Username(String username);
}
