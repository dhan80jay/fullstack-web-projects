package com.dhananjay.hospitalmanagement.service;
 import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dhananjay.hospitalmanagement.enums.AppointmentStatus;
import com.dhananjay.hospitalmanagement.exceptions.AppointmentNotCompletedException;
import com.dhananjay.hospitalmanagement.exceptions.PrescriptionExistException;
import com.dhananjay.hospitalmanagement.model.Appointment;
import com.dhananjay.hospitalmanagement.model.Medicine;
import com.dhananjay.hospitalmanagement.model.Prescription;
import com.dhananjay.hospitalmanagement.repository.MedicineRepository;
import com.dhananjay.hospitalmanagement.repository.PrescriptionRepository;

import jakarta.transaction.Transactional;

@Service
public class PrescriptionService {

	PrescriptionRepository prescriptionRepository;
	AppointmentService appointmentService;
	MedicineRepository medicineRepository;
	
	
	public PrescriptionService(PrescriptionRepository prescriptionRepository, AppointmentService appointmentService,
			MedicineRepository medicineRepository) {
		super();
		this.prescriptionRepository = prescriptionRepository;
		this.appointmentService = appointmentService;
		this.medicineRepository = medicineRepository;
	}

	//Create Prescription
	public Prescription createPrescription (Prescription prescrition) {
		Appointment appointment = prescrition.getAppointment();
 		Appointment originalAppointment= appointmentService.findAppointmentById(appointment.getId());
		
 		boolean isExist = prescriptionRepository.existsByAppointmentId(appointment.getId());
 
 		if(isExist)
 			 throw new PrescriptionExistException("Prescription already exists");
  		if(originalAppointment.getAppointmentStatus() == AppointmentStatus.COMPLETED) {
	    prescrition.setAppointment(originalAppointment);
 	    if(prescrition.getMedicine() == null || prescrition.getMedicine().isEmpty()) 
	    	throw new NoSuchElementException("Medicine Not Provided ");
 	    for(Medicine medicine: prescrition.getMedicine()) {
	    	medicine.setPrescription(prescrition);
 	    } 
	    
	    	
		return	prescriptionRepository.save(prescrition);
		}
		else {
			throw new AppointmentNotCompletedException("Prescription cannot be created because the appointment is not completed yet.");
		}
	}
	
	//Get All Prescription
	public List<Prescription> getAllPrescription (){
		return prescriptionRepository.findAll();
	}
	
	//Get Prescription by Id
	public Prescription getPrescriptionById(Long id) {
		Prescription prescription = prescriptionRepository.findById(id).orElse(null);
		if(prescription != null) {
			return prescription;
		}else {
			throw new NoSuchElementException("Prescription Not Found for Id "+id);
		}
 	}
	
	//Update Prescription 
	public Prescription updatePrescription (Prescription updatedPrescription,Long id) {
		Prescription prescription = getPrescriptionById(id);
 			
		
			if(updatedPrescription.getAppointment() == null || updatedPrescription.getAppointment().getId() == null) {
				throw new NoSuchElementException("Appointment not found with id: "+id);
			}
			Long apId = updatedPrescription.getAppointment().getId();
			Appointment appointment = appointmentService.findAppointmentById(apId);

			prescription.setAppointment(appointment);
			prescription.setDiagnosis(updatedPrescription.getDiagnosis());
			
			if(updatedPrescription.getMedicine() != null) {
				prescription.getMedicine().clear();
				
				for(Medicine medicine: updatedPrescription.getMedicine()) {
					medicine.setPrescription(prescription);
					prescription.getMedicine().add(medicine);
				}
			}
   			prescription.setNotes(updatedPrescription.getNotes());
			prescription.setPrescriptionDate(updatedPrescription.getPrescriptionDate());
   			return prescriptionRepository.save(prescription);
   	}
	
	//Delete Prescription
	@Transactional
	public void deletePrescription(Long id) {
  	    medicineRepository.deleteByPrescriptionId(id);
 	    prescriptionRepository.deletePrescriptionById(id);
	}	
}
