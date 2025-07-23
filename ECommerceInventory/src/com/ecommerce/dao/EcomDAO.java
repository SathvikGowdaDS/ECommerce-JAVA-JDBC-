package com.ecommerce.dao;

import java.sql.Connection;
import com.ecommerce.model.Product;

public interface EcomDAO {
    void addProduct(Connection conn, Product product);
    void viewProducts(Connection conn);
    void purchaseProduct(Connection conn, int productId, int qty);
    void viewOrders(Connection conn);
}
