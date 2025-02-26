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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.HashMap;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author MalakMostafaZaky
 */
public class EditProduct extends Application {
  private TextField idField, nameField, descriptionField, priceField, quantityField;
    private Label resultLabel;

    @Override
    public void start(Stage primaryStage) {
   primaryStage.setTitle("Edit Product");

        // Load product data
        Product.readProductsFromFile().forEach(product -> Product.getProductMap().put(product.getId(), product));

        // Input fields and labels
        idField = new TextField();
        nameField = new TextField();
        descriptionField = new TextField();
        priceField = new TextField();
        quantityField = new TextField();

        Button editButton = new Button("Edit");
        editButton.setOnAction(new EditHandler());

        resultLabel = new Label();

        // GridPane for the input form
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Enter Product ID to edit:"), 0, 0);
        grid.add(idField, 1, 0);

        grid.add(new Label("Enter new name:"), 0, 1);
        grid.add(nameField, 1, 1);

        grid.add(new Label("Enter new description:"), 0, 2);
        grid.add(descriptionField, 1, 2);

        grid.add(new Label("Enter new price:"), 0, 3);
        grid.add(priceField, 1, 3);

        grid.add(new Label("Enter new quantity:"), 0, 4);
        grid.add(quantityField, 1, 4);

        grid.add(editButton, 1, 5);
        grid.add(resultLabel, 1, 6);

        // Back Button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> navigateTo(new SellerMenu(), primaryStage));

        // HBox for the Back button
        HBox backBox = new HBox(backButton);
        backBox.setAlignment(Pos.BOTTOM_RIGHT);
        backBox.setPadding(new Insets(10));

        // Combine everything using BorderPane
        BorderPane root = new BorderPane();
        root.setCenter(grid);
        root.setBottom(backBox);

        // Scene setup
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public class EditHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            String idToEdit = idField.getText().trim();
            String newName = nameField.getText().trim();
            String newDescription = descriptionField.getText().trim();
            String newPrice = priceField.getText().trim();
            String newQuantity = quantityField.getText().trim();

            if (!idToEdit.isEmpty() && !newName.isEmpty() && !newDescription.isEmpty() && !newPrice.isEmpty() && !newQuantity.isEmpty()) {
                try {
                    int id = Integer.parseInt(idToEdit);
                    double price = Double.parseDouble(newPrice);
                    int quantity = Integer.parseInt(newQuantity);

                    Product product = Product.getProductMap().get(id);
                    if (product != null) {
                        product.setName(newName);
                        product.setDescription(newDescription);
                        product.setPrice(price);
                        product.setQuantity(quantity);
                        Product.saveProductsToFile();
                        resultLabel.setText("Product updated successfully.");
                    } else {
                        resultLabel.setText("Product not found.");
                    }
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Please enter valid numeric values.");
                }
            } else {
                resultLabel.setText("Please fill in all fields.");
            }
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
        launch(args);
    }
}