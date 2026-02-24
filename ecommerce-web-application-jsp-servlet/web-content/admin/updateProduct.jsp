<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.model.Product" %>
<%@ include file="admin-check.jsp" %>

<html>
<head>
<title>Update Product</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/addProduct.css">
</head>
<body>

<%
    Product p = (Product) request.getAttribute("product");
%>

<div class="admin-card">
    <h2>Update Product</h2>

<form action="<%= request.getContextPath() %>/update-product" method="post">

        <input type="hidden" name="id" value="<%=p.getId()%>">

        <div class="form-group">
            <label>Product Name</label>
            <input type="text" name="name" value="<%=p.getName()%>" required>
        </div>

        <div class="form-group">
            <label>Price</label>
            <input type="number" name="price" value="<%=p.getPrice()%>" required>
        </div>

        <div class="form-group">
            <label>Quantity</label>
            <input type="number" name="quantity" value="<%=p.getQuantity()%>" required>
        </div>
   		<div class="form-group">
            <label>Description</label>
            <input type="text" name="description" value="<%=p.getDescription()%>" required>
        </div>
        <button class="btn-submit">Update Product</button>
            <a href="adminProduct" class="back-link">← Back to Products</a>
        
    </form>

 </div>

</body>
</html>
