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
import javafx.scene.layout.*;
import javafx.stage.Stage;
import project.Admin;


/**
 *
 * @author MalakMostafaZaky
 */
public class EditAdmin extends Application {
 

    private TextField emailField;
    private TextField nameField;
    private TextField roleField;
     private TextField AddField;
    private Label resultLabel;

    @Override
    public void start(Stage primaryStage) {
        // Create Edit button
        Button btn = new Button();
        btn.setText("Edit");
        btn.setOnAction(new EditHandler());
         Button Backbtn = new Button("Back");
        Backbtn.setOnAction(e -> navigateTo(new AdminHomepage(), primaryStage));

        // Create GridPane layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setHgap(10);
        grid.setVgap(10);

        // Create input fields and labels
        Label emailLabel = new Label("Enter user's email to edit:");
        emailField = new TextField();

        Label nameLabel = new Label("Enter new name:");
        nameField = new TextField();

        Label roleLabel = new Label("Enter new role (Customer/Seller):");
        roleField = new TextField();
        
        AddField = new TextField();

        resultLabel = new Label();

        // Add elements to the grid
        grid.add(emailLabel, 0, 0);
        grid.add(emailField, 1, 0);
        grid.add(nameLabel, 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(roleLabel, 0, 2);
        grid.add(roleField, 1, 2);
        grid.add(AddField, 1, 3);
        grid.add(btn, 1, 4);
        grid.add(resultLabel, 1, 5);
        grid.add(Backbtn,2,6);

        // Create scene and set stage
        Scene scene = new Scene(grid, 500, 300);
        primaryStage.setTitle("Otlob - Admin - Edit");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Event handler for the edit action
    public class EditHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            String emailToEdit = emailField.getText().trim();
            String newName = nameField.getText().trim();
            String newRole = roleField.getText().trim();
            String newAdd = AddField.getText().trim();

            if (!emailToEdit.isEmpty() && !newName.isEmpty() && !newRole.isEmpty()) {
                boolean success = Admin.Edit(emailToEdit, newName, newRole, newAdd);
                if (success) {
                    resultLabel.setText("User updated successfully.");
                } else {
                    resultLabel.setText("User not found or update failed.");
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
