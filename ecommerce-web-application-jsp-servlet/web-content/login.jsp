<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
 <div class="card">
  <h2>Login</h2>
<form action="login" method="post">
 <input type="email" name="email" placeholder="Email" required>
<input type="password" name="password" placeholder="Password" required> 
<button>Login</button>
</form>
<p class="register-link">
    New user?
    <a href="register">Create an account</a>
</p>
</div>
</body>
</html>