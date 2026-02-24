<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="java.util.List"%>
<%@ page import="com.model.Order"%>
<%@ include file="admin-check.jsp"%>

<html>
<head>
<title>Manage Orders</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/orders.css">
</head>
<body>

	<div class="admin-container">
		<h2>Customer Orders</h2>

		<%
		if ("1".equals(request.getParameter("updated"))) {
		%>
		<p class="success">Order status updated successfully ✅</p>
		<%
		}
		%>
		<%
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
		%>

		<table class="orders-table">
			<thead>
				<tr>
					<th>Order ID</th>
					<th>User</th>
					<th>Total</th>
					<th>Payment</th>
					<th>Status</th>
					<th>Date</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>

				<%
				List<Order> orders = (List<Order>) request.getAttribute("orders");
				if (orders != null && !orders.isEmpty()) {
					for (Order o : orders) {
				%>
				<tr>
					<td>#<%=o.getId()%></td>
					<td><%=o.getUserName()%></td>
					<td>₹ <%=o.getTotalAmount()%></td>
					<td>
						<form method="post"
							action="<%=request.getContextPath()%>/updatePaymentStatus"
							class="payment-form">

							<input type="hidden" name="orderId" value="<%=o.getId()%>">

							<select name="paymentStatus"
								class="payment-select <%=(o.getPaymentStatus() != null ? o.getPaymentStatus().toLowerCase() : "pending")%>">

								<option value="PENDING"
									<%="PENDING".equals(o.getPaymentStatus()) ? "selected" : ""%>>
									PENDING</option>

								<option value="PAID"
									<%="PAID".equals(o.getPaymentStatus()) ? "selected" : ""%>>
									PAID</option>

								<option value="FAILED"
									<%="FAILED".equals(o.getPaymentStatus()) ? "selected" : ""%>>
									FAILED</option>

							</select>

							<button class="btn-payment">Update</button>
						</form>
					</td>

					<td><span class="status <%=o.getStatus().toLowerCase()%>">
							<%=o.getStatus()%>
					</span></td>
					<td class="col-date"><%=o.getOrderDate().format(fmt)%></td>


					<td>
						<form method="post"
							action="<%=request.getContextPath()%>/admin/updateOrderStatus">
							<input type="hidden" name="orderId" value="<%=o.getId()%>">
							<select name="status">
								<option>PENDING</option>
								<option>CONFIRMED</option>
								<option>SHIPPED</option>
								<option>Cancled</option>
							</select>
							<button>Update</button>
						</form>
					</td>
				</tr>
				<%
				}
				} else {
				%>
				<tr>
					<td colspan="6">No orders found</td>
				</tr>
				<%
				}
				%>

			</tbody>
		</table>
		<a href="adminProduct" class="back-link">Back to Products</a>

	</div>

</body>
</html>
