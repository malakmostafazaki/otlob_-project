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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author MalakMostafaZaky
 */
public class background extends Application {
   @Override
    public void start(Stage primaryStage) {
       String logoPath = "file:C:/Users/Norhan Khaled/Downloads/WhatsApp Image 2024-12-26 at 7.52.56 PM.jpeg";
        ImageView logo = new ImageView(new Image(logoPath));
        logo.setFitWidth(400); // Adjusted for fullscreen size
        logo.setPreserveRatio(true);

        // Buttons
        Button customerButton = createButton("Customer LogIn");
        Button sellerButton = createButton("Seller LogIn");
        Button adminButton = createButton("Admin LogIn");

        // Set action for Customer LogIn button // lanbda
        customerButton.setOnAction(e -> navigateTo(new CustomerLogin(), primaryStage));

        // Set action for Seller LogIn button
        sellerButton.setOnAction(e -> navigateTo(new SellerLogin(), primaryStage));

        // Set action for Admin LogIn button
        adminButton.setOnAction(e -> navigateTo(new Adminloginscreen(), primaryStage));

        // Button Layout
        VBox buttonLayout = new VBox(30, sellerButton, adminButton, customerButton); // Adjusted order
        buttonLayout.setAlignment(Pos.CENTER);

        // Main Layout
        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #8000FF, #0000FF);"); // Gradient background
        layout.setTop(createTopLayout(logo));
        layout.setCenter(buttonLayout);

        // Scene
        Scene scene = new Scene(layout);
        primaryStage.setTitle("Otlob Home Page");
        primaryStage.setScene(scene);

        // Fullscreen Mode
        primaryStage.setFullScreen(true); // Enables fullscreen
        primaryStage.show();
    }
 // 34an ysa3dni ahot el image feen 
    private VBox createTopLayout(ImageView logo) {
        VBox topLayout = new VBox();
        topLayout.setAlignment(Pos.TOP_CENTER);
        topLayout.setPadding(new Insets(50, 0, 0, 0)); // Adds 5 cm (50 px) padding from the top
        topLayout.getChildren().add(logo);
        return topLayout;
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font(30)); // Larger font size for fullscreen
        button.setStyle(
                "-fx-background-color: #FFFFFF; " +
                "-fx-text-fill: #0000FF; " +
                "-fx-background-radius: 15; " +
                "-fx-padding: 15 30;"
        );
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: #CCCCCC; " +
                "-fx-text-fill: #0000FF; " +
                "-fx-background-radius: 15; " +
                "-fx-padding: 15 30;"
        ));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: #FFFFFF; " +
                "-fx-text-fill: #0000FF; " +
                "-fx-background-radius: 15; " +
                "-fx-padding: 15 30;"
        ));
        return button;
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