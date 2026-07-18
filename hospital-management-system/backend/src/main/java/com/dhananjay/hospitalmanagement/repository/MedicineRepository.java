package com.dhananjay.hospitalmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dhananjay.hospitalmanagement.model.Medicine;

import jakarta.transaction.Transactional;

public interface MedicineRepository extends JpaRepository<Medicine, Long>{
 
    @Modifying
    @Transactional
    @Query("DELETE FROM Medicine m WHERE m.prescription.id = :prescriptionId")
	void deleteByPrescriptionId(@Param("prescriptionId") Long prescriptionId);
}
