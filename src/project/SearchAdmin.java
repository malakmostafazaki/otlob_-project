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
public class SearchAdmin extends Application {
    
  

    private TextField emailField;
    private TextField UserTypeField;
    private Label resultLabel;

    @Override
    public void start(Stage primaryStage) {
        
        Button btn = new Button();
        btn.setText("Search");
        btn.setOnAction(new SearchHandler());
         Button Backbtn = new Button("Back");
        Backbtn.setOnAction(e -> navigateTo(new AdminHomepage(), primaryStage));

        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setHgap(10);
        grid.setVgap(10);

        
        Label emailLabel = new Label("Enter user's email to search for them:");
        emailField = new TextField();
        UserTypeField = new TextField();
        resultLabel = new Label();

        
        grid.add(emailLabel, 0, 0);
        grid.add(emailField, 1, 0);
        grid.add(UserTypeField, 1, 2);
        grid.add(btn, 1, 1);
        grid.add(resultLabel, 1, 2);
         grid.add(Backbtn,2,6);
       
        Scene scene = new Scene(grid, 400, 200);
        primaryStage.setTitle("Otlob - Admin - Search");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    public class SearchHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            String emailToSearch = emailField.getText().trim();
            String userToRemove = UserTypeField.getText().trim();
            
            if (!emailToSearch.isEmpty()) {
                
                if (Admin.Search(emailToSearch ,userToRemove) != "No user found with the given email.") {
                boolean found =true ;
                if (found) {
                    resultLabel.setText("User found: " + emailToSearch);
                } else {
                    resultLabel.setText("User not found.");
                }
            } else {
                resultLabel.setText("Please enter a valid email.");
            }}
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
