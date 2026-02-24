package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PaymentDao {

	public PaymentStatus makePayment(String cardNumber, String cardName, String expiryDate, String cvv, double amount) {

		if (!isValidCard(cardNumber, cardName, expiryDate, cvv)) {
			return PaymentStatus.INVALID_CARD;
		}

		if (!hasSufficientBalance(cardNumber, amount)) {
			return PaymentStatus.INSUFFICIENT_BALANCE;
		}

		deductBalance(cardNumber, amount);
		return PaymentStatus.SUCCESS;
	}
	
	private boolean isValidCard(String cardNumber, String cardName, String expiryDate, String cvv) {

		String sql = "SELECT * FROM payments " + "WHERE card_number = ?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, cardNumber);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				System.out.println("✅ CARD NUMBER FOUND");
				System.out.println("DB name   = " + rs.getString("card_name"));
				System.out.println("DB expiry = " + rs.getString("expiry_date"));
				System.out.println("DB cvv    = " + rs.getString("cvv"));
				return true;
			} else {
				System.out.println("❌ CARD NUMBER NOT FOUND");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean hasSufficientBalance(String cardNumber, double amount) {

		String sql = "SELECT balance FROM payments WHERE card_number = ?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, cardNumber);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				double balance = rs.getDouble("balance");
				return balance >= amount;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void deductBalance(String cardNumber, double amount) {

		String sql = """
				    UPDATE payments
				    SET balance = balance - ?
				    WHERE card_number = ?
				""";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setDouble(1, amount);
			ps.setString(2, cardNumber);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public double getBalance(String cardNumber) {

		String sql = "SELECT balance FROM payments WHERE card_number = ?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, cardNumber);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getDouble("balance");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	

 }
