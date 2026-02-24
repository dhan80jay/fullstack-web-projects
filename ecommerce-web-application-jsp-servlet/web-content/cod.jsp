	<%@ page language="java" contentType="text/html; charset=UTF-8"
	    pageEncoding="UTF-8"%>
	<!DOCTYPE html>
	<html>
	<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="css/cod.css">
	
	</head>
	<body>
		<div class=address-box>
	    <!-- CASH ON DELIVERY -->
	    <form action="placeOrder" method="post">
	        <h3>Cash on Delivery</h3>
	        <p class="cod-text">Pay when the product is delivered 🚚</p>
	        <jsp:include page="address.jsp" />
	        
	        <button type="submit" class="btn-cod">Place Order</button>
	    </form>
	</div>
	</body>
	</html>