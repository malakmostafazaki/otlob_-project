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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.ArrayList;


/**
 *
 * @author MalakMostafaZaky
 */
public class RemoveProduct extends Application {
    
     private TextField idField;
    private Label resultLabel;

    @Override
    public void start(Stage primaryStage) {
       primaryStage.setTitle("Remove Product");

        // Input and Button Components
        Label idLabel = new Label("Enter Product ID to remove:");
        idField = new TextField();
        Button removeButton = new Button("Remove");
        Button backButton = new Button("Back");
        resultLabel = new Label();

        // Remove Button Action
        removeButton.setOnAction(new RemoveHandler());

        // Back Button Action
        backButton.setOnAction(e -> navigateTo(new SellerMenu(), primaryStage));

        // GridPane for Form
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(idLabel, 0, 0);
        grid.add(idField, 1, 0);
        grid.add(removeButton, 1, 1);
        grid.add(resultLabel, 1, 2);

        // Back Button in an HBox
        HBox backBox = new HBox(backButton);
        backBox.setAlignment(Pos.BOTTOM_RIGHT);
        backBox.setPadding(new Insets(10));

        // Layout with BorderPane
        BorderPane root = new BorderPane();
        root.setCenter(grid);
        root.setBottom(backBox);

        // Scene Setup
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public class RemoveHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String idInput = idField.getText().trim();

            if (idInput.isEmpty()) {
                resultLabel.setText("Please enter a Product ID.");
                return;
            }

            try {
                int id = Integer.parseInt(idInput);
                if (Product.getProductMap().containsKey(id)) {
                    Product.getProductMap().remove(id);
                    Product.saveProductsToFile();
                    resultLabel.setText("Product removed successfully!");
                } else {
                    resultLabel.setText("Product ID not found.");
                }
            } catch (NumberFormatException e) {
                resultLabel.setText("Invalid input. Please enter a numeric Product ID.");
            } catch (Exception e) {
                resultLabel.setText("An error occurred: " + e.getMessage());
            }
        }
    }

    // Method to Navigate to Another Window
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
        // Load products from file into the map
        ArrayList<Product> products = Product.readProductsFromFile();
        for (Product product : products) {
            Product.getProductMap().put(product.getId(), product);
        }

        launch(args);
    }
}