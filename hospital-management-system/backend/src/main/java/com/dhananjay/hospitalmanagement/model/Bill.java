package com.dhananjay.hospitalmanagement.model;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.dhananjay.hospitalmanagement.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bill")
public class Bill {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double consultationFee;
    private double medicineCharges;
    private double otherCharges;
    private double totalAmount;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;
   
    @CreationTimestamp
    private LocalDate billingDate;

    @JsonProperty(access = Access.WRITE_ONLY)
    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    public Bill() {}
    
    public Bill(Long id, double consultationFee, double medicineCharges, double otherCharges, double totalAmount,
			PaymentStatus paymentStatus, LocalDate billingDate,Appointment appointment) {
		super();
		this.id = id;
		this.consultationFee = consultationFee;
		this.medicineCharges = medicineCharges;
		this.otherCharges = otherCharges;
		this.totalAmount = totalAmount;
		this.paymentStatus = paymentStatus;
		this.billingDate = billingDate;
 		this.appointment = appointment;
	}



	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public double getMedicineCharges() {
        return medicineCharges;
    }

    public void setMedicineCharges(double medicineCharges) {
        this.medicineCharges = medicineCharges;
    }

    public double getOtherCharges() {
        return otherCharges;
    }

    public void setOtherCharges(double otherCharges) {
        this.otherCharges = otherCharges;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDate getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(LocalDate billingDate) {
        this.billingDate = billingDate;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

	@Override
	public String toString() {
		return "Bill [id=" + id + ", consultationFee=" + consultationFee + ", medicineCharges=" + medicineCharges
				+ ", otherCharges=" + otherCharges + ", totalAmount=" + totalAmount + ", paymentStatus=" + paymentStatus
				+ ", billingDate=" + billingDate + "]";
	}

	
}

