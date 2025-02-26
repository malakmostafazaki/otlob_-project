/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package project;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.*;
import project.Order;
import project.Product;

/**
 *
 * @author MalakMostafaZaky
 */
public class MaxOrdersAdmin extends Application {
    
 
    private Label topCustomerLabel;
    private Label topSellerLabel;

    @Override
    public void start(Stage primaryStage) {
      
        Button btn = new Button("Get Top Customer and Seller");
        btn.setOnAction(new TopCustomerOrSellerHandler());
         Button Backbtn = new Button("Back");
        Backbtn.setOnAction(e -> navigateTo(new AdminHomepage(), primaryStage));

  
        topCustomerLabel = new Label("Top Customer: ");
        topSellerLabel = new Label("Top Seller: ");

      
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
         vbox.getChildren().addAll(Backbtn);
        vbox.getChildren().addAll(btn, topCustomerLabel, topSellerLabel);

       
        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setTitle("Otlob - Admin - Max Orders");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

   
    public class TopCustomerOrSellerHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
           
            getTopCustomerOrSeller();
        }
    }

  
    public void getTopCustomerOrSeller() {
       
        Order.loadOrdersFromFile();

       
        HashMap<String, Integer> customerOrderCount = new HashMap<>();
        HashMap<Integer, Integer> sellerOrderCount = new HashMap<>();

        for (Order order : Order.getOrderMap().values()) {
            
            String customerName = order.getCustomerName();
            customerOrderCount.put(customerName, customerOrderCount.getOrDefault(customerName, 0) + 1);

           
            int productId = order.getProductId();
            Product product = Product.getProductMap().get(productId);
            if (product != null) {
                int sellerId = product.getId();
                sellerOrderCount.put(sellerId, sellerOrderCount.getOrDefault(sellerId, 0) + 1);
            }
        }

       
        String topCustomer = null;
        int maxCustomerOrders = 0;
        for (Map.Entry<String, Integer> entry : customerOrderCount.entrySet()) {
            if (entry.getValue() > maxCustomerOrders) {
                maxCustomerOrders = entry.getValue();
                topCustomer = entry.getKey();
            }
        }

       
        int topSellerId = -1;
        int maxSellerOrders = 0;
        for (Map.Entry<Integer, Integer> entry : sellerOrderCount.entrySet()) {
            if (entry.getValue() > maxSellerOrders) {
                maxSellerOrders = entry.getValue();
                topSellerId = entry.getKey();
            }
        }

        
        if (topCustomer != null) {
            topCustomerLabel.setText("Top Customer: " + topCustomer + " (" + maxCustomerOrders + " orders)");
        } else {
            topCustomerLabel.setText("Top Customer: No customers have placed orders.");
        }

        if (topSellerId != -1) {
            topSellerLabel.setText("Top Seller: Seller ID " + topSellerId + " (" + maxSellerOrders + " orders)");
        } else {
            topSellerLabel.setText("Top Seller: No sellers have orders associated.");
        }
    }
    
    private void navigateTo(Application function, Stage primaryStage) {
        try {
            Stage newStage = new Stage();
            function.start(newStage);
            primaryStage.close(); // Close the current window
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }
}
