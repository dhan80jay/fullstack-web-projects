package com.dhananjay.hospitalmanagement.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.dhananjay.hospitalmanagement.enums.AppointmentStatus;
import com.dhananjay.hospitalmanagement.exceptions.AppointmentInPastException;
import com.dhananjay.hospitalmanagement.exceptions.DoctorNotAvailableException;
import com.dhananjay.hospitalmanagement.model.Appointment;
import com.dhananjay.hospitalmanagement.model.Doctor;
import com.dhananjay.hospitalmanagement.model.Patient;
import com.dhananjay.hospitalmanagement.repository.AppoinmentRepository;

import jakarta.transaction.Transactional;

@Service
public class AppointmentService {
	
	AppoinmentRepository appointmentRepository;
	PatientService patientService;
	DoctorService doctorService;
 
	
	
	public AppointmentService(AppoinmentRepository appointmentRepository, PatientService patientService,
			DoctorService doctorService) {
 		this.appointmentRepository = appointmentRepository;
		this.patientService = patientService;
		this.doctorService = doctorService;
	}

	//Create appointment
	public Appointment createAppointment (Appointment appointment) {
		
		Long doctorId = appointment.getDoctor().getId();
 	    Doctor doctor = doctorService.findById(doctorId);
 	    
 	    Long patientId = appointment.getPatient().getId();
 	    Patient patient = patientService.findPatientById(patientId);
        
	    LocalTime time = appointment.getAppointmentTime();
        LocalDate localDate = appointment.getAppointmentDate();

        boolean isAvailable =  appointmentRepository.existsByDoctorIdAndAppointmentDateAndAppointmentTime(doctorId,localDate,time);

        if(localDate.isAfter(LocalDate.now()) || localDate.equals(LocalDate.now()) && time.isAfter(LocalTime.now())) {
      	    //boolean isAvailable =  doctorAvailability (appointment);
            if(isAvailable) {
    	    	throw new DoctorNotAvailableException("Doctor is not available at this time slot.");
     	    }else {
    	        appointment.setDoctor(doctor);
    	 	    appointment.setPatient(patient);
    	    	return appointmentRepository.save(appointment);
    	    }
        }else {
        	throw new AppointmentInPastException("Appointment date and time cannot be in the past.");
        }
	}
	
	//create appointment by patient
	//Create appointment
	public Appointment createAppointmentByPatient (Appointment appointment,Long patientId) {
		
		Long doctorId = appointment.getDoctor().getId();
 	    Doctor doctor = doctorService.findById(doctorId);
 	    
 	    Patient patient = patientService.findPatientById(patientId);
        
	    LocalTime time = appointment.getAppointmentTime();
        LocalDate localDate = appointment.getAppointmentDate();

        boolean isAvailable =  appointmentRepository.existsByDoctorIdAndAppointmentDateAndAppointmentTime(doctorId,localDate,time);

        if(localDate.isAfter(LocalDate.now()) || localDate.equals(LocalDate.now()) && time.isAfter(LocalTime.now())) {
      	    //boolean isAvailable =  doctorAvailability (appointment);
            if(isAvailable) {
    	    	throw new DoctorNotAvailableException("Doctor is not available at this time slot.");
     	    }else {
    	        appointment.setDoctor(doctor);
    	 	    appointment.setPatient(patient);
    	 	    appointment.setAppointmentStatus(AppointmentStatus.BOOKED);
    	    	return appointmentRepository.save(appointment);
    	    }
        }else {
        	throw new AppointmentInPastException("Appointment date and time cannot be in the past.");
        }
	}

	
	
	//find appointment by prescription Id
	public Appointment findAppointmentByPrescriptionId(Long prescriptionId) {
	    Appointment appointment =  appointmentRepository.findAppointmentByPrescriptionId(prescriptionId);
	    if(appointment == null) {
	    	throw new NoSuchElementException("Appointment Not Found");
	    }
	    return appointment;
	}
	
	//Create Multiple appointment
	@Transactional
	public List<Appointment> createMultipleAppointment (List<Appointment> appointments) {
	    for (Appointment appointment : appointments) {

	        Long doctorId = appointment.getDoctor().getId();
	        Long patientId = appointment.getPatient().getId();
 	        Doctor doctor = doctorService.findById(doctorId);
	        Patient patient = patientService.findPatientById(patientId);
	        
 	        LocalTime time = appointment.getAppointmentTime();
	        LocalDate localDate = appointment.getAppointmentDate();

	        
	        appointment.setDoctor(doctor);
	        appointment.setPatient(patient);
	    }

	    
		return appointmentRepository.saveAll(appointments);
	}
 	
	//Get All appointment
	public List<Appointment> getAllAppointments (){
		return appointmentRepository.findAll();
	}
	
	//find appointment by id
	public Appointment findAppointmentById (Long id) {
		Appointment appointment = appointmentRepository.findById(id).orElse(null);
		if(appointment == null)
			throw new NoSuchElementException("Appointment not found with id: "+id);
 			return appointment;
	}
	
	//find appointment by patient id
	public List <Appointment> findAppointmentsByPatientId(Long id) {
		List<Appointment> appointment = appointmentRepository.findAppointmentsByPatientId(id);
		if(appointment == null)
			throw new NoSuchElementException("Appointment not found with id: "+id);
 			return   appointment;

	}
	
	//Delete appointment 
	public void deleteAppointment (Long id) {
		Appointment appointment = findAppointmentById(id);
		appointmentRepository.delete(appointment);
	}
	
	
	//Update appointment
	public Appointment updateAppointment(Appointment updatedAppointment,Long id) {
		
		Appointment appointment = findAppointmentById(id);
		if(appointment.getAppointmentStatus() != AppointmentStatus.COMPLETED) {
		appointment.setAppointmentDate(updatedAppointment.getAppointmentDate());
		appointment.setAppointmentTime(updatedAppointment.getAppointmentTime());
		appointment.setAppointmentStatus(updatedAppointment.getAppointmentStatus());
		appointment.setReason(updatedAppointment.getReason());
		}
		else {
			throw new AppointmentInPastException("Appointment is completed");
		}
//		appointment.setDoctor(updatedAppointment.getDoctor());
//		appointment.setPatient(updatedAppointment.getPatient());
//		appointment.setPrescription(updatedAppointment.getPrescription());
//		appointment.setBill(updatedAppointment.getBill());

		return appointmentRepository.save(appointment);
	}

	public Appointment updateAppointmentStatus(Long appointmentId) {
		Appointment appointment = findAppointmentById(appointmentId);
		appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
		return appointmentRepository.save(appointment);
	}
	
	
	//This is my business logic
	
//	public boolean doctorAvailability (Appointment appointment) {
// 		
// 	        Long doctorId = appointment.getDoctor().getId();
//	        LocalTime time = appointment.getAppointmentTime();
//	        LocalDate localDate = appointment.getAppointmentDate();
//	        
//	        List<Appointment> appointments = getAllAppointments();
// 	        for(Appointment oldAppointment: appointments) {
//	        	if(oldAppointment.getDoctor().getId() == doctorId && oldAppointment.getAppointmentDate().equals(localDate)
//	        			&& oldAppointment.getAppointmentTime().equals(time)) {
//	        		return false;
//	        		 
//	        	}
//	        }
//	        
//		 return true;
//	}
}
