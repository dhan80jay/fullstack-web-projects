package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.CartItem;
 
public class CartDao {
	Connection con;
	public boolean addToCart(int userId, int productId,int quantity){
		
		try {
			con = DBConnection.getConnection();
			PreparedStatement pt = con.prepareStatement("insert into cart (user_id,product_id,quantity) values(?,?,?)");
			pt.setInt(1, userId);
			pt.setInt(2,productId);
			pt.setInt(3, quantity);
			
			int raws = pt.executeUpdate();
			return raws > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public List<CartItem> getCartItemsByUserId(int userId){
		List <CartItem> list = new ArrayList();

		try {
			con = DBConnection.getConnection();
			PreparedStatement pt = con.prepareStatement ("SELECT \r\n"
					+ "    c.cart_id,\r\n"
					+ "    c.user_id,\r\n"
					+ "    c.product_id,\r\n"
					+ "    c.quantity,\r\n"
					+ "    p.name,\r\n"
					+ "    p.price,\r\n"
					+ "    p.image\r\n"
					+ "FROM cart c\r\n"
					+ "JOIN products p \r\n"
					+ "    ON c.product_id = p.product_id\r\n"
					+ "WHERE c.user_id = ?;");
			pt.setInt(1, userId);
			ResultSet rs = pt.executeQuery();
				
			while(rs.next()) {
				CartItem item = new CartItem();
		          item.setCartId(rs.getInt("cart_id"));
	                item.setUserId(rs.getInt("user_id"));
	                item.setProductId(rs.getInt("product_id"));
	                item.setQuantity(rs.getInt("quantity"));
	                item.setProductName(rs.getString("name"));
	                item.setPrice(rs.getDouble("price"));
	                item.setImage(rs.getString("image"));
	                
	                list.add(item);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	
	public boolean removeFromCart(int cartId,int productid) {
		try {
			con = DBConnection.getConnection();
			PreparedStatement pt=con.prepareStatement("delete from cart where user_id = ? and product_id =?");
			pt.setInt(1, cartId);
			pt.setInt(2, productid);
			return pt.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void increaseQuantity(int cartId) {
	    String sql = "UPDATE cart SET quantity = quantity + 1 WHERE cart_id = ?";
	    try {
	    	con = DBConnection.getConnection();
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, cartId);
	        ps.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public void decreaseQuantity(int cartId) {
	    String sql =
	        "UPDATE cart SET quantity = quantity - 1 " +
	        "WHERE cart_id = ? AND quantity > 1";
	    try  {
	        con = DBConnection.getConnection();
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, cartId);
	        ps.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
	public boolean emptyCart(int userId) {
			try {
				con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement("delete from cart where user_id = ?");
				ps.setInt(1, userId);			
				int raws = ps.executeUpdate();
				return raws > 0;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
			
	}
	
	public boolean hasItemsInCart(int userId) {

	    String sql = "SELECT 1 FROM cart WHERE user_id = ? LIMIT 1";

	    try{
	    	con = DBConnection.getConnection();
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, userId);
	        ResultSet rs = ps.executeQuery();
	        return rs.next();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	public double getCartTotalAmount(int userId) {

	    double total = 0;

	    String sql =
	        "SELECT SUM(p.price * c.quantity) AS total_amount " +
	        "FROM cart c " +
	        "JOIN products p ON c.product_id = p.product_id " +
	        "WHERE c.user_id = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, userId);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            total = rs.getDouble("total_amount");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return total;
	}
 
	public int getCartQuantity(int cartId) {
	    int qty = 0;
	    String sql = "SELECT quantity FROM cart WHERE cart_id = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, cartId);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            qty = rs.getInt("quantity");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return qty;
	}

	public int getProductIdByCartId(int cartId) {
	    int productId = 0;
	    String sql = "SELECT product_id FROM cart WHERE cart_id = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, cartId);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            productId = rs.getInt("product_id");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return productId;
	}
	public void updateQuantity(int cartId, int quantity) {
	    String sql = "UPDATE cart SET quantity = ? WHERE cart_id = ?";
	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, quantity);
	        ps.setInt(2, cartId);
	        ps.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	public double calculateTotal(double price,int qty) {
		return price*qty;
	}
 

}
