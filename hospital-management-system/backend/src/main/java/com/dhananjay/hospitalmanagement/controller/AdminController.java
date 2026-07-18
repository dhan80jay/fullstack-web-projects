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

import com.dhananjay.hospitalmanagement.model.Appointment;
import com.dhananjay.hospitalmanagement.model.Bill;
import com.dhananjay.hospitalmanagement.model.Doctor;
import com.dhananjay.hospitalmanagement.model.Patient;
import com.dhananjay.hospitalmanagement.model.Prescription;
import com.dhananjay.hospitalmanagement.security.Users;
import com.dhananjay.hospitalmanagement.service.AppointmentService;
import com.dhananjay.hospitalmanagement.service.BillService;
import com.dhananjay.hospitalmanagement.service.DoctorService;
import com.dhananjay.hospitalmanagement.service.PatientService;
import com.dhananjay.hospitalmanagement.service.PrescriptionService;
import com.dhananjay.hospitalmanagement.service.UsersService;

@RestController
@RequestMapping ("/admin")
public class AdminController {
	PatientService patientService;
	DoctorService doctorService;
	AppointmentService appointmentService;
	BillService billService;
	UsersService userService;
	PrescriptionService prescriptionService;
	@Autowired
	public AdminController(PatientService patientService, DoctorService doctorService,
			AppointmentService appointmentService,PrescriptionService prescriptionService, BillService billService,UsersService userService) {
 		this.patientService = patientService;
		this.doctorService = doctorService;
		this.appointmentService = appointmentService;
		this.billService = billService;
		this.userService = userService;
		this.prescriptionService = prescriptionService;
	}
	
	//Register Admin
	
	@PostMapping("/register")
	public ResponseEntity<Users> registerAdmin(@RequestBody Users user) {

	    Users savedUser = userService.registerAdmin(user);

	    return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}
	
	//View Users
	@GetMapping("/users")
	public List<Users> getAllUsers(){
		return userService.getAllUsers();
	}
	
	//Manage Doctors

	@PostMapping ("/doctors")
	public ResponseEntity<Doctor> addDoctor (@RequestBody Doctor doctor){
		doctorService.addDoctor(doctor);
		return new ResponseEntity<Doctor>(doctor,HttpStatus.CREATED);
	}
	
	
	
	@PostMapping("/doctors/bulk")
	public ResponseEntity<List<Doctor>> addMultipleDoctors(@RequestBody List<Doctor> doctor){
		List<Doctor> savedDoctors = doctorService.addMultipleDoctor(doctor);
		return new ResponseEntity<List<Doctor>>(savedDoctors,HttpStatus.CREATED);
	}
	
	@GetMapping("/doctors")
	public List<Doctor> getAllDoctors (){
		return doctorService.getAllDoctors();
	}
	
	@GetMapping("/doctors/{id}")
	public Doctor getDoctorById	(@PathVariable Long id){
		return doctorService.findById(id);
	}
	
	@DeleteMapping("/doctors/{id}")
	public ResponseEntity<String> deleteDoctor(@PathVariable Long id){
		doctorService.deleteDoctor(id);
		return ResponseEntity.ok("Doctor deleted Successfully !");
	}
	
	@PutMapping("/doctors/{id}")
	public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id,@RequestBody Doctor doctor){
		Doctor updatedDoctor = doctorService.updateDoctor(doctor, id);
		return ResponseEntity.ok(updatedDoctor);
	}

	//Manage Patients

	@PostMapping("/patients")
	public ResponseEntity<Patient> addPatient (@RequestBody Patient patient) {
		 patientService.addPatient(patient);
		 return new ResponseEntity<>(patient, HttpStatus.CREATED);
	}
	
	@PostMapping("/patients/bulk")
	public ResponseEntity<List<Patient>> addMultiplePatient (@RequestBody List<Patient> patients){
		List<Patient> addedPatients = patientService.addMultiplePatient(patients);
		return ResponseEntity.ok(addedPatients);
	}
	
	@GetMapping("/patients")
	public ResponseEntity <List<Patient>>getAllPatients () {
		List<Patient> patients = patientService.getAllPatients();
		return ResponseEntity.ok(patients);
	}
	
	@GetMapping("/patients/{id}")
	public ResponseEntity<Patient> findPatientById (@PathVariable Long id) {
		Patient patient = patientService.findPatientById(id);
		return ResponseEntity.ok(patient);
	}
	 
	@DeleteMapping("/patients/{id}")
	public ResponseEntity<String> deletePatientById (@PathVariable Long id) {
		patientService.deletePatientById(id);
		return ResponseEntity.ok("Patient Deleted Successfully !");
	}
	
	@PutMapping("/patients/{id}")
	public ResponseEntity<String> updatePatient (@RequestBody Patient patient,@PathVariable Long id){
		patientService.updatePatient(patient, id);
		return ResponseEntity.ok("Patient Updated Successfully !");
	}
	
	//Appointments

	@GetMapping("/appointments")
	public ResponseEntity <List<Appointment>> getAllAppointments (){
		List<Appointment> appointments= appointmentService.getAllAppointments();
		return new ResponseEntity<List<Appointment>>(appointments,HttpStatus.OK);
	}

	@PostMapping("/appointments")
	public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
		  Appointment createdAppointment = appointmentService.createAppointment(appointment);
		  return new ResponseEntity<Appointment>(createdAppointment,HttpStatus.CREATED);
	}

	@PutMapping ("/appointments/{id}")
	public ResponseEntity<Appointment> updateAppointment (@RequestBody Appointment appointment,@PathVariable Long id){
		  Appointment updatedPatient =  appointmentService.updateAppointment(appointment, id);
		  return new ResponseEntity<>(updatedPatient,HttpStatus.OK);
 	}
	
	@DeleteMapping("/appointments/{id}")
	public ResponseEntity<String> deleteAppointment (@PathVariable Long id){
	      appointmentService.deleteAppointment(id);
	      return ResponseEntity.ok("Appointment Deleted Successfully !");
	}
	//Manage bills
	@PostMapping ("/bills")
	public ResponseEntity<Bill> createBill (@RequestBody Bill bill){
		Bill createdBill = billService.createBill(bill);
		return new ResponseEntity<Bill>(createdBill,HttpStatus.CREATED);
 	}
	
	@GetMapping("/bills")
	public ResponseEntity<List<Bill>> getAllBills (){
		List<Bill> bills = billService.getAllBills();
 		return new ResponseEntity<List<Bill>>(bills,HttpStatus.OK);
	}
	
	@GetMapping ("/bills/{id}")
	public ResponseEntity<Bill> getBillById	(@PathVariable Long id){
		Bill bill = billService.getBillById(id);
		return new ResponseEntity<Bill>(bill,HttpStatus.OK);
	}
	
	@DeleteMapping("/bills/{id}")
	public ResponseEntity<String> deleteBill (@PathVariable Long id){
	    billService.deleteBill(id);
		return new ResponseEntity<String>("Bill deleted Successfully",HttpStatus.NO_CONTENT);
 	}
 	
	@PutMapping("/bills/{id}")
	public ResponseEntity<Bill> updateBill (@RequestBody Bill bill,@PathVariable Long id){
		Bill updatedBill = billService.updateBill(bill, id);
		return new ResponseEntity<Bill>(updatedBill,HttpStatus.OK);
	}
 
	//Prescriptions
	@PostMapping("/prescriptions")
	public ResponseEntity<Prescription> createPrescription (@RequestBody Prescription prescrition){
 		prescriptionService.createPrescription(prescrition);
		return new ResponseEntity<Prescription>(prescrition, HttpStatus.CREATED);
	}
	
	@GetMapping("/prescriptions")
	public ResponseEntity<List<Prescription>> getAllPrescription (){
		List <Prescription> prescriptions = prescriptionService.getAllPrescription();
		return new ResponseEntity<List<Prescription>>(prescriptions,HttpStatus.OK);
	}
	
	@GetMapping ("/prescriptions/{id}")
	public ResponseEntity<Prescription> getPrescriptionById	(@PathVariable Long id){
		Prescription prescription = prescriptionService.getPrescriptionById(id);
		return new ResponseEntity<Prescription>(prescription, HttpStatus.OK);
	}
	
	@PutMapping("/prescriptions/{id}")
	public ResponseEntity<Prescription> updatePrescription (@RequestBody Prescription prescription , @PathVariable Long id){
		Prescription updatedPrescription = prescriptionService.updatePrescription(prescription, id);
		return new ResponseEntity<Prescription>(updatedPrescription,HttpStatus.OK);
	}
	
	@DeleteMapping("/prescriptions/{id}")
	public ResponseEntity<String> deletePrescription (@PathVariable Long id){
	    prescriptionService.deletePrescription(id);
		return  ResponseEntity.ok("Prescription Deleted Successfully !");
	}
}
