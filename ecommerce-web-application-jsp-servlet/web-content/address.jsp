<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/payment.css">

</head>
<body>
    	<!-- 📦 DELIVERY ADDRESS -->
<div class="address-section">
    <h3>📍 Delivery Address</h3>

    <input type="text" name="fullName" placeholder="Full Name" required>

    <input type="text" name="mobile" placeholder="Mobile Number" required>

    <textarea name="address"
              placeholder="House No, Street, Area"
              required></textarea>

    <div class="address-row">
        <input type="text" name="city" placeholder="City" required>
        <input type="text" name="state" placeholder="State" required>
    </div>

    <div class="address-row">
        <input type="text" name="pincode" placeholder="Pincode" required>
    </div>
</div>

</body>
</html>