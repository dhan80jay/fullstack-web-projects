<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.model.CartItem"%>

<!DOCTYPE html>
<html>
<head>
<title>Your Cart</title>
<link rel="stylesheet" href="css/cart.css">
</head>
<body>

	<div class="cart-container">
		<h2>Your Cart</h2>

		<table class="cart-table">
			<tr>
				<th>Image</th>
				<th>Product</th>
				<th>Price</th>
				<th>Quantity</th>
				<th>Total</th>
				<th>Action</th>
			</tr>

			<%
    List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");

    if (cartItems != null && !cartItems.isEmpty()) {
        for (CartItem item : cartItems) {
%>
			<tr>
				<td><img src="images/<%= item.getImage() %>"
					class="product-img"></td>

				<td><%= item.getProductName() %></td>

				<td>₹ <%= item.getPrice() %></td>

				<!-- QUANTITY COLUMN -->
				<td>
					<div class="qty-wrapper">

<%
String qtyError = request.getParameter("qtyError");
String errorCartId = request.getParameter("errorCartId");

boolean showPopup =
    qtyError != null &&
    errorCartId != null &&
    errorCartId.equals(String.valueOf(item.getCartId()));
%>

<% if (showPopup) { %>
    <div class="qty-popup">
        ❌ <%= "stock".equals(qtyError)
              ? "Out of stock"
              : "Maximum allowed quantity reached" %>
    </div>
<% } %>


						<div class="qty-box">

							<!-- DECREASE -->
							<form action="updatecart" method="post">
								<input type="hidden" name="cartId"
									value="<%= item.getCartId() %>"> <input type="hidden"
									name="action" value="decrease">
								<button type="submit" class="qty-btn">−</button>
							</form>

							<!-- INPUT (SET QUANTITY) -->
							<form action="updatecart" method="post">
								<input type="hidden" name="cartId"
									value="<%= item.getCartId() %>"> <input type="hidden"
									name="action" value="set"> <input type="number"
									name="quantity" id="qty-input"
									value="<%= item.getQuantity() %>" min="1" max="20"
 								required>

							</form>

							<!-- INCREASE -->
							<form action="updatecart" method="post">
								<input type="hidden" name="cartId"
									value="<%= item.getCartId() %>"> <input type="hidden"
									name="action" value="increase">
								<button type="submit" class="qty-btn">+</button>
							</form>

						</div>
					</div>
				</td>

				<!-- TOTAL -->
				<td>₹ <%= item.getPrice() * item.getQuantity() %></td>

				<!-- REMOVE -->
				<td>
					<form action="removefromcart" method="post">
						<input type="hidden" name="cartId" value="<%= item.getCartId() %>">
						<input type="hidden" name="productId"
							value="<%= item.getProductId() %>">
						<button type="submit" class="remove-btn">Remove</button>
					</form>
				</td>
			</tr>
			<%
        }
    } else {
%>
			<tr>
				<td colspan="6" class="empty-cart">Your cart is empty</td>
			</tr>
			<%
    }
%>
		</table>

		<%
    double grandTotal = 0;
    if (cartItems != null) {
        for (CartItem item : cartItems) {
            grandTotal += item.getPrice() * item.getQuantity();
        }
    }
%>

		<div class="cart-summary">
			<h3>Cart Summary</h3>
			<p>
				<strong>Grand Total:</strong> ₹
				<%= grandTotal %></p>
		</div>

		<form action="emptycart" method="post">
			<button type="submit" class="empty-btn">Empty Cart</button>
		</form>

		<a href="paymentOption.jsp" class="checkout-btn">Proceed to
			Checkout</a>

		<div class="cart-actions">
			<a href="products" class="btn">Continue Shopping</a>
		</div>
	</div>

</body>
</html>
