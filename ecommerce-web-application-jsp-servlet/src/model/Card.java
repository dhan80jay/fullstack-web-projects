package com.model;

public class Card {
    private int cardId;
    private String cardNumber;
    private String cardName;
    private String expiryDate;
    private String cvv;
    private double balance;

    public Card() {
		 
	}

	public Card(int cardId, String cardNumber, String cardName, String expiryDate, String cvv, double balance) {
		
		this.cardId = cardId;
		this.cardNumber = cardNumber;
		this.cardName = cardName;
		this.expiryDate = expiryDate;
		this.cvv = cvv;
		this.balance = balance;
	}

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Card [cardId=" + cardId + ", cardNumber=" + cardNumber + ", cardName=" + cardName + ", expiryDate="
				+ expiryDate + ", cvv=" + cvv + ", balance=" + balance + "]";
	}
    
	
    
}
