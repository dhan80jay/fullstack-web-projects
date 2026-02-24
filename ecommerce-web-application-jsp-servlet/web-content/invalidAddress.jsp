<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Invalid Address</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/invalidAddress.css">
</head>
<body>

<div class="error-container">
    <div class="error-card">
        <div class="icon">📍</div>

        <h2>Invalid Delivery Address</h2>

        <p>
            The address you entered is incomplete or invalid.<br>
            Please provide a valid delivery address to continue.
        </p>

        <div class="actions">
            <a href="payment.jsp" class="btn primary">Update Address</a>
            <a href="cart.jsp" class="btn secondary">Back to Cart</a>
        </div>
    </div>
</div>

</body>
</html>
