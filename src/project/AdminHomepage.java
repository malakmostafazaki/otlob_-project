/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package project;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.Admin;

/**
 *
 * @author MalakMostafaZaky
 */
public class AdminHomepage extends Application {
    
  

    @Override
    public void start(Stage primaryStage) {
        // Set the title for the stage
        primaryStage.setTitle("Otlob - Admin Homepage");

        // Create buttons for each function
        Button btnAdd = new Button("Add");
        Button btnRemove = new Button("Remove");
        Button btnEdit = new Button("Edit");
        Button btnSearch = new Button("Search");
        Button btnViewMaxOrders = new Button("View Max Orders");
        Button btnViewMaxRevenue = new Button("View Max Revenue");

        // Add action listeners to navigate to corresponding classes
        btnAdd.setOnAction(e -> navigateTo(new AddAdmin(), primaryStage));
        btnRemove.setOnAction(e -> navigateTo(new RemoveAdmin(), primaryStage));
        btnEdit.setOnAction(e -> navigateTo(new EditAdmin(), primaryStage));
        btnSearch.setOnAction(e -> navigateTo(new SearchAdmin(), primaryStage));
        btnViewMaxOrders.setOnAction(e -> navigateTo(new MaxOrdersAdmin(), primaryStage));
        btnViewMaxRevenue.setOnAction(e -> navigateTo(new MostRevenueProduct(), primaryStage));

        // Arrange buttons in a vertical layout
        VBox vbox = new VBox(10, btnAdd, btnRemove, btnEdit, btnSearch, btnViewMaxOrders, btnViewMaxRevenue);
        vbox.setAlignment(Pos.CENTER);

        // Set the scene
        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    /**
     * Helper method to navigate to another JavaFX class.
     * 
     * @param function The new Application to navigate to.
     * @param primaryStage The current stage.
     */
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
