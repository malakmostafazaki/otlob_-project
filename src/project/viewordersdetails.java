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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author MalakMostafaZaky
 */
public class viewordersdetails extends Application {
    private TextArea ordersDisplayArea;

    @Override
    public void start(Stage primaryStage) {
         primaryStage.setTitle("View All Orders");

        // Title Label
        Label titleLabel = new Label("All Orders");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // TextArea for displaying orders
        ordersDisplayArea = new TextArea();
        ordersDisplayArea.setEditable(false);
        ordersDisplayArea.setWrapText(true);

        // Load Orders Button
        Button loadOrdersButton = new Button("Load Orders");
        loadOrdersButton.setOnAction(event -> loadAllOrders());

        // Back Button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> navigateTo(new SellerMenu(), primaryStage));

        // Layout for Buttons and Orders Display
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(titleLabel, loadOrdersButton, ordersDisplayArea);

        // HBox for Back Button
        HBox backBox = new HBox(backButton);
        backBox.setAlignment(Pos.BOTTOM_RIGHT);
        backBox.setPadding(new Insets(10));

        // Layout using BorderPane
        BorderPane root = new BorderPane();
        root.setCenter(layout);
        root.setBottom(backBox);

        // Scene Setup
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load Orders into Memory
        Order.loadOrdersFromFile();
    }

    private void loadAllOrders() {
        StringBuilder ordersDetails = new StringBuilder();
        if (Order.getOrderMap().isEmpty()) {
            ordersDetails.append("No orders available.");
        } else {
            for (Order order : Order.getOrderMap().values()) {
                ordersDetails.append(order.toString()).append("\n\n");
            }
        }
        ordersDisplayArea.setText(ordersDetails.toString());
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
        launch(args);
    }
}