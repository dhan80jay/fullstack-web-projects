package com.controller;

import java.io.IOException;

import com.dao.CartDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/emptycart")
public class EmptyCartServlet extends HttpServlet {
	 
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		  HttpSession session = req.getSession(false);
	        if (session == null || session.getAttribute("userId") == null) {
	            res.sendRedirect("login.jsp");
	            return;
	       }
	        
	      int userId = (int) session.getAttribute("userId");
	      
	      CartDao cart = new CartDao();
	      cart.emptyCart(userId);
	      
	      res.sendRedirect("cart");

	}
}
