package com.controller;

import java.io.IOException;

import com.dao.OrderDao;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/updateOrderStatus")
public class UpdateOrderStatusServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        int orderId = Integer.parseInt(req.getParameter("orderId"));
        String status = req.getParameter("status");

        OrderDao dao = new OrderDao();
        dao.updateOrderStatus(orderId, status);

        res.sendRedirect(req.getContextPath() + "/orders");
    }
}
