/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miucinema;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author ok
 */
public class DashboardScene {
    
    private final Stage stage;
    private final Scene scene;

    public DashboardScene(Stage stage) {
        this.stage = stage;
        
         VBox sidebar = SideBar.createSidebar(stage);

        // Statistics 
        HBox statsBox = new HBox(20);
        statsBox.setAlignment(Pos.CENTER);
        statsBox.setPadding(new Insets(10));
        VBox ticketsSoldBox = createStatBlock("Total Tickets Sold", "file:src/Icons/ticket.png", "N/A"); //how do I upadate the N/A values, read it from an arraylist?
        VBox totalIncomeBox = createStatBlock("Total Revenue", "file:src/Icons/revenue.png", "N/A");
        VBox availableMoviesBox = createStatBlock("Available Movies", "file:src/Icons/movie.png", "N/A");

        statsBox.getChildren().addAll(ticketsSoldBox, totalIncomeBox, availableMoviesBox);

        // Rotating Images
        StackPane rotatingImagePane = new StackPane();
        rotatingImagePane.setStyle("-fx-border-color: transparent; -fx-border-width: 2; -fx-pref-height: 300;");
        ImageView rotatingImage = new ImageView();
        rotatingImage.setFitWidth(400);
        rotatingImage.setPreserveRatio(true);

        String[] images = {"file:src/Icons/dark_knight.jpg", "file:src/Icons/end_game.jpg", "file:src/Icons/oppenheimer.jpg"}; //how do I update this with every movie added?

        final int[] currentIndex = {0};
        rotatingImage.setImage(new Image(images[currentIndex[0]]));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            int nextIndex = (currentIndex[0] + 1) % images.length;
            Image nextImage = new Image(images[nextIndex]);

            TranslateTransition slideOut = new TranslateTransition(Duration.seconds(1), rotatingImage);
            slideOut.setFromX(0);
            slideOut.setToX(-100);

            TranslateTransition slideIn = new TranslateTransition(Duration.seconds(1), rotatingImage);
            slideIn.setFromX(150);
            slideIn.setToX(0);

            slideOut.setOnFinished(event -> {
                rotatingImage.setImage(nextImage);
                slideIn.play();
            });

            slideOut.play();
            currentIndex[0] = nextIndex;
        }));
        timeline.setCycleCount(Timeline.INDEFINITE); //runs infinitely
        timeline.play();
        rotatingImagePane.getChildren().add(rotatingImage);

        VBox centerContent = new VBox(20);
        centerContent.setAlignment(Pos.CENTER);
        centerContent.getChildren().addAll(statsBox, rotatingImagePane);

        
        BorderPane layout = new BorderPane();
        layout.setLeft(sidebar);
        layout.setCenter(centerContent);

    this.scene = new Scene(layout, 800, 600);
    }

    private VBox createStatBlock(String label, String imageName, String value) {
        VBox statBox = new VBox(10);
        statBox.setAlignment(Pos.CENTER);
        statBox.setPadding(new Insets(10));
        statBox.setStyle("-fx-background-color: #EFEFEF; -fx-padding: 10; -fx-border-color: transparent; -fx-border-width: 1;");

        ImageView icon = new ImageView(new Image(imageName));
        icon.setFitWidth(50);
        icon.setPreserveRatio(true);

        javafx.scene.control.Label statLabel = new javafx.scene.control.Label(label);
        statLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

        javafx.scene.control.Label statValue = new javafx.scene.control.Label(value);
        statValue.setStyle("-fx-font-size: 18;");

        statBox.getChildren().addAll(icon, statLabel, statValue);
        return statBox;
    }
    
        public Scene getScene() {
        return scene;
    }
}
