	package com.controller;
	
	import java.io.IOException;
	import java.util.List;
	
	import com.dao.OrderDao;
	import com.model.Order;
	import com.model.User;
	
	import jakarta.servlet.ServletException;
	import jakarta.servlet.annotation.WebServlet;
	import jakarta.servlet.http.HttpServlet;
	import jakarta.servlet.http.HttpServletRequest;
	import jakarta.servlet.http.HttpServletResponse;
	import jakarta.servlet.http.HttpSession;
	
	@WebServlet("/myOrders")
	public class MyOrdersServlet extends HttpServlet {
	
	    protected void doGet(HttpServletRequest req, HttpServletResponse res)
	            throws ServletException, IOException {
	
	        HttpSession session = req.getSession(false);
	
	        // 🔐 Safety check
	        if (session == null || session.getAttribute("user") == null) {
	            res.sendRedirect("login.jsp");
	            return;
	        }
	        
	        // ✅ Get User object
	        User user = (User) session.getAttribute("user");
	        int userId = user.getId();
	        String userName = user.getName();
	
	        OrderDao orderDao = new OrderDao();
	        List<Order> orders = orderDao.getOrdersByUserId(userId);
	
	        req.setAttribute("orders", orders);
	        req.setAttribute("userName", userName);
	
	        req.getRequestDispatcher("my-orders.jsp").forward(req, res);
	    }
	}
