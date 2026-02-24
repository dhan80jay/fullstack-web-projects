<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List,com.model.Product" %>
<%@ include file="admin-check.jsp" %>

<html>
<head>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/admin-add-product.css">
 </head>
<body>
<body>

<%
if ("1".equals(request.getParameter("updated"))) {
%>
    <p style="color:green; font-weight:bold;">
        Product updated successfully ✅
    </p>
<%
}
%>

<div class="admin-navbar">
    <h2>Admin Dashboard</h2>
     <a href="<%=request.getContextPath()%>/users" class="user">View Users</a>
    <a href="<%=request.getContextPath()%>/orders" class="order">Manage Orders</a>
    <a href="<%=request.getContextPath()%>/logout">Logout</a>   
 </div>

<div class="page-header">
    <h2>Products</h2>
    <a href="<%= request.getContextPath() %>/addproduct" class="btn">➕ Add Product</a>
</div>

<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Image</th>
            <th>Name</th>
            <th>Price</th>
            <th>Qty</th>
            <th>Action</th>
 
        </tr>
    </thead>

    <tbody>
    <%
        List<Product> list = (List<Product>) request.getAttribute("products");
        for(Product p : list){
    %>
        <tr>
            <td><%=p.getId()%></td>
            <td>
    <img src="<%=request.getContextPath()%>/images/<%=p.getImage()%>"
         class="product-img" >
			</td>
            
            <td><%=p.getName()%></td>
            <td class="price">₹<%=p.getPrice()%></td>
            <td><%=p.getQuantity()%></td>
            <td>
                <div class="actions">
                    <a href="edit-product?id=<%=p.getId()%>"
                       class="action-btn edit"
                       title="Edit">✏</a>

                    <a href="removeProduct?id=<%=p.getId()%>"
                       class="action-btn delete"
                       title="Delete">🗑</a>
                </div>
            </td>
        </tr>
    <%
        }
    %>
    </tbody>
</table>
 


</body>
</html>
