package com.controller;

import java.io.IOException;
import java.util.List;

import com.dao.CartDao;
import com.model.CartItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cart")
public class CartServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	 System.out.println("Inside cart servlet");
	HttpSession session = req.getSession(false);
	if (session == null || session.getAttribute("userId") == null) {
	    res.sendRedirect("login.jsp");
	    return;
	}
		
     int userId = (int) session.getAttribute("userId");

		
 	
 	
	//fetch from DB
	CartDao cart = new CartDao();
	List <CartItem> cartItems= cart.getCartItemsByUserId(userId	);
	
	req.setAttribute("cartItems", cartItems);
	
    req.getRequestDispatcher("cart.jsp").forward(req, res);

	System.out.println("Inside cartServlet");
   }
}
