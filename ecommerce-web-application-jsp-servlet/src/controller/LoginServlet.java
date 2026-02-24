package com.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.model.User;
import com.service.UserService;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
    	System.out.println("Inside login Servlet");
        //Read form data
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // Call Service layer
        UserService userService = new UserService();
        User user = userService.login(email, password);

        // If login successful → create session
        if (user != null) {

            HttpSession session = req.getSession(true); // create session
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getName()); // ✅ ADD THIS
            session.setAttribute("role", user.getRole());
            session.setMaxInactiveInterval(30 * 60); // 30 minutes

            //  Role-based redirect
            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                res.sendRedirect("adminProduct");
            } else {
                res.sendRedirect("products");
            }

        } else {
            //  Login failed
            res.sendRedirect("login.jsp?error=1");
        }
    }
}
