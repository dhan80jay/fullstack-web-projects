package com.dhananjay.hospitalmanagement.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhananjay.hospitalmanagement.model.Appointment;

public interface AppoinmentRepository extends JpaRepository<Appointment, Long>{
	
	boolean existsByDoctorIdAndAppointmentDateAndAppointmentTime(
	        Long doctorId,
	        LocalDate appointmentDate,
	        LocalTime appointmentTime
	);
	
	List<Appointment> findByDoctorId(Long doctorId);
	Appointment findByIdAndDoctorId(Long appointmentId,Long doctorId);
}
