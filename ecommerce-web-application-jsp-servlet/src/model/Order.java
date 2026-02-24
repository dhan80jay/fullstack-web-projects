package com.model;

import java.time.LocalDateTime;

public class Order {

	private int id; // order_id
	private int userId; // user_id
	private String userName; // user_name
	private String productNames; // product_name
	private double totalAmount; // total_amount
	private String status; // PLACED / SHIPPED / DELIVERED
	private String paymentStatus; // PAID / PENDING / FAILED
	private String paymentMethod;
	
	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	private LocalDateTime orderDate;// order_date

	 
	public Order() {
	}

    	public Order(int id, int userId, String userName, String productNames, double totalAmount, String status,
			String paymentStatus,String paymentMethod, LocalDateTime orderDate) {

		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.productNames = productNames;
		this.totalAmount = totalAmount;
		this.status = status;
		this.paymentStatus = paymentStatus;
		this.paymentMethod =paymentMethod;
		this.orderDate = orderDate;
	}

	// ✅ Getters & Setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProductNames() {
		return this.productNames;
	}

	public void setProductNames(String productNames) {
		this.productNames = productNames;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	// ✅ Optional (for debugging)
	@Override
	public String toString() {
		return "Order [id=" + id + ", userId=" + userId + ", userName=" + userName + ", productName=" + productNames
				+ ", totalAmount=" + totalAmount + ", status=" + status + ", paymentStatus=" + paymentStatus
				+ ", orderDate=" + orderDate + "]";
	}
}
