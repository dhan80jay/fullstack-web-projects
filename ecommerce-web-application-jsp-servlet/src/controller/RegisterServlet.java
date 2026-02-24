package com.controller;

import java.io.IOException;

import com.model.User;
import com.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet{
 
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		 
		 String name = req.getParameter("name");
		 String email = req.getParameter("email");
		 String password = req.getParameter("password");

		 User user = new User();
		 user.setName(name);
		 user.setEmail(email);
		 user.setPassword(password);
		 
		 UserService service= new UserService();
		 boolean registered=service.register(user);
		 
		 if(registered) {
			 req.getRequestDispatcher("login.jsp").forward(req, res);
		 }
		 else {
			 req.getRequestDispatcher("register.jsp").forward(req, res);
		 }

	}
	
}
