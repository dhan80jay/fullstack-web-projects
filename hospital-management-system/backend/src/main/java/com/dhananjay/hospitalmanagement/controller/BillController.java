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

import com.dhananjay.hospitalmanagement.model.Bill;
import com.dhananjay.hospitalmanagement.service.BillService;

@RestController
@RequestMapping ("/api/v1/bills")
public class BillController {
	BillService billService;

	@Autowired
	public BillController(BillService billService) {
 		this.billService = billService;
	}
	
	@PostMapping
	public ResponseEntity<Bill> createBill (@RequestBody Bill bill){
		Bill createdBill = billService.createBill(bill);
		return new ResponseEntity<Bill>(createdBill,HttpStatus.CREATED);
 	}
	
	@GetMapping
	public ResponseEntity<List<Bill>> getAllBills (){
		List<Bill> bills = billService.getAllBills();
 		return new ResponseEntity<List<Bill>>(bills,HttpStatus.OK);
	}
	
	@GetMapping ("/{id}")
	public ResponseEntity<Bill> getBillById	(@PathVariable Long id){
		Bill bill = billService.getBillById(id);
		return new ResponseEntity<Bill>(bill,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteBill (@PathVariable Long id){
	    billService.deleteBill(id);
		return new ResponseEntity<String>("Bill deleted Successfully",HttpStatus.NO_CONTENT);
 	}
 	
	@PutMapping("/{id}")
	public ResponseEntity<Bill> updateBill (@RequestBody Bill bill,@PathVariable Long id){
		Bill updatedBill = billService.updateBill(bill, id);
		return new ResponseEntity<Bill>(updatedBill,HttpStatus.OK);
	}
}
