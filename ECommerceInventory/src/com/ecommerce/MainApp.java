package com.ecommerce;

import java.util.InputMismatchException;
import java.util.Scanner;
import com.ecommerce.model.Product;
import com.ecommerce.service.EcomService;

public class MainApp {
    public static void main(String[] args) {
        EcomService service = new EcomService();
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("\n📦 1. Add Product\n📋 2. View Products\n🛒 3. Purchase Product\n🧾 4. View Orders\n❌ 5. Exit");
                System.out.print("Enter choice: ");
                int ch = sc.nextInt();

                switch (ch) {
                    case 1 -> {
                        System.out.print("Product ID: ");
                        int id = sc.nextInt();
                        sc.nextLine(); // consume newline
                        System.out.print("Name: ");
                        String name = sc.nextLine();
                        System.out.print("Price: ");
                        double price = sc.nextDouble();
                        System.out.print("Quantity: ");
                        int qty = sc.nextInt();
                        service.addProduct(new Product(id, name, price, qty));
                    }
                    case 2 -> service.viewProducts();
                    case 3 -> {
                        System.out.print("Enter Product ID: ");
                        int pid = sc.nextInt();
                        System.out.print("Quantity: ");
                        int q = sc.nextInt();
                        service.purchaseProduct(pid, q);
                    }
                    case 4 -> service.viewOrders();
                    case 5 -> {
                        System.out.println("👋 Thank you. Exiting...");
                        sc.close();
                        return;
                    }
                    default -> System.out.println("❌ Invalid option. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Please enter valid input (number expected).");
                sc.nextLine(); // clear buffer
            } catch (Exception e) {
                System.out.println("❌ Unexpected error: " + e.getMessage());
            }
        }
    }
}
