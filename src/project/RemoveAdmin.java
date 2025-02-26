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
public class RemoveAdmin extends Application {
    
   

   

    private TextField emailField;
    private TextField UserTypeField;

    @Override
    public void start(Stage primaryStage) {
        
        Button btn = new Button();
        btn.setText("Remove");
        btn.setOnAction(new RemoveHandler());
         Button Backbtn = new Button("Back");
        Backbtn.setOnAction(e -> navigateTo(new AdminHomepage(), primaryStage));

       
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setHgap(10);
        grid.setVgap(10);

     
        Label emailLabel = new Label("Enter user's email & type to remove:");
        emailField = new TextField();
        UserTypeField = new TextField();

        
        grid.add(emailLabel, 0, 0);
        grid.add(emailField, 1, 0);
        grid.add(UserTypeField, 1, 2);
        grid.add(btn, 1, 3);
        grid.add(Backbtn,2,6);


        Scene scene = new Scene(grid, 400, 200);
        primaryStage.setTitle("Otlob - Admin - Remove");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public class RemoveHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            String emailToRemove = emailField.getText().trim();
             String userToRemove = UserTypeField.getText().trim();
            
            if (!emailToRemove.isEmpty()) {
                Admin.Remove(emailToRemove, userToRemove);
                System.out.println("Attempted to remove user with email: " + emailToRemove);
            } else {
                System.out.println("Please enter a valid email.");
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
