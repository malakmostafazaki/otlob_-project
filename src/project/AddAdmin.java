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
import javafx.collections.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import project.Admin;


/**
 *
 * @author MalakMostafaZaky
 */
public class AddAdmin extends Application {
    
   

    private ComboBox<String> combo;
    private TextField email;
    private TextField username;
    private TextField password;
    private TextField address;
    private TextField typeOfProductsField;
    private GridPane grid;

    @Override
    public void start(Stage primaryStage) {
        
        String[] users = {"Admin", "Customer", "Seller"};
        combo = new ComboBox<>(FXCollections.observableArrayList(users));

       
        email = new TextField();
        username = new TextField();
        password = new TextField();
        address = new TextField();
        typeOfProductsField = new TextField();

        
        Button btn = new Button("Save");
        btn.setOnAction(new SaveHandler());
        Button Backbtn = new Button("Back");
        Backbtn.setOnAction(e -> navigateTo(new AdminHomepage(), primaryStage));
       
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

     
        Label emailLabel = new Label("Email:");
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        Label addressLabel = new Label("Address:");
        Label typeOfProductsLabel = new Label("Type of Products Sold:");

       
        grid.add(new Label("User Type:"), 0, 0);
        grid.add(combo, 1, 0);
        grid.add(emailLabel, 0, 1);
        grid.add(email, 1, 1);
        grid.add(usernameLabel, 0, 2);
        grid.add(username, 1, 2);
        grid.add(passwordLabel, 0, 3);
        grid.add(password, 1, 3);
        grid.add(addressLabel, 0, 4);
        grid.add(address, 1, 4);
        grid.add(btn, 1, 6);
        grid.add(Backbtn,2,6);

       
        combo.setOnAction(e -> {
            if ("Seller".equals(combo.getValue())) {
                if (!grid.getChildren().contains(typeOfProductsLabel)) {
                    grid.add(typeOfProductsLabel, 0, 5);
                    grid.add(typeOfProductsField, 1, 5);
                }
            } else {
                grid.getChildren().removeAll(typeOfProductsLabel, typeOfProductsField);
            }
        });

       
        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setTitle("Otlob - Admin - Add");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public class SaveHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            String userType = combo.getValue();
            String emails = email.getText().trim();
            String usernames = username.getText().trim();
            String passwords = password.getText().trim();
            String addresss = address.getText().trim();

            if (emails.isEmpty() || usernames.isEmpty() || passwords.isEmpty() || addresss.isEmpty() || userType == null) {
                System.out.println("Please fill in all fields.");
                return;
            }

            ArrayList<String> userDetails = new ArrayList<>();
            userDetails.add(emails);
            userDetails.add(usernames);
            userDetails.add(passwords);
            userDetails.add(addresss);

            if ("Seller".equals(userType)) {
                String typeOfProducts = typeOfProductsField.getText().trim();
                if (typeOfProducts.isEmpty()) {
                    System.out.println("Please enter the type of products sold.");
                    return;
                }
                userDetails.add(typeOfProducts);
                Admin.Map.put(userDetails, "Seller");
            } else {
                Admin.Map.put(userDetails, userType);
            }

            Admin.SaveToFile();
            System.out.println(userType + " saved successfully.");
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
