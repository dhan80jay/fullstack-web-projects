package com.dhananjay.hospitalmanagement.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="prescription")
public class Prescription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String diagnosis;
    private String notes;
    private LocalDate prescriptionDate;
    
    @JsonProperty(access = Access.WRITE_ONLY)
    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @JsonProperty(access = Access.WRITE_ONLY)
    @OneToMany(mappedBy="prescription",cascade = CascadeType.REMOVE)
    private List<Medicine> medicine=new ArrayList<Medicine>();
    
    public Prescription() {}
    
  
	public Prescription(Long id, String diagnosis, String notes, LocalDate prescriptionDate,
			Appointment appointment, List<Medicine> medicine) {
		super();
		this.id = id;
		this.diagnosis = diagnosis;
 		this.notes = notes;
		this.prescriptionDate = prescriptionDate;
		this.appointment = appointment;
		this.medicine = medicine;
	}


	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
     
 

	public List<Medicine> getMedicine() {
		return medicine;
	}


	public void setMedicine(List<Medicine> medicine) {
		this.medicine = medicine;
	}


	public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(LocalDate prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }


	@Override
	public String toString() {
		return "Prescription [id=" + id + ", diagnosis=" + diagnosis + ", notes=" + notes + ", prescriptionDate="
				+ prescriptionDate + "]";
	}


 
     
}