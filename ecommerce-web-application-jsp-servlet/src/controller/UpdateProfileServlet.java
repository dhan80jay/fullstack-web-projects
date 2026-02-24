package com.controller;

import java.io.IOException;

import com.dao.UserDAO;
import com.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/updateProfile")
public class UpdateProfileServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        // Read updated values
        user.setName(req.getParameter("name"));
        user.setPhone(req.getParameter("phone"));
 
        // Update DB
        UserDAO userDao = new UserDAO();
        userDao.updateUserProfile(user);

        // Update session
        session.setAttribute("user", user);

        res.sendRedirect("profile?success=1");
    }
}
