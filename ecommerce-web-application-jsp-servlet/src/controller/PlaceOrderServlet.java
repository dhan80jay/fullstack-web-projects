package com.controller;
import java.io.IOException;
import java.util.List;

import com.dao.OrderDao;
import com.dao.ProductDao;
import com.model.CartItem;
import com.model.Product;
import com.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/placeOrder")
public class PlaceOrderServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        String userName = user.getName();

        // 📍 Address details
        String fullName = req.getParameter("fullName");
        String mobNo    = req.getParameter("mobile");
        String address  = req.getParameter("address");

        if (fullName == null || fullName.trim().isEmpty()
                || mobNo == null || !mobNo.matches("\\d{10}")
                || address == null || address.trim().isEmpty()) {

            res.sendRedirect("invalidAddress.jsp");
            return;
        }

        // ⚡ BUY NOW data from session
        Integer pid = (Integer) session.getAttribute("buyNowPid");
        Integer qty = (Integer) session.getAttribute("buyNowQty");
        Double totalAmount = (Double) session.getAttribute("buyNowTotal");

        if (pid == null || qty == null || totalAmount == null || totalAmount <= 0) {
            res.sendRedirect("products.jsp");
            return;
        }

        OrderDao orderDao = new OrderDao();
        ProductDao productDao = new ProductDao();

        // 🧾 Create order (COD)
        int orderId = orderDao.createOrder(
                userId,
                totalAmount,
                userName,
                "COD",
                "PENDING"
        );

        // 📦 Create order item
        Product p = productDao.getProductById(pid);

        CartItem item = new CartItem();
        item.setProductId(pid);
        item.setProductName(p.getName());
        item.setQuantity(qty);
        item.setPrice(p.getPrice() * qty);

        orderDao.addOrderItems(orderId, List.of(item));

        // 📉 Reduce stock
        productDao.reduceStockAfterBuyNow(pid, qty);

        // 🧹 Clear Buy Now session
        session.removeAttribute("buyNowPid");
        session.removeAttribute("buyNowQty");
        session.removeAttribute("buyNowTotal");
        session.removeAttribute("checkoutType");

        session.setAttribute("orderId", orderId);

        res.sendRedirect("placeOrder.jsp");
    }
}
