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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author MalakMostafaZaky
 */
public class SellerMenu extends Application {
    
   
   @Override
    public void start(Stage primaryStage) {
    primaryStage.setTitle("Seller Menu");

        // Title label
        Label titleLabel = new Label("Seller Menu");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Role dropdown
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll(
                "Add Product", //malak
                "Edit Product",//norhan
                "Remove Product",//norhan
                "Search Products", //malak
                "Display Products",// malak
                "Best Selling Product", //norhan
                "Total Revenue", //malak
                "Average Revenue",// malak
                "View All Orders",// malak
                "Change Order Status",//malak
                "Most Revenue Product",//norhan
                "Pieces Sold Over Specific Time" //norhan
        );
        roleComboBox.setPromptText("Select Action");

        // Action button
        Button goButton = new Button("Go");
        Label resultLabel = new Label("");

        // Back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> navigateTo(new SellerLogin(), primaryStage)); // Navigate to SellerLogin
        
        // Layout setup
        VBox menuLayout = new VBox(10);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setPadding(new Insets(20));
        menuLayout.getChildren().addAll(
                titleLabel, roleComboBox, goButton, backButton, resultLabel
        );

        // Button action for the "Go" button
        goButton.setOnAction(e -> {
            String selectedAction = roleComboBox.getValue();
            if (selectedAction != null) {
                switch (selectedAction) {
                    case "Add Product":
                        navigateTo(new addproduct(), primaryStage);
                        break;
                    case "Edit Product":
                        navigateTo(new EditProduct(), primaryStage);
                        break;
                    case "Remove Product":
                        navigateTo(new RemoveProduct(), primaryStage);
                        break;
                    case "Search Products":
                        navigateTo(new Search(), primaryStage);
                        break;
                    case "Display Products":
                        navigateTo(new Displayproduct(), primaryStage);
                        break;
                    case "Best Selling Product":
                        navigateTo(new Bestsellingproduct(), primaryStage);
                        break;
                    case "Total Revenue":
                        navigateTo(new totalrevenue(), primaryStage);
                        break;
                    case "Average Revenue":
                        navigateTo(new averagerevenue(), primaryStage);
                        break;
                    case "View All Orders":
                        navigateTo(new viewordersdetails(), primaryStage);
                        break;
                    case "Change Order Status":
                        navigateTo(new ChangeOrderStaute(), primaryStage);
                        break;
                    case "Most Revenue Product":
                        navigateTo(new MostRevenueProduct(), primaryStage);
                        break;
                    case "Pieces Sold Over Specific Time":
                        navigateTo(new SoldPices(), primaryStage);
                        break;
                    default:
                        resultLabel.setText("Invalid action selected.");
                }
            } else {
                resultLabel.setText("Please select an action.");
            }
        });

        // Scene setup
        Scene scene = new Scene(menuLayout, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Refactored navigateTo function
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