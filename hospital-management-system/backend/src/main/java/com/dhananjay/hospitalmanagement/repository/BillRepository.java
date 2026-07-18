package com.dhananjay.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
 import com.dhananjay.hospitalmanagement.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long>{
	
	void deleteByAppointment_Id(Long appointmentId);
	
	Bill findByAppointmentIdAndAppointmentPatientId(Long appointmentId, Long patientId);
}
