package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.model.CartItem;
import com.model.Order;

public class OrderDao {
	Connection con;

	public int createOrder(int userId, double totalAmount, String userName, String paymentMethod,
			String paymentStatus) {

		int orderId = 0;

		String sql = """
				INSERT INTO orders
				(user_id, order_date, total_amount,
				payment_method, payment_status, order_status, user_name)
				VALUES (?, NOW(), ?, ?, ?, 'PLACED', ?)
				""";

		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setInt(1, userId);
			ps.setDouble(2, totalAmount);
			ps.setString(3, paymentMethod);
			ps.setString(4, paymentStatus);
			ps.setString(5, userName);

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				orderId = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return orderId;
	}

	public void addOrderItems(int orderId, List<CartItem> cartList) {

		String sql = "INSERT INTO order_items (order_id, product_id, quantity, price,product_name) " + "VALUES (?, ?, ?, ?,?)";

		try {
			con = DBConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			for (CartItem c : cartList) {
				ps.setInt(1, orderId);
				ps.setInt(2, c.getProductId());
				ps.setInt(3, c.getQuantity());
				ps.setDouble(4, c.getPrice());
				ps.setString(5, c.getProductName());
				ps.addBatch();
			}
			ps.executeBatch();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Order> getAllOrders() {

	    List<Order> list = new ArrayList<>();

	    String sql = """
	        SELECT
	            o.order_id,
	            o.user_id,
	            o.user_name,
	            o.total_amount,
	            o.payment_status,
	            o.order_status,
	            o.payment_method,
	            o.order_date
	        FROM orders o
	        ORDER BY o.order_date DESC
	    """;

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            Order o = new Order();
	            o.setId(rs.getInt("order_id"));
	            o.setUserId(rs.getInt("user_id"));
	            o.setUserName(rs.getString("user_name"));
	            o.setTotalAmount(rs.getDouble("total_amount"));
	            o.setPaymentStatus(rs.getString("payment_status"));
	            o.setStatus(rs.getString("order_status"));
	            o.setPaymentMethod(rs.getString("payment_method"));

	            Timestamp ts = rs.getTimestamp("order_date");
	            o.setOrderDate(ts.toLocalDateTime());

	            list.add(o);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}

	// update order status (admin action)
	public boolean updateOrderStatus(int orderId, String status) {

		String sql = "UPDATE orders SET order_status=? WHERE order_id=?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, status);
			ps.setInt(2, orderId);
			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updatePaymentStatus(int orderId, String paymentStatus) {

		String sql = "UPDATE orders SET payment_status=? WHERE order_id=?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, paymentStatus);
			ps.setInt(2, orderId);

			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Order> getOrdersByUserId(int userId) {

	    List<Order> list = new ArrayList<>();

	    String sql = """
	        SELECT
	            o.order_id,
	            o.total_amount,
	            o.order_status,
	            o.payment_method,
	            GROUP_CONCAT(
	                COALESCE(oi.product_name, 'Unknown Product')
	                ORDER BY oi.item_id
	                SEPARATOR ', '
	            ) AS product_names
	        FROM orders o
	        LEFT JOIN order_items oi ON o.order_id = oi.order_id
	        WHERE o.user_id = ?
	        GROUP BY
	            o.order_id,
	            o.total_amount,
	            o.order_status,
	            o.payment_method
	        ORDER BY o.order_date DESC
	    """;

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, userId);

	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Order o = new Order();
	                o.setId(rs.getInt("order_id"));
	                o.setTotalAmount(rs.getDouble("total_amount"));
	                o.setStatus(rs.getString("order_status"));
	                o.setPaymentMethod(rs.getString("payment_method"));
	                o.setProductNames(rs.getString("product_names"));
	                list.add(o);
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}


	public void addBuyNowItem(int orderId, int pid, int qty) {

	    String sql = """
	        INSERT INTO order_items (order_id, product_id, product_name, quantity, price)
	        SELECT ?, product_id, name, ?, (price * ?)
	        FROM products
	        WHERE product_id = ?
	    """;

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, orderId);
	        ps.setInt(2, qty);
	        ps.setInt(3, qty);
	        ps.setInt(4, pid);
	        ps.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


}
