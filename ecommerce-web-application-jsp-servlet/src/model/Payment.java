package com.model;

import java.time.LocalDateTime;

public class Payment {
    private int id;
    private int orderId;
    private String cardNumber;    
    private double amount;
    private double balance;       
    private String status;       
    private LocalDateTime paymentDate;
    
    public Payment() {
		 
	}

	public Payment(int id, int orderId, String cardNumber, double amount, double balance, String status,
			LocalDateTime paymentDate) {
		 
		this.id = id;
		this.orderId = orderId;
		this.cardNumber = cardNumber;
		this.amount = amount;
		this.balance = balance;
		this.status = status;
		this.paymentDate = paymentDate;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", orderId=" + orderId + ", cardNumber=" + cardNumber + ", amount=" + amount
				+ ", balance=" + balance + ", status=" + status + ", paymentDate=" + paymentDate + "]";
	}
    
    
}
