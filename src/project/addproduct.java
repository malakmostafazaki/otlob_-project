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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.HashMap;

public class addproduct extends Application {
     private TextField idField; // textfield di bada5l feeh el haga ely ana 3yzaha 
    private TextField nameField;
    private TextField descriptionField;
    private TextField priceField;
    private TextField quantityField;
    private Button saveButton;
    private Button backButton;

    @Override
    public void start(Stage primaryStage) {
        idField = new TextField();
        nameField = new TextField();
        descriptionField = new TextField();
        priceField = new TextField();
        quantityField = new TextField();
        saveButton = new Button("Save");
        backButton = new Button("Back");

        // Back Button Action
        backButton.setOnAction(e -> navigateTo(new SellerMenu(), primaryStage));

        GridPane grid = new GridPane();  // moraba3 gwah margin  da el setpadding 
        grid.setAlignment(Pos.CENTER);// hotoholi fi anhi position 
        grid.setPadding(new Insets(20));// maoraba3 da kol gamb mno 20pixel
        grid.setHgap(10);//ets horizontal spacing
        grid.setVgap(10);//ets horizontal spacing
 // get childrn leyout divide into row and colum each child componets divide into row and column 
        // Add fields and buttons to the grid
        grid.add(new Label("Product ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Product Name:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Description:"), 0, 2);
        grid.add(descriptionField, 1, 2);
        grid.add(new Label("Price:"), 0, 3);
        grid.add(priceField, 1, 3);
        grid.add(new Label("Quantity:"), 0, 4);
        grid.add(quantityField, 1, 4);
        grid.add(saveButton, 0, 5);
        grid.add(backButton, 1, 5);

        // Save Button Action
        saveButton.setOnAction(new SaveHandler());

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setTitle("Add Product");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public class SaveHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();
                String description = descriptionField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                int quantity = Integer.parseInt(quantityField.getText().trim());

                if (name.isEmpty() || description.isEmpty()) {
                    System.out.println("Please fill in all fields.");
                    return;
                }

                HashMap<Integer, Product> productMap = Product.getProductMap();
                Product.readProductsFromFile().forEach(product -> productMap.put(product.getId(), product));

                if (productMap.containsKey(id)) {
                    System.out.println("Product with this ID already exists.");
                    return;
                }

                Product newProduct = new Product(id, name, description, price, quantity, 0, 0.0);
                productMap.put(id, newProduct);
                Product.saveProductsToFile();

                System.out.println("Product added successfully!");
            } catch (NumberFormatException e) {
                System.out.println("Please enter valid numerical values for ID, price, and quantity.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    // Navigate to another screen
    private void navigateTo(Application function, Stage primaryStage) {
        try {
            Stage newStage = new Stage();
            function.start(newStage);
            primaryStage.close(); // Close the current window
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(args);
    }
    
}