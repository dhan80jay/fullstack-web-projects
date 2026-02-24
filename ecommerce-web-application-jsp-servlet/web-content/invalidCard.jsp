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
    <h2>❌ Invalid Card</h2>
    <p>The card details you entered are incorrect.</p>

    <ul>
        <li>Check card number</li>
        <li>Verify expiry date</li>
        <li>Enter correct CVV</li>
    </ul>

    <a href="payment" class="btn">Try Again</a>
    <a href="cart" class="link">Back to Cart</a>
</div>

</body>
</html>
