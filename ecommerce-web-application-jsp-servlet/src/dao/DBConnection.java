package com.dao;


import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL ="jdbc:mysql://localhost:3306/shopping_cart";
    private static final String USER = "root";
    private static final String PASSWORD = "8080167864";

    // Get database connection
    public static Connection getConnection() {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create and return connection
            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

