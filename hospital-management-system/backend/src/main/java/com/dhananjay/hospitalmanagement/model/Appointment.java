package com.dhananjay.hospitalmanagement.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;

import com.dhananjay.hospitalmanagement.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="appointment")
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus appointmentStatus; // BOOKED, COMPLETED, CANCELLED
    private String reason;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @JsonProperty(access = Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn (name = "doctor_id")
    private Doctor doctor;
   
    @JsonProperty(access = Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn (name = "patient_id")
    private Patient patient;

    @JsonIgnore
    @OneToOne(mappedBy = "appointment",cascade = CascadeType.ALL)
    private Prescription prescription;
    
    @JsonIgnore
    @OneToOne(mappedBy = "appointment",cascade = CascadeType.REMOVE)
    private Bill bill;
    
    public Appointment() {}
    
    public Appointment(Long id, LocalDate appointmentDate, LocalTime appointmentTime,
			AppointmentStatus appointmentStatus, String reason, LocalDateTime createdAt, Doctor doctor,
			Patient patient) {
		super();
		this.id = id;
		this.appointmentDate = appointmentDate;
		this.appointmentTime = appointmentTime;
		this.appointmentStatus = appointmentStatus;
		this.reason = reason;
		this.createdAt = createdAt;
		this.doctor = doctor;
		this.patient = patient;
	}



	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public Prescription getPrescription() {
		return prescription;
	}

	public void setPrescription(Prescription prescription) {
		this.prescription = prescription;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

 
    public AppointmentStatus getAppointmentStatus() {
		return appointmentStatus;
	}

	public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}

	public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

	@Override
	public String toString() {
		return "Appointment [id=" + id + ", appointmentDate=" + appointmentDate + ", appointmentTime=" + appointmentTime
				+ ", appointmentStatus=" + appointmentStatus + ", reason=" + reason + ", createdAt=" + createdAt + "]";
	}
 
   
    
}