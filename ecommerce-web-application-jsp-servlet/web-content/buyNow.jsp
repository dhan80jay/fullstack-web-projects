<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.model.Product"%>
<%
Product p = (Product) request.getAttribute("product");
int qty = (request.getAttribute("qty") != null) ? (int) request.getAttribute("qty") : 1;
%>

<!DOCTYPE html>
<html>
<head>
<title>Buy Now</title>
<link rel="stylesheet" href="css/buy-now.css">

</head>
<body>	

	<div class="buy-container">

		<h2>⚡ Buy Now</h2>

		<div class="product-box">
			<img src="<%=request.getContextPath()%>/images/<%=p.getImage()%>"
				alt="product">

			<div class="details">
				<h3><%=p.getName()%></h3>
				<p class="price">
					₹
					<%=p.getPrice()%></p>

				<p class="price">
					₹
					<%=p.getPrice()%>
					(per item)
				</p>

				<p class="total-price">
					Total: ₹ <strong><%=request.getAttribute("totalAmount")%></strong>
				</p>

				<!-- Quantity control -->
				<%
				if (Boolean.TRUE.equals(request.getAttribute("showPopup"))) {
					long animKey = System.currentTimeMillis(); // 👈 unique every request
				%>
				<div class="qty-toast" style="animation-name: toastPop-<%=animKey%>">
					<span class="icon">✕</span> <span>Out of stock</span>
				</div>


				<%
				}
				%>
				<%
				Integer maxAllowed = (Integer) request.getAttribute("maxAllowed");
				if (maxAllowed == null)
					maxAllowed = 1;
				%>
				<div class="qty-box">
					<form action="buynow" method="get" class="qty-box">
						<input type="hidden" name="pid" value="<%=p.getId()%>">

						<button type="submit" name="action" value="dec" class="qty-btn">−</button>

						<input type="number" name="qty"
							class="qty-input <%=qty >= maxAllowed ? "limit-hit" : ""%>"
							value="<%=qty%>" min="1" max="<%=maxAllowed%>" required>

						<button type="submit" name="action" value="inc" class="qty-btn">+</button>
					</form>
				</div>
				<form action="paymentOption.jsp" method="get">
					<button class="btn-proceed">Proceed to Checkout</button>
				</form>
				<a href="<%=request.getContextPath()%>/products"
					class="floating-back"> <span class="text">Back</span>
				</a>

			</div>

		</div>
 	</div>
</body>
<script>
function showQtyToast() {
    const toast = document.getElementById("qtyToast");

    // 🔥 reset animation so it can replay
    toast.classList.remove("show");
    void toast.offsetWidth;   // force reflow (magic line)

    toast.classList.add("show");
}
</script>


</html>
