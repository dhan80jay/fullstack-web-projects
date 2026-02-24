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

@WebServlet("/orders")
public class AdminOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        OrderDao dao = new OrderDao();
        List<Order> orders = dao.getAllOrders();

        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/admin/viewOrders.jsp")
           .forward(req, res);
    }
}
