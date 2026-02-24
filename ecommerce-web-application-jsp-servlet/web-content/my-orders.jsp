<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.model.Order" %>
<%@ page import="com.model.User" %>


<html>
<head>
<title>My Orders</title>
<link rel="stylesheet" href="css/my-orders.css">
</head>
<body>

<h2 class="title">📦 My Orders</h2>
 <%
User loggedUser = (User) session.getAttribute("user");
%>
<h3 class="user">
    Welcome, <%= loggedUser != null ? loggedUser.getName() : "Guest" %>
</h3>
 
<table>
    <tr>
    	<th>Order id</th>
        <th>Product Name</th>
        <th>Price</th>
        <th>Order Status</th>
        <th>Payment Type</th>
    </tr>

<%
    List<Order> orders = (List<Order>) request.getAttribute("orders");
    if (orders != null && !orders.isEmpty()) {
        for (Order o : orders) {
%>
    <tr>
    	<td><%=o.getId() %></td>
<td>
    <%= o.getProductNames() != null ? o.getProductNames() : "—" %>
</td>
        <td>₹ <%= o.getTotalAmount() %></td>
        <td><%= o.getStatus() %></td>
      <%
    String paymentMethod = o.getPaymentMethod();
    String paymentClass = (paymentMethod != null) ? paymentMethod.toLowerCase() : "pending";
    String paymentText  = (paymentMethod != null) ? paymentMethod : "PENDING";
    
    
%>
<td>
    <span class="order-type <%= paymentClass %>">
        <%= paymentText %>
    </span>
</td>
    </tr>
<%
        }
    } else {
%>
    <tr>
<td colspan="5" style="text-align:center;">No orders found</td>
    </tr>
<%
    }
%>
</table>

<a href="products" class="back">← Back to Products</a>

</body>
</html>
