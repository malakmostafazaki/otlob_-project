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
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SellerLogin extends Application{

    public SellerLogin() {
    }
    
    @Override
    public void start(Stage primaryStage) {
          primaryStage.setTitle("Seller Login");

        // Create a GridPane layout for the form
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        // Username Label and TextField
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");

        // Password Label and PasswordField
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        // Login Button
        Button loginButton = new Button("Login");

        // Back Button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> navigateTo(new background(), primaryStage)); // Navigate back to the background
        // Feedback Label
        Label feedbackLabel = new Label("");

        // Add components to the GridPane
        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 1, 2); // Align the button below the input fields
        grid.add(backButton, 1, 3); // Add the back button below the login button
        grid.add(feedbackLabel, 1, 4); // Feedback label below the buttons

        // Button action for login
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Create a new Seller instance and attempt login
            Seller seller = new Seller();
            boolean isLoginSuccessful = seller.login(username, password);

            if (isLoginSuccessful) {
                feedbackLabel.setText("Login successful!");
                navigateTo(new SellerMenu(), primaryStage); // Navigate to the seller menu
            } else {
                feedbackLabel.setText("Invalid username or password. Please try again.");
            }
        });

        // Scene setup
        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Refactored navigateTo function
    private void navigateTo(Application function, Stage primaryStage) {
        try {
            Stage newStage = new Stage();
            function.start(newStage); // Start the new application window
            primaryStage.close(); // Close the current window
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}