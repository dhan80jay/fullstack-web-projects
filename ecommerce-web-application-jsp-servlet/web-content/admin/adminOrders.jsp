<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List,com.model.Order"%>
<%@ include file="admin-check.jsp"%>

<html>
<head>
<link rel="stylesheet" href="../css/admin-common.css">
</head>	
<body>

	<h2>Orders</h2>

	<table>
		<tr>
			<th>ID</th>
			<th>User</th>
			<th>Total</th>
 			<th>Status</th>
			<th>Action</th>
		</tr>

		<%
		List<Order> orders = (List<Order>) request.getAttribute("orders");
		for (Order o : orders) {
		%>
		<tr>
			<td><%=o.getId()%></td>
			<td><%=o.getUserName()%></td>
			<td>₹<%=o.getTotalAmount()%></td>
			 
			<td class="status-<%=o.getStatus()%>">
				<form action="update-order-status" method="post">
					<input type="hidden" name="orderId" value="<%=o.getId()%>">
					<select name="status">
						<option>PLACED</option>
						<option>SHIPPED</option>
						<option>DELIVERED</option>
						<option>CANCELLED</option>
					</select>
					<button>Update</button>
				</form>
			</td>


		</tr>

		<%}%>

	</table>
</body>
</html>
