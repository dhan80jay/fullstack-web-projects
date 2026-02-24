package com.controller;

import java.io.IOException;

import com.dao.CartDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/addtocart")
public class AddToCartServlet  extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		  HttpSession session = req.getSession(false);
	        if (session == null || session.getAttribute("userId") == null) {
	            res.sendRedirect("login.jsp");
	            return;
	       }
		    

	       //Add product into cart
	      int productId = Integer.parseInt(req.getParameter(("pid")));
 	      int userId = (int) session.getAttribute("userId");
	      int quantity = 1;
	      CartDao cart = new CartDao();
	      cart.addToCart(userId,productId,quantity); 
	      res.sendRedirect("products?added=true");
	      System.out.println("Product added");
	}
}
