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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author MalakMostafaZaky
 */
public class Placeorder extends Application {
         private TextField customerNameField, productNameField, quantityField;
    private Label resultLabel;


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Place Order");

        // UI Components
        Label customerNameLabel = new Label("Customer Name:");
        customerNameField = new TextField();

        Label productNameLabel = new Label("Product Name:");
        productNameField = new TextField();

        Label quantityLabel = new Label("Quantity:");
        quantityField = new TextField();

        Button placeOrderButton = new Button("Place Order");
        resultLabel = new Label();
        placeOrderButton.setOnAction(new PlaceOrderHandler());

        // Layout Setup
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(customerNameLabel, 0, 0);
        grid.add(customerNameField, 1, 0);
        grid.add(productNameLabel, 0, 1);
        grid.add(productNameField, 1, 1);
        grid.add(quantityLabel, 0, 2);
        grid.add(quantityField, 1, 2);
        grid.add(placeOrderButton, 1, 3);
        grid.add(resultLabel, 1, 4);

        VBox layout = new VBox(10, grid);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private class PlaceOrderHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String customerName = customerNameField.getText().trim();
            String productName = productNameField.getText().trim();
            String quantityInput = quantityField.getText().trim();

            if (customerName.isEmpty() || productName.isEmpty() || quantityInput.isEmpty()) {
                resultLabel.setText("Please fill in all fields.");
                return;
            }

            try {      // convert into warrer class
                int quantity = Integer.parseInt(quantityInput);
                if (quantity <= 0) {
                    resultLabel.setText("Quantity must be greater than 0.");
                    return;
                }

                ArrayList<Product> products = Product.readProductsFromFile();
                Product selectedProduct = null;

                for (Product product : products) {
                    if (product.getName().equalsIgnoreCase(productName)) {
                        selectedProduct = product;
                        break;
                    }
                }

                if (selectedProduct == null) {
                    resultLabel.setText("Product not found. Please check the product name.");
                    return;
                }

                if (quantity > selectedProduct.getQuantity()) {
                    resultLabel.setText("Invalid quantity. Not enough stock available.");
                    return;
                }

                selectedProduct.setQuantity(selectedProduct.getQuantity() - quantity);
                selectedProduct.setSold(selectedProduct.getSold() + quantity);

                Product.writeProductsToFile(products);

                int orderId = Order.getOrderMap().size() + 1;
                double totalPrice = selectedProduct.getPrice() * quantity;
                String orderDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                Order newOrder = new Order(orderId, selectedProduct.getId(), quantity, "pending", totalPrice, orderDate, customerName);
                Order.getOrderMap().put(orderId, newOrder);

                Order.saveOrdersToFile();

                resultLabel.setText("Order placed successfully!\nOrder ID: " + orderId + "\nTotal Price: $" + totalPrice);
            } catch (NumberFormatException e) {
                resultLabel.setText("Please enter a valid numeric quantity.");
            } catch (Exception e) {
                resultLabel.setText("An error occurred: " + e.getMessage());
            }
        }
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Order.loadOrdersFromFile();
        Product.readProductsFromFile().forEach(product -> Product.getProductMap().put(product.getId(), product));
        launch(args);
    }
    
}