<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="admin-check.jsp" %>
<html>
<head>
<link rel="stylesheet" href="css/addProduct.css">
 </head>
<body>


<form method="post"
      action="<%= request.getContextPath() %>/addproduct"
      enctype="multipart/form-data">
<h2>Add Product</h2>

    <input type="text" name="name" placeholder="Product Name" required>

    <input type="number" name="price" placeholder="Price" step="0.01" required>

    <input type="text" name="category" placeholder="Category">

    <input type="number" name="quantity" placeholder="Quantity" min="0">

    <!-- IMAGE UPLOAD -->
    <input type="file" name="image" accept="image/*" required>

    <textarea name="description" placeholder="Description"></textarea>

    <button type="submit">Save</button>
</form>
</body>
</html>
