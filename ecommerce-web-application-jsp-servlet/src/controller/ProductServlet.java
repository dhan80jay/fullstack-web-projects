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
import jakarta.servlet.http.HttpSession;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // 🔐 Session check
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        // 🔍 Read filters
        String category = req.getParameter("category");
        String search   = req.getParameter("search");
        String sort     = req.getParameter("sort");   // ✅ ADD THIS

        String minPriceStr = req.getParameter("minPrice");
        String maxPriceStr = req.getParameter("maxPrice");

        Double minPrice = null;
        Double maxPrice = null;

        try {
            if (minPriceStr != null && !minPriceStr.isEmpty())
                minPrice = Double.parseDouble(minPriceStr);

            if (maxPriceStr != null && !maxPriceStr.isEmpty())
                maxPrice = Double.parseDouble(maxPriceStr);
        } catch (Exception e) {
            // ignore invalid input
        }

        // 📦 Fetch products (UPDATED CALL)
        ProductDao dao = new ProductDao();
        List<Product> products = dao.getFilteredProducts(
                category,
                search,
                minPrice,
                maxPrice,
                sort        // ✅ PASS SORT
        );

        // 📤 Send to JSP
        req.setAttribute("products", products);
        req.setAttribute("selectedCategory", category);
        req.setAttribute("searchText", search);
        req.setAttribute("sort", sort); // optional (for active UI)

        // 📄 Forward
        req.getRequestDispatcher("products.jsp").forward(req, res);
    }
}
