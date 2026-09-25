package com.dhananjay.hospitalmanagement.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dhananjay.hospitalmanagement.exceptions.PrescriptionExistException;
import com.dhananjay.hospitalmanagement.model.Patient;
import com.dhananjay.hospitalmanagement.model.Prescription;
import com.dhananjay.hospitalmanagement.repository.PatientRepository;
import com.dhananjay.hospitalmanagement.repository.PrescriptionRepository;
import com.dhananjay.hospitalmanagement.security.Users;

@Service
public class PatientService {

	PatientRepository patientRepository;
	PrescriptionRepository prescriptionRepository;
	UsersService userService;
	PasswordEncoder passwordEncoder;
	
	public PatientService(PatientRepository patientRepository,PrescriptionRepository prescriptionRepository
			,UsersService userService,PasswordEncoder passwordEncoder) {
 		this.patientRepository = patientRepository;
 		this.prescriptionRepository = prescriptionRepository;
 		this.userService = userService;
 		this.passwordEncoder = passwordEncoder;
	}
	
	//Add patient
	public Patient addPatient(Patient patient) {
		System.out.println("Inside addpatient patientService");
		patient.getUser().setEmail(patient.getEmail());
 		userService.registerPatient(patient.getUser());
 		return patientRepository.save(patient);
	}
	
	//Add multiple patients
	public List<Patient> addMultiplePatient (List<Patient> patients){
		return patientRepository.saveAll(patients);
	}
	
 	//Get all patients
	public  List<Patient> getAllPatients (){
 		return patientRepository.findAll();
	}
	
	//Find patient by username
	public Patient getPatientByUsername(String userName) {
		 Patient patient = patientRepository.findByUser_Username(userName);
		 if(patient == null) {
			 throw new NoSuchElementException("Patient Not found !");
		 }
		 return patient;
	}
	
	//Find patient by id
	public Patient findPatientById (Long id) {
		Patient patient = patientRepository.findById(id).orElse(null);
		if(patient == null)
			throw new NoSuchElementException("Patient not found with id: "+id);
 			return patient;
 	}
	
	//Delete patient by id
	public void deletePatientById(Long id) {
		Patient patient = findPatientById(id);
		patientRepository.delete(patient);
	}
	
	//Update patient profile
	public Patient updatePatientProfile(Patient updatedPatient, Long id) {

	    Patient patient = patientRepository.findById(id)
	            .orElseThrow(() ->
	                new NoSuchElementException("Patient not found with id " + id)
	            );

	    patient.setBloodGroup(updatedPatient.getBloodGroup());
	    patient.setGender(updatedPatient.getGender());
	    patient.setDateOfBirth(updatedPatient.getDateOfBirth());
	    patient.setPhoneNumber(updatedPatient.getPhoneNumber());

	    return patientRepository.save(patient);
	}
	
	//Update patient 
	public Patient updatePatient (Patient updatedPatient,Long id) {
		
		Patient patient = findPatientById(id);
		 patient.setFirstName(updatedPatient.getFirstName());
		 patient.setLastName(updatedPatient.getLastName());
		 patient.setGender(updatedPatient.getGender());
		 patient.setBloodGroup(updatedPatient.getBloodGroup());
		 patient.setEmail(updatedPatient.getEmail());
		 patient.setAddress(updatedPatient.getAddress());
		 patient.setAppointments(updatedPatient.getAppointments());
		 patient.setDateOfBirth(updatedPatient.getDateOfBirth());
		 patient.setPhoneNumber(updatedPatient.getPhoneNumber());
		 Users user = updatedPatient.getUser();
		 Users user1 = userService.updatePatientUser(user,user.getId());
		 user.setEmail(user.getEmail());
		 
		 patient.setUser(user1);
		 return patientRepository.save(patient);
	}
	
	//View Prescription By Appointment Id
	public Prescription getPrescriptionByAppointmentId(Long patientId,Long appointmentId) {
		Prescription prescription = prescriptionRepository.findByAppointmentIdAndAppointmentPatientId(patientId,appointmentId);
		if(prescription != null)
			return prescription;
		else
			throw new PrescriptionExistException("Prescription Not Found");
	}
	
}
