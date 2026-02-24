package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.CartItem;
import com.model.Product;

public class ProductDao {
	Connection con;

	public List<Product> getAllProducts() {
		ArrayList<Product> list = new ArrayList();
		try {
			con = DBConnection.getConnection();
			PreparedStatement pt = con.prepareStatement("Select * from products");
			ResultSet rs = pt.executeQuery();

			while (rs.next()) {
				Product p = new Product();
				p.setId(rs.getInt("product_id"));
				p.setName(rs.getString("name"));
				p.setDescription(rs.getString("description"));
				p.setPrice(rs.getDouble("price"));
				p.setCategory(rs.getString("category"));
				p.setQuantity(rs.getInt("quantity"));
				p.setImage(rs.getString("image"));
				;

				list.add(p);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public boolean addProduct(Product p) {
		try {
			con = DBConnection.getConnection();
			PreparedStatement pt = con.prepareStatement(
					"insert into products(name,description,price,category,quantity,image) values(?,?,?,?,?,?)");
			pt.setString(1, p.getName());
			pt.setString(2, p.getDescription());
			pt.setDouble(3, p.getPrice());
			pt.setString(4, p.getCategory());
			pt.setInt(5, p.getQuantity());
			pt.setString(6, p.getImage());

			int raw = pt.executeUpdate();
			return raw > 0;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

 

	public Product getProductById(int id) {
		Product p = null;

		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT * FROM products WHERE product_id=?")) {

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				p = new Product();
				p.setId(rs.getInt("product_id"));
				p.setName(rs.getString("name"));
				p.setDescription(rs.getString("description"));
				p.setPrice(rs.getDouble("price"));
				p.setQuantity(rs.getInt("quantity"));
				p.setImage(rs.getString("image"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

	public boolean updateProduct(Product p) {
		try {
			con = DBConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(
					"UPDATE products SET name=?, price=?, quantity=? ,description=? WHERE product_id=?");
			ps.setString(1, p.getName());
			ps.setDouble(2, p.getPrice());
			ps.setInt(3, p.getQuantity());
			ps.setString(4, p.getDescription());
			ps.setInt(5, p.getId());

			int raw = ps.executeUpdate();
			System.out.println("Raw affected : " + raw);
			return raw > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}



	public int getProductQuantity(int productId) {

		int quantity = 0;
		String sql = "SELECT quantity FROM products WHERE product_id = ?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, productId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				quantity = rs.getInt("quantity"); // stock from products table
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return quantity;
	}

	public void reduceStockAfterPayment(List<CartItem> cartItems) {

		String sql = "UPDATE products " + "SET quantity = quantity - ? " + "WHERE product_id = ? AND quantity >= ?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			for (CartItem item : cartItems) {

				ps.setInt(1, item.getQuantity()); // cart qty
				ps.setInt(2, item.getProductId());
				ps.setInt(3, item.getQuantity()); // safety check

				ps.addBatch();
			}

			ps.executeBatch(); // 🔥 fast & safe

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean reduceStockAfterBuyNow(int productId, int qty) {

		String sql = "UPDATE products " + "SET quantity = quantity - ? " + "WHERE product_id = ? AND quantity >= ?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, qty);
			ps.setInt(2, productId);
			ps.setInt(3, qty);

			int rowsUpdated = ps.executeUpdate();

			return rowsUpdated > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Product> getFilteredProducts(
	        String category,
	        String search,
	        Double minPrice,
	        Double maxPrice,
	        String sort) {

	    List<Product> list = new ArrayList<>();

	    StringBuilder sql = new StringBuilder(
	            "SELECT * FROM products WHERE 1=1"
	    );

	    // 🔹 Category filter
	    if (category != null && !category.trim().isEmpty()) {
	        sql.append(" AND LOWER(category) = LOWER(?)");
	    }

	    // 🔹 Search by product name
	    if (search != null && !search.trim().isEmpty()) {
	        sql.append(" AND LOWER(name) LIKE ?");
	    }

	    // 🔹 Min price
	    if (minPrice != null) {
	        sql.append(" AND price >= ?");
	    }

	    // 🔹 Max price
	    if (maxPrice != null) {
	        sql.append(" AND price <= ?");
	    }

	    // 🔹 Price sorting
	    if ("low".equals(sort)) {
	        sql.append(" ORDER BY price ASC");
	    } else if ("high".equals(sort)) {
	        sql.append(" ORDER BY price DESC");
	    }

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql.toString())) {

	        int index = 1;

	        if (category != null && !category.trim().isEmpty()) {
	            ps.setString(index++, category);
	        }

	        if (search != null && !search.trim().isEmpty()) {
	            ps.setString(index++, "%" + search.toLowerCase() + "%");
	        }

	        if (minPrice != null) {
	            ps.setDouble(index++, minPrice);
	        }

	        if (maxPrice != null) {
	            ps.setDouble(index++, maxPrice);
	        }

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Product p = new Product();
	            p.setId(rs.getInt("product_id"));
	            p.setName(rs.getString("name"));
	            p.setDescription(rs.getString("description"));
	            p.setPrice(rs.getDouble("price"));
	            p.setCategory(rs.getString("category"));
	            p.setQuantity(rs.getInt("quantity"));
	            p.setImage(rs.getString("image"));
	            list.add(p);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}


	public boolean deleteProduct(int id) {
	    Connection con = null;
	    PreparedStatement psCart = null;
	    PreparedStatement psProduct = null;

	    try {
	        con = DBConnection.getConnection();
	        con.setAutoCommit(false); // 🔒 start transaction

	        // 1️⃣ Delete product from cart first
	        psCart = con.prepareStatement(
	            "DELETE FROM cart WHERE product_id = ?"
	        );
	        psCart.setInt(1, id);
	        psCart.executeUpdate();

	        // 2️⃣ Delete product from products table
	        psProduct = con.prepareStatement(
	            "DELETE FROM products WHERE product_id = ?"
	        );
	        psProduct.setInt(1, id);
	        int rows = psProduct.executeUpdate();

	        con.commit(); // ✅ success
	        return rows > 0;

	    } catch (SQLException e) {
	        try {
	            if (con != null) con.rollback(); // ❌ undo on error
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        e.printStackTrace();
	    } finally {
	        try {
	            if (psCart != null) psCart.close();
	            if (psProduct != null) psProduct.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return false;
	}

	
	public double getProductPriceById(int productId) {

	    double price = 0.0;

	    String sql = "SELECT price FROM products WHERE product_id = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, productId);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            price = rs.getDouble("price");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return price;
	}

}
