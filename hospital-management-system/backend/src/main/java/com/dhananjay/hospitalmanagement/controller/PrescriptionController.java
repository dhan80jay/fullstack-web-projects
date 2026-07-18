package com.dhananjay.hospitalmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dhananjay.hospitalmanagement.model.Prescription;
import com.dhananjay.hospitalmanagement.service.PrescriptionService;

@RestController
@RequestMapping ("/api/v1/prescriptions")
public class PrescriptionController {

	PrescriptionService prescriptionService;

	@Autowired
	public PrescriptionController(PrescriptionService prescriptionService) {
 		this.prescriptionService = prescriptionService;
	}
	
	@PostMapping
	public ResponseEntity<Prescription> createPrescription (@RequestBody Prescription prescrition){
 		prescriptionService.createPrescription(prescrition);
		return new ResponseEntity<Prescription>(prescrition, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<Prescription>> getAllPrescription (){
		List <Prescription> prescriptions = prescriptionService.getAllPrescription();
		return new ResponseEntity<List<Prescription>>(prescriptions,HttpStatus.OK);
	}
	
	@GetMapping ("/{id}")
	public ResponseEntity<Prescription> getPrescriptionById	(@PathVariable Long id){
		Prescription prescription = prescriptionService.getPrescriptionById(id);
		return new ResponseEntity<Prescription>(prescription, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Prescription> updatePrescription (@RequestBody Prescription prescription , @PathVariable Long id){
		Prescription updatedPrescription = prescriptionService.updatePrescription(prescription, id);
		return new ResponseEntity<Prescription>(updatedPrescription,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePrescription (@PathVariable Long id){
	    prescriptionService.deletePrescription(id);
		return  ResponseEntity.ok("Prescription Deleted Successfully !");
	}

}
