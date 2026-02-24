package com.controller;

import java.io.IOException;

import com.dao.CartDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/removefromcart")
public class RemoveCartItemServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		  HttpSession session = req.getSession(false);
	        if (session == null || session.getAttribute("user") == null) {
	            res.sendRedirect("login.jsp");
	            return;
	        }
	        
	        String productIdStr = req.getParameter("productId");
	        if (productIdStr == null) {
	            res.sendRedirect("cart");
	            return;
	        }

	        int productId = Integer.parseInt(productIdStr);
	        int userId = (int) session.getAttribute("userId");
	        
 	        CartDao cart = new CartDao();
	        cart.removeFromCart(userId,productId);
	        
	        res.sendRedirect("cart");
	}
}
