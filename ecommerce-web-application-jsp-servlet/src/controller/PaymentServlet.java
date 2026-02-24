package com.controller;

import java.io.IOException;
import java.util.List;

import com.dao.CartDao;
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

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        req.getRequestDispatcher("payment.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String checkoutType = (String) session.getAttribute("checkoutType"); // CART / BUY_NOW
        String paymentMode  = req.getParameter("paymentMode");               // CARD / COD

        CartDao cartDao = new CartDao();
        ProductDao productDao = new ProductDao();
        OrderDao orderDao = new OrderDao();
        PaymentDao paymentDao = new PaymentDao();

        double totalAmount;

        /* ================= DETERMINE TOTAL ================= */
        if ("BUY_NOW".equals(checkoutType)) {
            Double buyNowTotal = (Double) session.getAttribute("buyNowTotal");
            if (buyNowTotal == null || buyNowTotal <= 0) {
                res.sendRedirect("products");
                return;
            }
            totalAmount = buyNowTotal;
        } else {
            totalAmount = cartDao.getCartTotalAmount(userId);
            if (totalAmount <= 0) {
                res.sendRedirect("cart");
                return;
            }
        }

        /* ================= ADDRESS VALIDATION ================= */
        String fullName = req.getParameter("fullName");
        String mobNo    = req.getParameter("mobile");
        String address  = req.getParameter("address");

        if (fullName == null || fullName.trim().isEmpty()
                || mobNo == null || !mobNo.matches("\\d{10}")
                || address == null || address.trim().isEmpty()) {

            res.sendRedirect("invalidAddress.jsp");
            return;
        }

        /* ================= PAYMENT ================= */
        PaymentStatus status = PaymentStatus.SUCCESS;

        if ("CARD".equals(paymentMode)) {

            String cardNumber = req.getParameter("cardNumber");
            String cardHolder = req.getParameter("cardHolder");
            String expiry     = req.getParameter("expiry");
            String cvv        = req.getParameter("cvv");

            if (cardNumber == null || cardHolder == null
                    || expiry == null || cvv == null) {

                res.sendRedirect("payment.jsp?error=missingCard");
                return;
            }

            cardNumber = cardNumber.replaceAll("\\s+", "");
            cardHolder = cardHolder.trim();

            status = paymentDao.makePayment(
                    cardNumber,
                    cardHolder,
                    expiry,
                    cvv,
                    totalAmount
            );
        }

        /* ================= PAYMENT FAILURE ================= */
        if (status != PaymentStatus.SUCCESS) {
            if (status == PaymentStatus.INVALID_CARD)
                res.sendRedirect("invalidCard.jsp");
            else if (status == PaymentStatus.INSUFFICIENT_BALANCE)
                res.sendRedirect("insufficientBalance.jsp");
            else
                res.sendRedirect("payment.jsp?error=unknown");
            return;
        }

        /* ================= CREATE ORDER ================= */
        String paymentStatus;
        if ("CARD".equals(paymentMode)) {
            paymentStatus = "PAID";
        } else { // COD
            paymentStatus = "PENDING";
        }

        int orderId = orderDao.createOrder(
                userId,
                totalAmount,
                fullName,
                paymentMode,      // CARD / COD
                paymentStatus     // PAID / PENDING
        );

        if (orderId <= 0) {
            res.sendRedirect("payment.jsp?error=order_failed");
            return;
        }

        /* ================= BUY NOW ================= */
        if ("BUY_NOW".equals(checkoutType)) {

            Integer pid = (Integer) session.getAttribute("buyNowPid");
            Integer qty = (Integer) session.getAttribute("buyNowQty");
            Product p = productDao.getProductById(pid);

            CartItem item = new CartItem();
            item.setProductId(pid);
            item.setProductName(p.getName());     // ✅ MUST
            item.setQuantity(qty);
            item.setPrice(p.getPrice() * qty);  

            orderDao.addOrderItems(orderId, List.of(item));
            productDao.reduceStockAfterBuyNow(pid, qty);

            session.removeAttribute("buyNowPid");
            session.removeAttribute("buyNowQty");
            session.removeAttribute("buyNowTotal");

        }
        /* ================= CART ================= */
        else {

            List<CartItem> cartItems = cartDao.getCartItemsByUserId(userId);
            orderDao.addOrderItems(orderId, cartItems);
            productDao.reduceStockAfterPayment(cartItems);
            cartDao.emptyCart(userId);
        }

        session.removeAttribute("checkoutType");
        res.sendRedirect("payment-success.jsp");
    }
}
