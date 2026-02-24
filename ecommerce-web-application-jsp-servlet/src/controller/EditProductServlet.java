package com.controller;

import java.io.IOException;

import com.dao.ProductDao;
import com.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/edit-product")
public class EditProductServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        ProductDao dao = new ProductDao();
        Product product = dao.getProductById(id);

        req.setAttribute("product", product);
        req.getRequestDispatcher("admin/updateProduct.jsp").forward(req, res);
    }
}
