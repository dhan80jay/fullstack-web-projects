package com.dhananjay.hospitalmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.dhananjay.hospitalmanagement.service.AppointmentService;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

	AppointmentService appointmentService;

	@Autowired
	public AppointmentController(AppointmentService appointmentService) {
 		this.appointmentService = appointmentService;
	}
	
	@GetMapping
	public ResponseEntity <List<Appointment>> getAllAppointments (){
		List<Appointment> appointments= appointmentService.getAllAppointments();
		return new ResponseEntity<List<Appointment>>(appointments,HttpStatus.OK);
	}
	
	@GetMapping("/patient/{id}")
	public ResponseEntity<List<Appointment>> getAppointmentByPatientId (@PathVariable Long id) {
		  List<Appointment> appointment = appointmentService.findAppointmentsByPatientId(id);
		  return new ResponseEntity<List<Appointment>>(appointment,HttpStatus.OK);
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<Appointment> getAppointmentById (@PathVariable Long id) {
		  Appointment appointment = appointmentService.findAppointmentById(id);
		  return new ResponseEntity<Appointment>(appointment,HttpStatus.OK);
	}
	
	@GetMapping("/prescription/{prescriptionId}")
	public ResponseEntity<Appointment> getAppointmentByPrescriptionId(
	        @PathVariable Long prescriptionId) {

	    Appointment appointment =
	            appointmentService.findAppointmentByPrescriptionId(prescriptionId);

	    return new ResponseEntity<>(appointment, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
		  Appointment createdAppointment = appointmentService.createAppointment(appointment);
		  return new ResponseEntity<Appointment>(createdAppointment,HttpStatus.CREATED);
	}
	
	@PostMapping("/patient/{patientId}")
	public ResponseEntity<Appointment> createAppointmentByPatient(@RequestBody Appointment appointment,@PathVariable Long patientId) {
		  Appointment createdAppointment = appointmentService.createAppointmentByPatient(appointment,patientId);
		  return new ResponseEntity<Appointment>(createdAppointment,HttpStatus.CREATED);
	}
	
	@PutMapping("/patient/{appointmentId}")
	public ResponseEntity<Appointment> updateAppointmentStatus(@PathVariable Long appointmentId){
		Appointment appointment = appointmentService.updateAppointmentStatus(appointmentId);
		return new ResponseEntity<Appointment>(appointment,HttpStatus.OK);
		
	}
	
	@PostMapping ("/bulk")
	public ResponseEntity<List<Appointment>> createMultipleAppointment(@RequestBody List<Appointment> appointments) {
		  List<Appointment> createdAppointments = appointmentService.createMultipleAppointment(appointments);
		  return new ResponseEntity<List<Appointment>>(createdAppointments,HttpStatus.CREATED);
	}

	
	@PutMapping ("/{id}")
	public ResponseEntity<Appointment> updateAppointment (@RequestBody Appointment appointment,@PathVariable Long id){
		  Appointment updateAppointment =  appointmentService.updateAppointment(appointment, id);
		  return new ResponseEntity<>(updateAppointment,HttpStatus.OK);
 	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAppointment (@PathVariable Long id){
	      appointmentService.deleteAppointment(id);
	      return ResponseEntity.ok("Appointment Deleted Successfully !");
	}
}
