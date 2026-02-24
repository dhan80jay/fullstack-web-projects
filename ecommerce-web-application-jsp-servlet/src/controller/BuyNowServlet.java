package com.controller;

import java.io.IOException;

import com.dao.CartDao;
import com.dao.ProductDao;
import com.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/buynow")
public class BuyNowServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // 1️⃣ Read params
        int pid = Integer.parseInt(req.getParameter("pid"));
        String action = req.getParameter("action");

        // 2️⃣ Get product
     // 2️⃣ Get product
        ProductDao productDao = new ProductDao();
        Product product = productDao.getProductById(pid);

        if (product == null) {
            res.sendRedirect("products.jsp");
            return;
        }

        int stock = product.getQuantity();
        int maxAllowed = Math.min(20, stock);

        // 3️⃣ Quantity logic
        boolean showPopup = false;
        int qty = 1;

        String qtyParam = req.getParameter("qty");
        if (qtyParam != null) {
            try {
                qty = Integer.parseInt(qtyParam);
            } catch (Exception e) {
                qty = 1;
            }
        }

        if ("inc".equals(action)) qty++;
        if ("dec".equals(action)) qty--;

        if (qty < 1) qty = 1;

        if (qty > maxAllowed) {
            qty = maxAllowed;
            showPopup = true;
        }

        // 4️⃣ Calculate total amount
        CartDao cartDao = new CartDao();
        double totalAmount = cartDao.calculateTotal(product.getPrice(), qty);

        // 5️⃣ Send to JSP
        req.setAttribute("product", product);
        req.setAttribute("qty", qty);
        req.setAttribute("maxAllowed", maxAllowed);
        req.setAttribute("totalAmount", totalAmount);
        req.setAttribute("showPopup", showPopup);
        
        HttpSession session = req.getSession();
        session.setAttribute("buyNowPid", product.getId());
        session.setAttribute("buyNowQty", qty);
        session.setAttribute("buyNowTotal", totalAmount);
        session.setAttribute("checkoutType", "BUY_NOW");
        
        req.getRequestDispatcher("buyNow.jsp").forward(req, res);
    }
 }
