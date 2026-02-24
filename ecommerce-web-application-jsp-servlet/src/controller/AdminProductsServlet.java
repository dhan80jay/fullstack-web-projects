package com.controller;

import java.io.IOException;
import java.util.List;

import com.dao.ProductDao;
import com.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/adminProduct")
public class AdminProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	System.out.println("Inside AdminProduct Servlet");
        ProductDao dao = new ProductDao();

        List<Product> products = dao.getAllProducts();
        
 
        req.setAttribute("products", products);

        req.getRequestDispatcher("/admin/adminProduct.jsp")
           .forward(req, resp);
    }
}
