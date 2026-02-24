package com.controller;

import java.io.IOException;
import java.util.Collections;

import com.dao.OrderDao;
import com.dao.PaymentDao;
import com.dao.PaymentStatus;
import com.dao.ProductDao;
import com.model.CartItem;
import com.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/buyNowPayment")
public class BuyNowPaymentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        // 🔹 Buy Now data from session
        Integer pid = (Integer) session.getAttribute("buyNowPid");
        System.out.println("BuyNow PID = " + pid);

        Integer qty = (Integer) session.getAttribute("buyNowQty");
        Double totalAmount = (Double) session.getAttribute("buyNowTotal");

        if (pid == null || qty == null || totalAmount == null) {
            res.sendRedirect("products.jsp");
            return;
        }

        // 🔹 Payment mode
        String paymentMode = req.getParameter("paymentMode"); // CARD / COD
        String paymentMethod = paymentMode;
        String paymentStatus = "COD".equals(paymentMode) ? "PENDING" : "SUCCESS";


        // 🔹 Address
        String fullName = req.getParameter("fullName");
        String mobNo    = req.getParameter("mobile");
        String address  = req.getParameter("address");

        if (fullName == null || fullName.trim().isEmpty()
                || mobNo == null || !mobNo.matches("\\d{10}")
                || address == null || address.trim().isEmpty()) {

            res.sendRedirect("invalidAddress.jsp");
            return;
        }

        PaymentDao paymentDao = new PaymentDao();
        PaymentStatus status = PaymentStatus.SUCCESS;

        // ================= 💳 CARD PAYMENT =================
        if ("CARD".equals(paymentMode)) {

            String cardNumber = req.getParameter("cardNumber").replaceAll("\\s+", "");
            String cardHolder = req.getParameter("cardHolder").trim();
            String expiry     = req.getParameter("expiry");
            String cvv        = req.getParameter("cvv");

            status = paymentDao.makePayment(
                    cardNumber,
                    cardHolder,
                    expiry,
                    cvv,
                    totalAmount
            );
        }

        // ================= 🚚 COD =================
        else if ("COD".equals(paymentMode)) {
            status = PaymentStatus.SUCCESS; // ❌ no balance deduction
        }

        // ================= RESULT =================
        if (status == PaymentStatus.SUCCESS) {

            OrderDao orderDao = new OrderDao();

            int orderId = orderDao.createOrder(
                    userId,
                    totalAmount,
                    fullName,
                    paymentMethod,
                    paymentStatus
            );

            // single product → order_items
            ProductDao productDao = new ProductDao();
            Product   p= productDao.getProductById(pid);
            CartItem item = new CartItem();
            item.setProductId(pid);
            item.setProductName(p.getName());   // ✅ THIS WAS MISSING
            item.setQuantity(qty);
            item.setPrice(p.getPrice() * qty);

            orderDao.addOrderItems(orderId, Collections.singletonList(item));

            // reduce stock
             productDao.reduceStockAfterBuyNow(pid, qty);

            // cleanup session
            session.removeAttribute("buyNowPid");
            session.removeAttribute("buyNowQty");
            session.removeAttribute("buyNowTotal");

            res.sendRedirect("payment-success.jsp");

        } else if (status == PaymentStatus.INVALID_CARD) {

            res.sendRedirect("invalidCard.jsp");

        } else if (status == PaymentStatus.INSUFFICIENT_BALANCE) {

            res.sendRedirect("insufficientBalance.jsp");

        } else {
            res.sendRedirect("payment.jsp?error=unknown");
        }
    }
}
