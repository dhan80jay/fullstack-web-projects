<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.model.User" %>
<%@ include file="admin-check.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>View All Users</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminUsers.css">
</head>
<body>

<div class="admin-container">
    <h2>All Users</h2>
	
    <table class="users-table">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Role</th>
 
            </tr>
        </thead>
        <tbody>

        <%
            List<User> users = (List<User>) request.getAttribute("users");
            if (users != null && !users.isEmpty()) {
                for (User u : users) {
        %>
            <tr>
                <td><%= u.getId() %></td>
                <td><%= u.getName() %></td>
                <td><%= u.getEmail() %></td>
                <td><%= u.getRole() %></td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="5" class="no-data">No users found</td>
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
