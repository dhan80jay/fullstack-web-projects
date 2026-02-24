package com.controller;

import java.io.IOException;
import java.util.List;

import com.dao.UserDAO;
import com.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/users")
public class AdminUserServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        UserDAO dao = new UserDAO();
        List<User> list = dao.getAllUsers();

        req.setAttribute("users", list);
        req.getRequestDispatcher("/admin/viewUsers.jsp")
           .forward(req, res);
    }
}

