/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author MalakMostafaZaky
 */
public class CustomerLogin extends Application {
    
    @Override
    public void start(Stage primaryStage) {
           primaryStage.setTitle("Customer Login");

        // Create GridPane layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Add components
        Label usernameLabel = new Label("Username:");
        grid.add(usernameLabel, 0, 0);

        TextField usernameField = new TextField();
        grid.add(usernameField, 1, 0);

        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 1);

        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 1);

        Button loginButton = new Button("Login");
        grid.add(loginButton, 1, 2);

        Button backButton = new Button("Back");
        grid.add(backButton, 1, 3);

        Label statusLabel = new Label();
        grid.add(statusLabel, 1, 4);

        // Set event handling for login button
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Create a Customer object to call the login function
            Customer customer = new Customer();

            if (customer.customerLogin(username, password)) {
                statusLabel.setText("Login successful!");
                statusLabel.setTextFill(Color.GREEN);

                // Proceed to the customer menu or next screen
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Success");
                alert.setHeaderText(null);
                alert.setContentText("Welcome, " + username + "!");
                alert.showAndWait();

                primaryStage.close(); // Close the login window
            } else {
                statusLabel.setText("Invalid username or password.");
                statusLabel.setTextFill(Color.RED);
            }
        });

        // Set event handling for back button
        backButton.setOnAction(e -> navigateTo(new background(), primaryStage));

        // Create the scene and set it on the stage
        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
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