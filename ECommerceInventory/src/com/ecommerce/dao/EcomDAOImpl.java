package com.ecommerce.dao;

import java.sql.*;
import com.ecommerce.model.Product;

public class EcomDAOImpl implements EcomDAO {

    public void addProduct(Connection conn, Product product) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO products VALUES (?, ?, ?, ?)");
            ps.setInt(1, product.getProductId());
            ps.setString(2, product.getName());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.executeUpdate();
            System.out.println("✅ Product added successfully.");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("❌ Product ID already exists.");
        } catch (SQLException e) {
            System.out.println("❌ SQL Error while adding product: " + e.getMessage());
        }
    }

    public void viewProducts(Connection conn) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM products");
            System.out.println("\n📦 Product List:");
            while (rs.next()) {
                System.out.printf("ID: %d | Name: %s | ₹%.2f | Stock: %d\n",
                        rs.getInt("productId"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error viewing products: " + e.getMessage());
        }
    }

    public void purchaseProduct(Connection conn, int productId, int qty) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT quantity FROM products WHERE productId=?");
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int stock = rs.getInt(1);
                if (stock >= qty) {
                    PreparedStatement updateStock = conn.prepareStatement(
                        "UPDATE products SET quantity = quantity - ? WHERE productId=?");
                    updateStock.setInt(1, qty);
                    updateStock.setInt(2, productId);
                    updateStock.executeUpdate();

                    PreparedStatement insertOrder = conn.prepareStatement(
                        "INSERT INTO orders(productId, quantity) VALUES (?, ?)");
                    insertOrder.setInt(1, productId);
                    insertOrder.setInt(2, qty);
                    insertOrder.executeUpdate();

                    System.out.println("✅ Order placed successfully.");
                } else {
                    System.out.println("❌ Insufficient stock available.");
                }
            } else {
                System.out.println("❌ Product not found.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error placing order: " + e.getMessage());
        }
    }

    public void viewOrders(Connection conn) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM orders");
            System.out.println("\n📃 Order History:");
            while (rs.next()) {
                System.out.printf("OrderID: %d | ProductID: %d | Qty: %d | Date: %s\n",
                        rs.getInt("orderId"),
                        rs.getInt("productId"),
                        rs.getInt("quantity"),
                        rs.getTimestamp("orderDate"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error viewing orders: " + e.getMessage());
        }
    }
}
