package com.service;


import java.util.List;

import com.dao.UserDAO;
import com.model.User;

public class UserService {

    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

	    //LOGIN BUSINESS LOGIC
	    public User login(String email, String password) {
	
	        // basic validation
	        if (email == null || password == null ||
	            email.isEmpty() || password.isEmpty()) {
	            return null;
	        }
	
	        return userDAO.loginUser(email, password);
	    }

    // REGISTRATION BUSINESS LOGIC
    public boolean register(User user) {

        if (user == null) return false;
        if (user.getEmail() == null || user.getEmail().isEmpty()) return false;
        if (user.getPassword() == null || user.getPassword().length() < 6) return false;

        user.setRole("USER");
        return userDAO.registerUser(user);
    }

    // ADMIN: VIEW ALL USERS
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}
