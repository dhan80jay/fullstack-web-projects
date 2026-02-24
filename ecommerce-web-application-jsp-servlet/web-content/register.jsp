<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Create Account</title>
    <link rel="stylesheet" href="css/register.css">
</head>
<body>

<div class="auth-container">
    <h2>Create Account</h2>

    <!-- Error message -->
    <%
        String error = request.getParameter("error");
        if ("exists".equals(error)) {
    %>
        <p class="error">Email already registered. Please login.</p>
    <%
        }
    %>

    <!-- Register form -->
    <form action="register" method="post">

        <input type="text" name="name"
               placeholder="Full Name"
               required>

        <input type="email" name="email"
               placeholder="Email Address"
               required>

        <input type="password" name="password"
               placeholder="Password"
               minlength="6"
               required>

        <button type="submit">Register</button>
    </form>

    <p class="switch-link">
        Already have an account?
        <a href="login.jsp">Login here</a>
    </p>
</div>

</body>
</html>
