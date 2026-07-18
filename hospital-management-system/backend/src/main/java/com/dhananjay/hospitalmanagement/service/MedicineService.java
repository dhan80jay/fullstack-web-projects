package com.dhananjay.hospitalmanagement.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.dhananjay.hospitalmanagement.model.Medicine;
import com.dhananjay.hospitalmanagement.repository.MedicineRepository;

import jakarta.transaction.Transactional;

@Service
public class MedicineService {
	MedicineRepository medicineRepository;
	
	
 	public MedicineService(MedicineRepository medicineRepository) {
		super();
		this.medicineRepository = medicineRepository;
	}

	//Add medicine
	public Medicine addMedicine (Medicine medicine) {
		return medicineRepository.save(medicine);
	}
	
	//Get medicines
	public List<Medicine> getAllMedicines(){
		return medicineRepository.findAll();
	}
	
	//Get medicine by id
	public Medicine getMedicineById(Long id) {
		Medicine medicine = medicineRepository.findById(id).orElse(null);
		if(medicine != null)
			return medicine;
		else
			throw new NoSuchElementException("Medicine not found with id "+id);
	}
	
	//Update medicine 
	public Medicine updateMedicine (Medicine updatedMedicine,Long id) {
		Medicine medicine = getMedicineById(id);
		medicine.setDosage(updatedMedicine.getDosage());
		medicine.setDuration(updatedMedicine.getDuration());
		medicine.setFrequency(updatedMedicine.getFrequency());
		medicine.setName(updatedMedicine.getName());
		medicine.setNotes(updatedMedicine.getNotes());
		medicine.setPrescription(updatedMedicine.getPrescription());
		
		return medicineRepository.save(medicine);

 	}
	
	//Delete Medicine
	public void deleteMedicine(Long id) {
		Medicine medicine = getMedicineById(id);
 		medicineRepository.delete(medicine);
	}
	 
}
