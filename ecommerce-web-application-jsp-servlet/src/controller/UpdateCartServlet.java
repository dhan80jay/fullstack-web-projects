package com.controller;

import java.io.IOException;

import com.dao.CartDao;
import com.dao.ProductDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/updatecart")
public class UpdateCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        int cartId = Integer.parseInt(req.getParameter("cartId"));
        String action = req.getParameter("action");

        CartDao cartDao = new CartDao();
        ProductDao productDao = new ProductDao();

        // 🔹 get productId from cart
        int productId = cartDao.getProductIdByCartId(cartId);

        // 🔹 current quantities
        int cartQty  = cartDao.getCartQuantity(cartId);
        int stockQty = productDao.getProductQuantity(productId);

        // ⭐ MAX ALLOWED RULE
        int maxAllowed = Math.min(20, stockQty);

        /* =======================
           INCREASE (+) BUTTON
           ======================= */
        if ("increase".equals(action)) {

        	if (cartQty >= maxAllowed) {

        	    if (stockQty < 20) {
        	        // stock limited case
                    res.sendRedirect("cart?qtyError=stock&errorCartId=" + cartId);
        	    } else {
        	        // max 20 limit case
                    res.sendRedirect("cart?qtyError=max&errorCartId=" + cartId);
        	    }
        	    return;
        	}
            
            cartDao.increaseQuantity(cartId);
        }

        /* =======================
           DECREASE (−) BUTTON
           ======================= */
        else if ("decrease".equals(action)) {

            if (cartQty > 1) {
                cartDao.decreaseQuantity(cartId);
            }
        }

        /* =======================
           SET (INPUT VALUE)
           ======================= */
        else if ("set".equals(action)) {

            String qtyParam = req.getParameter("quantity");
            int newQty = 1;

            try {
                newQty = Integer.parseInt(qtyParam);
            } catch (Exception e) {
                newQty = 1;
            }

            // min validation
            if (newQty < 1) {
                newQty = 1;
            }

            // ⭐ maxAllowed validation
            if (cartQty >= maxAllowed) {

                if (stockQty < 20) {
                    // stock limited case
                    res.sendRedirect("cart?qtyError=stock&errorCartId=" + cartId);
                } else {
                    // max 20 limit case
                    res.sendRedirect("cart?qtyError=max&errorCartId=" + cartId);
                }
                return;
            }
            if (newQty > maxAllowed) {
                res.sendRedirect("cart?qtyError=max&errorCartId=" + cartId);
                return;
            }

            cartDao.updateQuantity(cartId, newQty);
        }

        // 🔹 refresh cart page
        res.sendRedirect("cart");
    }
}
