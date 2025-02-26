/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


/**
 *
 * @author MalakMostafaZaky
 */
public class ChangeOrderStaute extends Application {
    
    
  private TextField orderIdField;
    private Label resultLabel;

    @Override
    public void start(Stage primaryStage) {
         primaryStage.setTitle("Change Order Status");

        // Title Label
        Label titleLabel = new Label("Change Order Status");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Input Fields
        Label orderIdLabel = new Label("Enter Order ID:");
        orderIdField = new TextField();

        // Change Status Button
        Button changeStatusButton = new Button("Change Status to Shipped");
        resultLabel = new Label();
        changeStatusButton.setOnAction(event -> changeOrderStatus());

        // Back Button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> navigateTo(new SellerMenu(), primaryStage));

        // GridPane for Input Form
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(orderIdLabel, 0, 0);
        grid.add(orderIdField, 1, 0);
        grid.add(changeStatusButton, 1, 1);
        grid.add(resultLabel, 1, 2);

        // HBox for Back Button
        HBox backBox = new HBox(backButton);
        backBox.setAlignment(Pos.BOTTOM_RIGHT);
        backBox.setPadding(new Insets(10));

        // Layout using BorderPane
        BorderPane root = new BorderPane();
        root.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        root.setCenter(grid);
        root.setBottom(backBox);

        // Scene Setup
        Scene scene = new Scene(root, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load Orders
        Order.loadOrdersFromFile();
    }

    private void changeOrderStatus() {
        String orderIdInput = orderIdField.getText().trim();
        if (orderIdInput.isEmpty()) {
            resultLabel.setText("Please enter an Order ID.");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdInput);
            Order.changeStatusToShipped(orderId);
            resultLabel.setText("Order ID " + orderId + " status changed to 'shipped'.");
        } catch (NumberFormatException e) {
            resultLabel.setText("Invalid Order ID. Please enter a valid number.");
        } catch (Exception e) {
            resultLabel.setText("Error: " + e.getMessage());
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

    public static void main(String[] args) {
        // Load Orders
        Order.loadOrdersFromFile();
        launch(args);
    }
}