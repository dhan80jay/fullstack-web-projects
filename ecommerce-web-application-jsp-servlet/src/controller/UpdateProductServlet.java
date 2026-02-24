package com.controller;

import java.io.IOException;

import com.dao.ProductDao;
import com.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/update-product")
public class UpdateProductServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

    	System.out.println("Inside update prodcut servlet");
    	
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        double price = Double.parseDouble(req.getParameter("price"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        String description = req.getParameter("description");
        Product p = new Product();
        p.setId(id);
        p.setName(name);
        p.setPrice(price);
        p.setDescription(description);
        p.setQuantity(quantity);
        System.out.println(p);
        ProductDao dao = new ProductDao();
        boolean update=dao.updateProduct(p);
        
        if(update) {
        	res.sendRedirect("adminProduct");
        }else {
        	System.out.println("Product Not Found");
        }

    }
}
