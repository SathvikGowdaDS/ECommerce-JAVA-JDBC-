package com.ecommerce.service;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import com.ecommerce.dao.EcomDAOImpl;
import com.ecommerce.model.Product;

public class EcomService {
    EcomDAOImpl dao = new EcomDAOImpl();

    private Connection getConnection() throws Exception {
        FileReader fr = new FileReader("ecommerce.properties");
        Properties p = new Properties();
        p.load(fr);
        return DriverManager.getConnection(p.getProperty("url"), p);
    }

    public void addProduct(Product product) {
        try (Connection conn = getConnection()) {
            dao.addProduct(conn, product);
        } catch (Exception e) {
            System.out.println("❌ Exception in addProduct(): " + e.getMessage());
        }
    }

    public void viewProducts() {
        try (Connection conn = getConnection()) {
            dao.viewProducts(conn);
        } catch (Exception e) {
            System.out.println("❌ Exception in viewProducts(): " + e.getMessage());
        }
    }

    public void purchaseProduct(int productId, int qty) {
        try (Connection conn = getConnection()) {
            dao.purchaseProduct(conn, productId, qty);
        } catch (Exception e) {
            System.out.println("❌ Exception in purchaseProduct(): " + e.getMessage());
        }
    }

    public void viewOrders() {
        try (Connection conn = getConnection()) {
            dao.viewOrders(conn);
        } catch (Exception e) {
            System.out.println("❌ Exception in viewOrders(): " + e.getMessage());
        }
    }
}
