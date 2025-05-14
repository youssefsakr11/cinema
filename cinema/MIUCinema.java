/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package miucinema;

import javafx.application.Application;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author ok
 */
public class MIUCinema extends Application {
@Override
    public void start(Stage primaryStage) {
        // Create the initial scene using ReceptionistScene
        ReceptionistScene receptionistScene = new ReceptionistScene(new HBox(), 400, 200);

        // Set the initial scene to the primary stage
        primaryStage.setScene(receptionistScene);

        // Set the window title
        primaryStage.setTitle("Cinema Booking System");

        // Show the stage (window)
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
    
}
