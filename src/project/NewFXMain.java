/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package project;


/**
 *
 * @author MalakMostafaZaky
 * 
 *
 *  */import javafx.geometry.*;
import java.util.*; // Import for List and Optional
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.Optional;
import java.util.Scanner;
import java.util.*;


public class NewFXMain extends Application {
    
    @Override
   
    public void start(Stage primaryStage) {
        HBox topBar = new HBox(20);
        topBar.setStyle("-fx-background-color: #F47C20;");
        topBar.setPrefHeight(80);
        Label title = new Label("Otlob");
        title.setStyle("-fx-text-fill: white; -fx-font-family: 'Arial Rounded MT Bold'; -fx-font-size: 35px;");
        title.setUnderline(true);
        topBar.setPadding(new Insets(20, 0, 0, 710));
        topBar.getChildren().add(title);

        // Main content
        HBox maincontent = new HBox(30);
        maincontent.setPadding(new Insets(0, 0, 0, 0));
        maincontent.setStyle("-fx-background-color: #FFF4F8;");

        VBox root = new VBox(10);
        root.setStyle("-fx-background-color: #FFF4F8;");

        ScrollPane scrollpane = new ScrollPane();
        scrollpane.setContent(maincontent);
        scrollpane.setFitToWidth(true);
        scrollpane.setStyle("-fx-background-color: #FFF4F8;");

        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);
        Label text = new Label("Welcome to Otlob Program!");
        text.setStyle("-fx-font-size: 40px; -fx-font-family: 'Arial Rounded MT Bold'; -fx-fill: #333333;");

        HBox loginButtons = new HBox(10);
        loginButtons.setAlignment(Pos.CENTER);
        Button bt1 = new Button("Login");
        bt1.setStyle(
                "-fx-background-color: #F47C20;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 20px;" +
                "-fx-padding: 10 20;"
        );

        bt1.setOnMouseEntered(e -> bt1.setStyle(
                "-fx-background-color: #D3661A;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 20px;" +
                "-fx-padding: 10 20;" +
                "-fx-cursor: hand;"
        ));

        bt1.setOnMouseExited(e -> bt1.setStyle(
                "-fx-background-color: #F47C20;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 20px;" +
                "-fx-padding: 10 20;"
        ));

        bt1.setOnAction(event -> {
            List<String> roles = Arrays.asList("Seller", "Customer", "Admin");
            ChoiceDialog<String> dialog = new ChoiceDialog<>("Seller", roles);
            dialog.setTitle("Login");
            dialog.setHeaderText("Select your role to continue");
            dialog.setContentText("Role:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(role -> {
                switch (role) {
                    case "Seller":
                        openLoginWindow("Seller Login");
                        break;
                    case "Customer":
                        openLoginWindow("Customer Login");
                        break;
                    case "Admin":
                        openLoginWindow("Admin Login");
                        break;
                }
            });
        });

        // Add button to layout
        loginButtons.getChildren().addAll(bt1);
        contentBox.getChildren().addAll(text, loginButtons);

        StackPane overlay = new StackPane();
        overlay.getChildren().addAll(scrollpane, contentBox);
        StackPane.setAlignment(contentBox, Pos.CENTER);
        root.getChildren().addAll(topBar, overlay);
        Scene scene = new Scene(root, 1300, 700);

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void openLoginWindow(String titleText) {
        Stage loginStage = new Stage();
        loginStage.setTitle(titleText);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        Label userLabel = new Label("Username:");
        GridPane.setConstraints(userLabel, 0, 0);
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter Username");
        GridPane.setConstraints(usernameField, 1, 0);

        Label passLabel = new Label("Password:");
        GridPane.setConstraints(passLabel, 0, 1);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        GridPane.setConstraints(passwordField, 1, 1);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 2);
        loginButton.setOnAction(e -> {
            System.out.println("Username: " + usernameField.getText());
            System.out.println("Password: " + passwordField.getText());
        });

        grid.getChildren().addAll(userLabel, usernameField, passLabel, passwordField, loginButton);
        Scene loginScene = new Scene(grid, 350, 200);

        loginStage.setScene(loginScene);
        loginStage.show();
    }
  
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
