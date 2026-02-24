<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Payment</title>
<link rel="stylesheet" href="css/payment.css">
</head>
<body>

<div class="payment-box">
    <h2>💳 Payment Options</h2>

    <!-- ERROR MESSAGE -->
    <%
        String error = request.getParameter("error");
        if ("INVALID_CARD".equals(error)) {
    %>
        <p class="error-msg">❌ Invalid card details</p>
    <%
        } else if ("INSUFFICIENT_BALANCE".equals(error)) {
    %>
        <p class="error-msg">❌ Insufficient balance</p>
    <%
        }
    %>

    <!-- CARD PAYMENT -->
    <form action="payment" method="post" class="card-form">
        <h3>Card Payment</h3>
   		<input type="hidden" name="paymentMode" value="CARD">
        <input type="text" name="cardNumber" placeholder="Card Number" required>
        <input type="text" name="cardHolder" placeholder="Card Holder Name" required>
        <input type="password" name="cvv" placeholder="CVV" required>
        <input type="month" name="expiry" required>
	 <!-- 📦 ADDRESS (INSIDE FORM) -->
        <jsp:include page="address.jsp" />
        <button type="submit" class="btn pay">Pay Now</button>
    </form>		    
</div>

</body>
</html>
