/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project;

/**
 *
 * @author MalakMostafaZaky
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.util.HashMap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
public class Displayproduct extends Application{
      @Override
    public void start(Stage primaryStage) {
  primaryStage.setTitle("Product Display");

        // TextArea to display products
        TextArea textArea = new TextArea();
        textArea.setEditable(false);

        // Load product data
        HashMap<Integer, Product> productMap = Product.getProductMap();
        Product.readProductsFromFile().forEach(product -> productMap.put(product.getId(), product));

        // Build display content
        StringBuilder displayContent = new StringBuilder();
        displayContent.append("Product List:\n\n");
        for (Product product : productMap.values()) {
            displayContent.append("ID: ").append(product.getId()).append("\n")
                    .append("Name: ").append(product.getName()).append("\n")
                    .append("Description: ").append(product.getDescription()).append("\n")
                    .append("Price: ").append(product.getPrice()).append("\n")
                    .append("Quantity: ").append(product.getQuantity()).append("\n")
                    .append("Sold: ").append(product.getSold()).append("\n")
                    .append("Revenue: ").append(product.getRevenue()).append("\n")
                    .append("----------------------------\n");
        }
        textArea.setText(displayContent.toString());

        // ScrollPane for TextArea
        ScrollPane scrollPane = new ScrollPane(textArea);// 34an atla3vw anzel
        scrollPane.setFitToWidth(true);

        // Back Button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> navigateTo(new SellerMenu(), primaryStage));

        // HBox for Back Button
        HBox backBox = new HBox(backButton);
        backBox.setAlignment(Pos.BOTTOM_RIGHT);
        backBox.setPadding(new Insets(10));

        // Layout using BorderPane
        BorderPane layout = new BorderPane();
        layout.setCenter(scrollPane);
        layout.setBottom(backBox);

        // Scene setup
        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Navigation function
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