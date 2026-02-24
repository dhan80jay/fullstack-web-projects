<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html>
<html>
<head>
<title>Select Payment</title>
<style>
/* Reset */
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    font-family: "Segoe UI", Tahoma, sans-serif;
}

/* Page background */
body {
    min-height: 100vh;
    background: linear-gradient(135deg, #f5f7fa, #e4ecf7);
    display: flex;
    align-items: center;
    justify-content: center;
}

/* Main box */
.payment-option {
    background: #ffffff;
    width: 420px;
    padding: 30px 35px;
    border-radius: 14px;
    box-shadow: 0 15px 35px rgba(0, 0, 0, 0.12);
    text-align: center;
}

/* Heading */
.payment-option h2 {
    font-size: 24px;
    margin-bottom: 25px;
    color: #2c3e50;
}

/* Forms spacing */
.payment-option form {
    margin-bottom: 18px;
}

/* Buttons common */
.btn {
    width: 100%;
    padding: 14px;
    font-size: 16px;
    border: none;
    border-radius: 10px;
    cursor: pointer;
    font-weight: 600;
    transition: all 0.3s ease;
}

/* Card payment button */
.btn.pay {
    background: linear-gradient(135deg, #3498db, #2c81ba);
    color: white;
}

.btn.pay:hover {
    background: linear-gradient(135deg, #2c81ba, #21618c);
    transform: translateY(-2px);
}

/* COD button */
.btn.cod {
    background: linear-gradient(135deg, #27ae60, #1e8449);
    color: white;
}

.btn.cod:hover {
    background: linear-gradient(135deg, #1e8449, #145a32);
    transform: translateY(-2px);
}

/* Mobile */
@media (max-width: 480px) {
    .payment-option {
        width: 90%;
        padding: 25px;
    }

    .payment-option h2 {
        font-size: 22px;
    }
}

</style>
</head>
<body>
 
<%
    String checkoutType = (String) session.getAttribute("checkoutType");
%>

<div class="payment-option">
    <h2>💳 Choose Payment Method</h2>

    <!-- CARD PAYMENT -->
    <form action="payment" method="get">
        <input type="hidden" name="paymentMode" value="CARD">
        <button class="btn pay">💳 Card Payment</button>
    </form>

    <!-- CASH ON DELIVERY -->
    <form action="cod.jsp" method="post">
        <input type="hidden" name="paymentMode" value="COD">
        <button class="btn cod">🚚 Cash on Delivery</button>
    </form>
</div>

</body>
</html>
