package com.dhananjay.hospitalmanagement.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.dhananjay.hospitalmanagement.enums.AppointmentStatus;
import com.dhananjay.hospitalmanagement.exceptions.AppointmentNotFoundException;
import com.dhananjay.hospitalmanagement.model.Appointment;
import com.dhananjay.hospitalmanagement.model.Doctor;
import com.dhananjay.hospitalmanagement.repository.AppoinmentRepository;
import com.dhananjay.hospitalmanagement.repository.DoctorRepository;
import com.dhananjay.hospitalmanagement.security.Users;

@Service
public class DoctorService {
	DoctorRepository doctorRepository;
	AppoinmentRepository appointmentRepository;
	UsersService userService;
	
 	public DoctorService(DoctorRepository doctorRepository, AppoinmentRepository appointmentRepository,UsersService userService) {
 		this.doctorRepository = doctorRepository;
		this.appointmentRepository = appointmentRepository;
		this.userService = userService;
	}

	//Add Doctor
	public Doctor addDoctor (Doctor doctor) {
		userService.registerDoctor(doctor.getUser());
		return doctorRepository.save(doctor);
 	}
	
	//Add multiple doctor at once
	public List<Doctor> addMultipleDoctor(List<Doctor> doctor){
		return doctorRepository.saveAll(doctor);
	}
	
	//Get All Doctors
	public List<Doctor> getAllDoctors (){
		return doctorRepository.findAll();
	}
	
	//Find by Id
	public Doctor findById (Long id) {
  		    Doctor doctor = doctorRepository.findById(id).orElse(null);

		    if (doctor == null) {
		        throw new NoSuchElementException("Doctor not found with id: " + id);
		    }
 		    return doctor;
	}
	
	//Delete Doctor
	public void deleteDoctor (Long id) {
		Doctor doctor  = findById(id);
		doctorRepository.delete(doctor);
	}
	
	//Update Doctor
	public Doctor updateDoctor (Doctor updatedDoctor,Long id) {
		Doctor doctor = findById(id);
		doctor.setFirstName(updatedDoctor.getFirstName());
		doctor.setLastName(updatedDoctor.getLastName());
		doctor.setSpecialization(updatedDoctor.getSpecialization());
		doctor.setEmail(updatedDoctor.getEmail());
		doctor.setPhoneNumber(updatedDoctor.getPhoneNumber());
		doctor.setQualification(updatedDoctor.getQualification());
		doctor.setExperienceYears(updatedDoctor.getExperienceYears());
		doctor.setAppointments(updatedDoctor.getAppointments());
		
		return doctor;
 	}
	
	//GetAppointments By Doctor Id
	public List<Appointment> getAppointmentByDoctorId(Long id) {
		 List<Appointment> appointments= appointmentRepository.findByDoctorId(id);
		 return appointments;
 	}
	
	//UpdateAppointment Status
 	public Appointment updateAppointmentStatus (Long doctorId,Long appointmentId,AppointmentStatus status) {
		 
 		Appointment appointment = appointmentRepository.findByIdAndDoctorId(appointmentId,doctorId);
 		if(appointment == null)
 			throw new AppointmentNotFoundException("Appointment Not Found");
 		else
 			appointment.setAppointmentStatus(status);
 	
 		appointmentRepository.save(appointment);
 		return appointment;
 	}
	
	
}
