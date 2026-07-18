package com.dhananjay.hospitalmanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dhananjay.hospitalmanagement.enums.AppointmentStatus;
import com.dhananjay.hospitalmanagement.model.Appointment;
import com.dhananjay.hospitalmanagement.model.Doctor;
import com.dhananjay.hospitalmanagement.model.Prescription;
import com.dhananjay.hospitalmanagement.security.Users;
import com.dhananjay.hospitalmanagement.service.AppointmentService;
import com.dhananjay.hospitalmanagement.service.DoctorService;
import com.dhananjay.hospitalmanagement.service.PrescriptionService;
import com.dhananjay.hospitalmanagement.service.UsersService;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

	
	
	DoctorService doctorService;
	AppointmentService appointmentService;
	PrescriptionService prescriptionService;
	
  	public DoctorController(DoctorService doctorService, AppointmentService appointmentService,
			PrescriptionService prescriptionService) {
 		this.doctorService = doctorService;
		this.appointmentService = appointmentService;
		this.prescriptionService = prescriptionService;
 	}

 	
	@PostMapping("/register")
	public ResponseEntity<Doctor> addDoctor (@RequestBody Doctor doctor){
		doctorService.addDoctor(doctor);
 		return new ResponseEntity<Doctor>(doctor,HttpStatus.CREATED);
	}
	
	@PostMapping("/bulk")
	public ResponseEntity<List<Doctor>> addMultipleDoctors(@RequestBody List<Doctor> doctor){
		List<Doctor> savedDoctors = doctorService.addMultipleDoctor(doctor);
		return new ResponseEntity<List<Doctor>>(savedDoctors,HttpStatus.CREATED);
	}
	
	@GetMapping
	public List<Doctor> getAllDoctors (){
		return doctorService.getAllDoctors();
	}
	
	@GetMapping("/{id}")
	public Doctor getDoctorById	(@PathVariable Long id){
		return doctorService.findById(id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteDoctor(@PathVariable Long id){
		doctorService.deleteDoctor(id);
		return ResponseEntity.ok("Doctor deleted Successfully !");
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id,@RequestBody Doctor doctor){
		Doctor updatedDoctor = doctorService.updateDoctor(doctor, id);
		return ResponseEntity.ok(updatedDoctor);
	}
	
	@GetMapping("/appointments/{id}")
	public ResponseEntity <List<Appointment>> getAppointmentByDoctorId (@PathVariable Long id){
		List<Appointment> appointments= doctorService.getAppointmentByDoctorId(id);
		return new ResponseEntity<List<Appointment>>(appointments,HttpStatus.OK);
	}
	
	@PostMapping("/prescriptions")
	public ResponseEntity<Prescription> createPrescription (@RequestBody Prescription prescrition){
 		prescriptionService.createPrescription(prescrition);
		return new ResponseEntity<Prescription>(prescrition, HttpStatus.CREATED);
	}
	
	@PutMapping("/{doctorId}/appointments/{appointmentId}/{status}")
	public ResponseEntity<Appointment> updateAppointmentStatus (@PathVariable Long appointmentId,@PathVariable Long doctorId,@RequestParam AppointmentStatus status){
		Appointment appointment = doctorService.updateAppointmentStatus(doctorId, appointmentId, status);
		return new ResponseEntity<Appointment>(appointment,HttpStatus.OK);
	}
	 
 }
