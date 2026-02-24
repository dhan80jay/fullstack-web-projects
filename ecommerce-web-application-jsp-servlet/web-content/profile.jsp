<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%@ page import="com.model.User" %>

<%
User u = (User) request.getAttribute("user");
%>

<html>
<head>
<title>My Profile</title>
<link rel="stylesheet" href="css/myprofile.css">
<style>
@charset "UTF-8";
/* =========================
   PROFILE PAGE
========================= */

body {
    font-family: Arial, sans-serif;
    background: #f4f6f8;
    padding: 30px;
}

/* Page title */
h2 {
    text-align: center;
    margin-bottom: 25px;
    color: #222;
}

/* Profile card */
.profile-form {
    max-width: 420px;
    margin: auto;
    background: #fff;
    padding: 25px 30px;
    border-radius: 12px;
    box-shadow: 0 8px 20px rgba(0,0,0,0.12);
}

/* Labels */
.profile-form label {
    display: block;
    margin-top: 14px;
    font-size: 14px;
    font-weight: 600;
    color: #444;
}

/* Inputs */
.profile-form input,
.profile-form textarea {
    width: 100%;
    margin-top: 6px;
    padding: 10px 12px;
    border-radius: 8px;
    border: 1px solid #ccc;
    font-size: 14px;
    transition: border-color 0.3s;
}

.profile-form input:focus,
.profile-form textarea:focus {
    border-color: #0d6efd;
    outline: none;
}

/* Disabled email */
.profile-form input[disabled] {
    background: #f1f3f5;
    cursor: not-allowed;
}

/* Address textarea */
.profile-form textarea {
    resize: none;
    height: 80px;
}

/* Update button */
.profile-form button {
    width: 100%;
    margin-top: 20px;
    padding: 12px;
    border: none;
    border-radius: 25px;
    background: #0d6efd;
    color: #fff;
    font-size: 15px;
    font-weight: bold;
    cursor: pointer;
    transition: background 0.3s;
}

.profile-form button:hover {
    background: #084298;
}

/* Back link */
.back-btn {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    gap: 6px;

    margin: 20px auto 0;
    padding: 10px 18px;

    background: #f1f3f5;
    color: #333;
    font-size: 14px;
    font-weight: 600;
    text-decoration: none;

    border-radius: 999px;
    border: 1px solid #ddd;

    width: fit-content;

    transition: 
        background 0.3s ease,
        transform 0.3s ease,
        box-shadow 0.3s ease;
}

/* Hover effect */
.back-btn:hover {
    background: #e9ecef;
    transform: translateY(-2px);
    box-shadow: 0 6px 15px rgba(0,0,0,0.15);
}

/* Active (click) */
.back-btn:active {
    transform: translateY(0);
    box-shadow: 0 3px 8px rgba(0,0,0,0.2);
}

/* =========================
   SUCCESS MESSAGE (OPTIONAL)
========================= */

.success-msg {
    text-align: center;
    margin-bottom: 15px;
    color: #28a745;
    font-weight: 600;
}

/* =========================
   RESPONSIVE
========================= */
@media (max-width: 480px) {
    body {
        padding: 20px;
    }

    .profile-form {
        padding: 20px;
    }
}



</style>
 </head>
<body>

<h2>My Profile</h2>

<form action="<%=request.getContextPath()%>/updateProfile" class="profile-form" method="post">

    <label>Name</label>
    <input type="text" name="name" value="<%= u.getName() %>" required>

    <label>Email</label>
    <input type="email" value="<%= u.getEmail() %>" disabled>

    <label>Phone</label>
    <input type="text" name="phone" value="<%= u.getPhone() %>">

 
    <button type="submit">Update Profile</button>
</form>

<a href="products" class="back-btn">Back</a>

</body>
</html>
