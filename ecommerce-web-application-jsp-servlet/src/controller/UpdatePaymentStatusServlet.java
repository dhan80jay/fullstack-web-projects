package com.controller;

import java.io.IOException;
import java.util.List;

import com.dao.OrderDao;
import com.model.Order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/updatePaymentStatus")
public class UpdatePaymentStatusServlet extends HttpServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

		int orderId = Integer.parseInt(req.getParameter("orderId"));
		String paymentStatus = req.getParameter("paymentStatus");

		OrderDao dao = new OrderDao();
		dao.updatePaymentStatus(orderId, paymentStatus);


         List<Order> orders = dao.getAllOrders();

        req.setAttribute("orders", orders);
        req.setAttribute("updated", "1");

   
        try {
			req.getRequestDispatcher("/admin/viewOrders.jsp")
			   .forward(req, res);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
