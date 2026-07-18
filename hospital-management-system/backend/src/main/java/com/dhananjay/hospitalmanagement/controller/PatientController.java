package com.dhananjay.hospitalmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dhananjay.hospitalmanagement.model.Appointment;
import com.dhananjay.hospitalmanagement.model.Bill;
import com.dhananjay.hospitalmanagement.model.Patient;
import com.dhananjay.hospitalmanagement.model.Prescription;
import com.dhananjay.hospitalmanagement.service.AppointmentService;
import com.dhananjay.hospitalmanagement.service.BillService;
import com.dhananjay.hospitalmanagement.service.PatientService;
import com.dhananjay.hospitalmanagement.service.PrescriptionService;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {
	
	PatientService patientService;
	AppointmentService appointmentService;
	BillService billService;
	PrescriptionService prescriptionService;
	
	@Autowired
 	public PatientController(PatientService patientService, AppointmentService appointmentService,
			BillService billService, PrescriptionService prescriptionService) {
 		this.patientService = patientService;
		this.appointmentService = appointmentService;
		this.billService = billService;
		this.prescriptionService = prescriptionService;
	}

	@PostMapping("/register")
	public ResponseEntity<Patient> addPatient (@RequestBody Patient patient) {
		 patientService.addPatient(patient);
		 return new ResponseEntity<Patient>(patient,HttpStatus.CREATED);
	}
	
	@PostMapping("/bulk")
	public ResponseEntity<List<Patient>> addMultiplePatient (@RequestBody List<Patient> patients){
		List<Patient> addedPatients = patientService.addMultiplePatient(patients);
		return ResponseEntity.ok(addedPatients);
	}
	
	@GetMapping
	public ResponseEntity <List<Patient>>getAllPatients () {
 		List<Patient> patients = patientService.getAllPatients();
		return ResponseEntity.ok(patients);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Patient> findPatientById (@PathVariable Long id) {
		Patient patient = patientService.findPatientById(id);
		return ResponseEntity.ok(patient);
	}
	 
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePatientById (@PathVariable Long id) {
		patientService.deletePatientById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updatePatient (@RequestBody Patient patient,@PathVariable Long id){
		patientService.updatePatient(patient, id);
		return ResponseEntity.ok("Patient Updated Successfully !");
	}
	
	//Create Appointment
	@PostMapping("/appointments")
	public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
		  Appointment createdAppointment = appointmentService.createAppointment(appointment);
		  return new ResponseEntity<Appointment>(createdAppointment,HttpStatus.CREATED);
	}
	
	//View Bill
	@GetMapping ("/{patientId}/bills/{appointmentId}")
	public ResponseEntity<Bill> getBillByAppointmentId(@PathVariable Long patientId,@PathVariable Long appointmentId){
		Bill bill = billService.getBillByAppointmentId(patientId,appointmentId);
		return new ResponseEntity<Bill>(bill,HttpStatus.OK);
	}

	//View Prescription
	@GetMapping("/{patientId}/prescriptions/{prescriptionId}")
	public ResponseEntity<Prescription> getPrescriptionByAppointmentId(@PathVariable Long patientId,@PathVariable Long prescriptionId){
		Prescription prescription = patientService.getPrescriptionByAppointmentId(patientId,prescriptionId);
		return new ResponseEntity<Prescription>(prescription,HttpStatus.OK);
	}
	
	
}
