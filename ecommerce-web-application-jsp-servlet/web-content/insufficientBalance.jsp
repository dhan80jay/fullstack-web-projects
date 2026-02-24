<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Payment Failed</title>
    <link rel="stylesheet" href="css/payment-error.css">
</head>
<body>

<div class="error-container">
    <h2>⚠ Insufficient Balance</h2>
    <p>Your account does not have enough balance to complete this payment.</p>

    <p class="amount-note">
        Please use another card or reduce cart amount.
    </p>

    <a href="payment" class="btn">Try Another Card</a>
    <a href="cart" class="link">Back to Cart</a>
</div>

</body>
</html>
