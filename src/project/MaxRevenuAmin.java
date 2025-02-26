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


/**
 *
 * @author MalakMostafaZaky
 */
public class MaxRevenuAmin extends Application {
    
   
    private Label resultLabel;

    @Override
    public void start(Stage primaryStage) {
       
        Button getRevenueButton = new Button("Get User with Greatest Revenue");
        getRevenueButton.setOnAction(new GetRevenueHandler());
         Button Backbtn = new Button("Back");
        Backbtn.setOnAction(e -> navigateTo(new AdminHomepage(), primaryStage));

       
        resultLabel = new Label("Click the button to calculate the user with the greatest revenue.");

       
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(Backbtn);
        vbox.getChildren().addAll(getRevenueButton, resultLabel);
        

       
        Scene scene = new Scene(vbox, 500, 200);
        primaryStage.setTitle("Otlob - Admin - Greatest Revenue Viewer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    public class GetRevenueHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            getUserWithGreatestRevenue();
        }
    }

   
    public void getUserWithGreatestRevenue() {
        double maxRevenue = 0;
        String topUser = null;

        Order.loadOrdersFromFile();

        
        HashMap<Integer, Double> sellerRevenue = new HashMap<>();
        for (Order order : Order.getOrderMap().values()) {
            double orderRevenue = order.getTotalPrice();
            int sellerId = order.getProductId();

            sellerRevenue.put(sellerId, sellerRevenue.getOrDefault(sellerId, 0.0) + orderRevenue);
        }

     
        HashMap<String, Double> customerSpending = new HashMap<>();
        for (Order order : Order.getOrderMap().values()) {
            double orderRevenue = order.getTotalPrice();
            String customerName = order.getCustomerName();

            customerSpending.put(customerName, customerSpending.getOrDefault(customerName, 0.0) + orderRevenue);
        }

       
        int topSellerId = -1;
        for (Map.Entry<Integer, Double> entry : sellerRevenue.entrySet()) {
            double revenue = entry.getValue();
            if (revenue > maxRevenue) {
                maxRevenue = revenue;
                topSellerId = entry.getKey();
                topUser = "Seller with ID: " + topSellerId;
            }
        }

        
        for (Map.Entry<String, Double> entry : customerSpending.entrySet()) {
            double spending = entry.getValue();
            if (spending > maxRevenue) {
                maxRevenue = spending;
                topUser = "Customer: " + entry.getKey();
            }
        }

       
        if (topUser != null) {
            resultLabel.setText("User with greatest revenue: " + topUser + " with revenue: $" + String.format("%.2f", maxRevenue));
        } else {
            resultLabel.setText("No orders found to calculate revenue.");
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
