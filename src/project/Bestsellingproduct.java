/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class Bestsellingproduct extends Application {
    
    private TextArea resultArea;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Best Selling Product");

        // Title Label
        Label titleLabel = new Label("Best Selling Product");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        // TextArea for displaying the best selling product
        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setWrapText(true);

        // Button to show the best selling product
        Button showButton = new Button("Show Best Selling Product");
        showButton.setOnAction(event -> displayBestSellingProduct());

        // Back Button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> navigateTo(new SellerMenu(), primaryStage));

        // Layout for the form
        VBox layout = new VBox(10);  // vertical 
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(titleLabel, showButton, resultArea);
   
        // HBox for Back Button
        HBox backBox = new HBox(backButton);
        backBox.setAlignment(Pos.BOTTOM_RIGHT);  // horizontal 
        backBox.setPadding(new Insets(10));

        // Using BorderPane to combine layouts
        BorderPane root = new BorderPane();
        root.setCenter(layout);
        root.setBottom(backBox);

        // Scene setup
        Scene scene = new Scene(root, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        Product.readProductsFromFile().forEach(product -> Product.getProductMap().put(product.getId(), product));
    }

    private void displayBestSellingProduct() {
        Product bestSellingProduct = Product.getBestSellingProduct();

        if (bestSellingProduct != null) {
            resultArea.setText("Best Selling Product:\n\n" + bestSellingProduct.toString());
        } else {
            resultArea.setText("No products available to determine the best seller.");
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