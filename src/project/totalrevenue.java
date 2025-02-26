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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author MalakMostafaZaky
 */
public class totalrevenue extends Application {
    private TextField startDateField, endDateField;
    private Label resultLabel;
    @Override
    public void start(Stage primaryStage) {
       primaryStage.setTitle("Calculate Total Revenue");

        // Title Label
        Label titleLabel = new Label("Total Revenue Calculator");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Input Fields
        Label startDateLabel = new Label("Start Date (yyyy-MM-dd):");
        startDateField = new TextField();

        Label endDateLabel = new Label("End Date (yyyy-MM-dd):");
        endDateField = new TextField();

        // Calculate Button
        Button calculateButton = new Button("Calculate Total Revenue");
        resultLabel = new Label();
        calculateButton.setOnAction(event -> calculateTotalRevenue());

        // Back Button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> navigateTo(new SellerMenu(), primaryStage));

        // GridPane for Inputs
        GridPane grid = new GridPane();// contains getshildern to return the list of node in pane
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));//Adds a padding of 20 pixels around the edges of the grid. This creates space between the grid and its parent container.
        grid.setHgap(10);//ets the horizontal (Hgap) and vertical (Vgap) gaps between elements in the grid to 10 pixels.

        grid.setVgap(10);

        grid.add(startDateLabel, 0, 0);//Places the startDateLabel in the first column (0) of the first row (0)
        grid.add(startDateField, 1, 0);
        grid.add(endDateLabel, 0, 1);
        grid.add(endDateField, 1, 1);
        grid.add(calculateButton, 1, 2);
        grid.add(resultLabel, 1, 3);

        // HBox for Back Button
        HBox backBox = new HBox(backButton);
        backBox.setAlignment(Pos.BOTTOM_RIGHT);
        backBox.setPadding(new Insets(10));

        // Layout using BorderPane
        BorderPane root = new BorderPane();// borderpane place the node in up / down /left 
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
//helper method 
    private void calculateTotalRevenue() {
        String startDate = startDateField.getText().trim();
        String endDate = endDateField.getText().trim();

        if (startDate.isEmpty() || endDate.isEmpty()) {
            resultLabel.setText("Please enter both start and end dates.");
            return;
        }

        try {
            double totalRevenue = Order.calculateTotalRevenue(startDate, endDate);
            resultLabel.setText("Total Revenue: $" + String.format("%.2f", totalRevenue));
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