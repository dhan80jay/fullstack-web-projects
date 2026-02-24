package com.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import com.dao.ProductDao;
import com.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/addproduct")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,  // 1MB
    maxFileSize = 1024 * 1024 * 5,     // 5MB
    maxRequestSize = 1024 * 1024 * 10  // 10MB
)
public class AddProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // show add product form
        req.getRequestDispatcher("/admin/addProduct.jsp")
           .forward(req, res);
    }
	
	
	
	
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	
    	System.out.println("Inside addproduct servlet");
    	
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        // 🧾 Normal fields
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        double price = Double.parseDouble(req.getParameter("price"));
        String category = req.getParameter("category");
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        // 📷 Image upload
        Part imagePart = req.getPart("image");
        String fileName = Paths.get(imagePart.getSubmittedFileName())
                               .getFileName()
                               .toString();

        // 📁 Save location
        String uploadPath = getServletContext().getRealPath("") 
                            + File.separator + "images";

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        // Save file
        imagePart.write(uploadPath + File.separator + fileName);

        // 🧠 Create Product object
        Product p = new Product();
        p.setName(name);
        p.setDescription(description);
        p.setPrice(price);
        p.setCategory(category);
        p.setQuantity(quantity);
        p.setImage(fileName); // only filename

        // 💾 Save to DB
        ProductDao dao = new ProductDao();
        dao.addProduct(p);
        resp.sendRedirect("adminProduct");
    }
}
