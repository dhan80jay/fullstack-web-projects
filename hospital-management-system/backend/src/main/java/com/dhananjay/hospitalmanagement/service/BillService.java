package com.dhananjay.hospitalmanagement.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.dhananjay.hospitalmanagement.enums.AppointmentStatus;
import com.dhananjay.hospitalmanagement.exceptions.AppointmentNotCompletedException;
import com.dhananjay.hospitalmanagement.exceptions.BillAlreadyExistsException;
import com.dhananjay.hospitalmanagement.exceptions.BillNotFoundException;
import com.dhananjay.hospitalmanagement.model.Appointment;
import com.dhananjay.hospitalmanagement.model.Bill;
import com.dhananjay.hospitalmanagement.repository.BillRepository;

import jakarta.transaction.Transactional;

@Service
public class BillService {
	BillRepository billRepository;
 	AppointmentService appointmentService;
 
 	PatientService patientService;
 	
 	public BillService(
 	        BillRepository billRepository,
 	        AppointmentService appointmentService,
 	        PatientService patientService) {

 	    super();
 	    this.billRepository = billRepository;
 	    this.appointmentService = appointmentService;
 	    this.patientService = patientService;
 	}

	//Create Bill
 	public Bill createBill(Bill bill){
  		Long appointmentId = bill.getAppointment().getId();
 		Appointment originalAppointment = appointmentService.findAppointmentById(appointmentId);
 	 	bill.setAppointment(originalAppointment);
 	 	
 	 	if (billRepository.existsByAppointmentId(appointmentId)) {
 	 	    throw new BillAlreadyExistsException(
 	 	        "Bill already exists for this appointment"
 	 	    );
 	 	}
 		if(originalAppointment.getAppointmentStatus() == AppointmentStatus.COMPLETED) {
 	 		double totalAmount = bill.getConsultationFee()+bill.getMedicineCharges()+bill.getOtherCharges();
 	 		bill.setTotalAmount(totalAmount);

 	 		billRepository.save(bill);
 		}
 		else {
 			throw new AppointmentNotCompletedException("Appointment not completed yet cannot create bill");
 		}
  		
 		return bill;
 	}
 	
 	public List<Bill> getBillsByPatient(String username) {
 	    return billRepository.findByAppointment_Patient_User_Username(username);
 	}
 	
 	//Get all bills
 	public List<Bill> getAllBills (){
 		 return billRepository.findAll();
 	}
 	
 	public Map<Long, Appointment> getAppointmentsForBills() {

 	    List<Bill> bills = billRepository.findAll();

 	    Map<Long, Appointment> billAppointments = new HashMap<>();

 	    for (Bill bill : bills) {

 	        if (bill.getAppointment() != null) {

 	            billAppointments.put(
 	                bill.getId(),
 	                bill.getAppointment()
 	            );

 	        }
 	    }

 	    return billAppointments;
 	}
 	//Get bill by id
 	public Bill getBillById(Long id){
 		Bill bill = billRepository.findById(id).orElse(null);
 		if(bill != null) {
 			return bill;
 		}
 		else {
 			throw new NoSuchElementException("Bill not found with id "+id);
 		}
  	}
 	
 	//Update bill
 	public Bill updateBill(Bill bill,Long id){
 		Bill originalBill = getBillById(id);
 		
	 	double totalAmount = bill.getConsultationFee()+bill.getMedicineCharges()+bill.getOtherCharges();
 		Long appointmentId = bill.getAppointment().getId();
 		Appointment appointment = appointmentService.findAppointmentById(appointmentId);
  	
 		originalBill.setAppointment(appointment);
  		originalBill.setConsultationFee(bill.getConsultationFee());
 		originalBill.setMedicineCharges(bill.getMedicineCharges());
  		originalBill.setOtherCharges(bill.getOtherCharges());
 		originalBill.setPaymentStatus(bill.getPaymentStatus());
 		originalBill.setTotalAmount(totalAmount);
 		
 		return billRepository.save(originalBill);
 	}
 	
 	
 	
 	//Delete bill
 	@Transactional
 	public void deleteBill (Long id){
  		
 		Bill bill = getBillById(id);
 	
  		billRepository.delete(bill);;
  	}
 	
 	
 	// Get Bill By appointment id
 	
 	public Bill getBillByAppointmentId (Long appointmentId,Long patientId) {
 		Bill bill = billRepository.findByAppointmentIdAndAppointmentPatientId(appointmentId,patientId);
 		
 		if(bill == null)
 			throw new BillNotFoundException("Bill Not Found !");
 		else
 		return bill;
 	}
  	
}
