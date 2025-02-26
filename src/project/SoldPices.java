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


public class SoldPices extends Application {
    
     private TextField startDateField, endDateField;
    private Label resultLabel;
    
    @Override
    public void start(Stage primaryStage) {
     primaryStage.setTitle("Pieces Sold Over Period");

        // Title Label
        Label titleLabel = new Label("Pieces Sold Over Period");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Input Fields
        Label startDateLabel = new Label("Start Date (yyyy-MM-dd):");
        startDateField = new TextField();

        Label endDateLabel = new Label("End Date (yyyy-MM-dd):");
        endDateField = new TextField();

        // Calculate Button and Result Label
        Button calculateButton = new Button("Calculate Pieces Sold");
        resultLabel = new Label();
        resultLabel.setWrapText(true);

        // Button Action
        calculateButton.setOnAction(event -> calculatePiecesSold());

        // Back Button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> navigateTo(new SellerMenu(), primaryStage));

        // GridPane for Input Form
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(startDateLabel, 0, 0);
        gridPane.add(startDateField, 1, 0);
        gridPane.add(endDateLabel, 0, 1);
        gridPane.add(endDateField, 1, 1);
        gridPane.add(calculateButton, 1, 2);
        gridPane.add(resultLabel, 1, 3);

        // HBox for Back Button
        HBox backBox = new HBox(backButton);
        backBox.setAlignment(Pos.BOTTOM_RIGHT);
        backBox.setPadding(new Insets(10));

        // Layout using BorderPane
        BorderPane root = new BorderPane();
        root.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        root.setCenter(gridPane);
        root.setBottom(backBox);

        // Scene Setup
        Scene scene = new Scene(root, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load Orders
        Order.loadOrdersFromFile();
    }

    private void calculatePiecesSold() {
        String startDate = startDateField.getText().trim();
        String endDate = endDateField.getText().trim();

        if (startDate.isEmpty() || endDate.isEmpty()) {
            resultLabel.setText("Please enter both start and end dates.");
            return;
        }

        try {
            int totalPiecesSold = Order.piecesSoldOverPeriod(startDate, endDate);
            resultLabel.setText("Total Pieces Sold: " + totalPiecesSold);
        } catch (Exception e) {
            resultLabel.setText("Error: Invalid date format or data issue.");
            e.printStackTrace();
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