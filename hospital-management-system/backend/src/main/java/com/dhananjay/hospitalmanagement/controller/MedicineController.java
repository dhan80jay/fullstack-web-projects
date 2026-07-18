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

import com.dhananjay.hospitalmanagement.model.Medicine;
import com.dhananjay.hospitalmanagement.service.MedicineService;

@RestController
@RequestMapping("/api/v1/medicines")
public class MedicineController {
	MedicineService medicineService;

	@Autowired
	public MedicineController(MedicineService medicineService) {
 		this.medicineService = medicineService;
	}
	
	@PostMapping
	public ResponseEntity<Medicine> addMedicine (@RequestBody Medicine medicine){
		medicineService.addMedicine(medicine);
		return new ResponseEntity<Medicine>(medicine, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<Medicine>> getAllMedicines (){
		List<Medicine> medicines = medicineService.getAllMedicines();
		return new ResponseEntity<List<Medicine>>(medicines,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Medicine> getMedicine (@PathVariable Long id){
		Medicine medicine = medicineService.getMedicineById(id);
		return new ResponseEntity<Medicine>(medicine,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMedicine (@PathVariable Long id){
		medicineService.deleteMedicine(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Medicine> updateMedicine(@RequestBody Medicine medicine,@PathVariable Long id){
		Medicine updatedMedicine = medicineService.updateMedicine(medicine, id);
		return new ResponseEntity<Medicine>(updatedMedicine,HttpStatus.OK);
	}
}
