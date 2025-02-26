/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package project;

/**
 *
 * @author MalakMostafaZaky
 */

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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Search extends Application {
    
      private TextField searchField;
    private ComboBox<String> searchCriteria;
    private TextArea resultArea;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Search Product");

        // Components
        Label criteriaLabel = new Label("Search By:");
        searchCriteria = new ComboBox<>();
        searchCriteria.getItems().addAll("ID", "Name", "Description", "Price", "Quantity", "Sold", "Revenue");
        searchCriteria.setValue("ID");

        Label searchLabel = new Label("Enter Search Value:");
        searchField = new TextField();

        Button searchButton = new Button("Search");
        searchButton.setOnAction(new SearchHandler());

        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setWrapText(true);

        // GridPane for input form
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(criteriaLabel, 0, 0);
        grid.add(searchCriteria, 1, 0);
        grid.add(searchLabel, 0, 1);
        grid.add(searchField, 1, 1);
        grid.add(searchButton, 1, 2);

        // Back Button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> navigateTo(new SellerMenu(), primaryStage));

        // HBox for Back Button
        HBox backBox = new HBox(backButton);
        backBox.setAlignment(Pos.BOTTOM_RIGHT);
        backBox.setPadding(new Insets(10));

        // Layout using BorderPane
        BorderPane layout = new BorderPane();
        layout.setCenter(new VBox(10, grid, resultArea));
        layout.setBottom(backBox);

        // Scene setup
        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private class SearchHandler implements EventHandler<ActionEvent> {//private inner class
        @Override
        public void handle(ActionEvent event) {//This method is called automatically when an action (e.g., button click) triggers the event handler
            String criteria = searchCriteria.getValue().toLowerCase();
            String value = searchField.getText().trim();

            if (value.isEmpty()) {
                resultArea.setText("Please enter a search value.");
                return;
            }
            boolean found = false;
            StringBuilder results = new StringBuilder("Search Results:\n\n");

            for (Product product : Product.getProductMap().values()) {
                boolean match = false;
                switch (criteria) {
                    case "id":
                        match = String.valueOf(product.getId()).contains(value);
                        break;
                    case "name":
                        match = product.getName().toLowerCase().contains(value.toLowerCase());
                        break;
                    case "description":
                        match = product.getDescription().toLowerCase().contains(value.toLowerCase());
                        break;
                    case "price":
                        match = String.valueOf(product.getPrice()).contains(value);
                        break;
                    case "quantity":
                        match = String.valueOf(product.getQuantity()).contains(value);
                        break;
                    case "sold":
                        match = String.valueOf(product.getSold()).contains(value);
                        break;
                    case "revenue":
                        match = String.valueOf(product.getRevenue()).contains(value);
                        break;
                }

                if (match) {//his is a StringBuilder object used to efficiently construct a string 
                    results.append(product).append("\n\n");
                    found = true;//Calls the toString() method of the product object to get its string representation(append)
                }
            }

            if (found) {
                resultArea.setText(results.toString());
            } else {
                resultArea.setText("No matching products found for " + criteria + ": " + value);
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
        // Load products from file into the map
        ArrayList<Product> products = Product.readProductsFromFile();
        for (Product product : products) {
            Product.getProductMap().put(product.getId(), product);
        }

        launch(args);
    }
}