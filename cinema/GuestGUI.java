package miucinema;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

public class GuestGUI extends Application {
    private Guest currentGuest;
    @Override
     public void start(Stage primaryStage) {
        primaryStage.setTitle("Guest");
        Label welcomelabel1=new Label("Welcome, " + (currentGuest != null ? currentGuest.getUserName() : "Guest") + "!");
        welcomelabel1.setStyle("-fx-font-size: 20px; -fx-text-fill: red;");
        
        Label bookinghistory=new Label("Your Booking History: ");
        bookinghistory.setStyle("-fx-font-size: 16px; -fx-text-fill: red;");
        GridPane bookinghistoryGrid=new GridPane();
        bookinghistoryGrid.setHgap(10);
        bookinghistoryGrid.setVgap(10);
        bookinghistoryGrid.setPadding(new Insets(10));
        if(currentGuest!=null && currentGuest.historyOfbookings!=null){
            int row=0;
            for(Booking booking: currentGuest.historyOfbookings){
                Label bookinglabel=new Label(booking.toString());
                bookinglabel.setStyle("-fx-border-color: red; -fx-padding: 5; -fx-background-color: red; -fx-text-fill: white;");
                bookinghistoryGrid.add(bookinglabel, 0, row++);
            }
        }
        else{
            Label noBookingLabel = new Label("No booking history available.");
            noBookingLabel.setStyle("-fx-font-style: italic; -fx-text-fill: white;");
            bookinghistoryGrid.add(noBookingLabel, 0, 0);
        }
        
        Label rateBookingLabel = new Label("Rate a Booking:");
        rateBookingLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: red;");
        TextField bookingIdField = new TextField();
        bookingIdField.setPromptText("Booking ID");
        TextField bookingRatingField = new TextField();
        bookingRatingField.setPromptText("Rating (0-5)");
        Button rateBookingButton = new Button("Submit Rating");
        rateBookingButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        rateBookingButton.setOnAction(e->{
            try{
                int bookingId = Integer.parseInt(bookingIdField.getText());
                float rating = Float.parseFloat(bookingRatingField.getText());
                boolean found = false;
                if(currentGuest !=null){
                    for(Booking booking: currentGuest.historyOfbookings){
                        if(booking.getBookingID()==bookingId){
                            booking.setRating(rating);
                            Alert alert=new Alert(Alert.AlertType.INFORMATION,"Booking Rated Successfully");
                            alert.show();
                            found=true;
                            break;
                        }
                    }
                }
            if (!found) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Booking ID not found.");
                    alert.show();
            }
            }
            catch (NumberFormatException ex){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid input. Please enter valid numbers.");
                alert.show();
            }
        });
        
        HBox ratebookinginputs=new HBox(10,bookingIdField,bookingRatingField,rateBookingButton);
        
        Label rateMovieLabel = new Label("Rate Movies:");
        rateMovieLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: red;");
        VBox rateMovieBox = new VBox(10);
        if (currentGuest != null && currentGuest.historyOfbookings != null) {
            for (Booking booking : currentGuest.historyOfbookings) {
                HBox movieRatingRow = new HBox(10);
                Label movieLabel = new Label("Rate " + booking.getMovieName() + ":");
                movieLabel.setStyle("-fx-text-fill: red;");
                TextField movieRatingField = new TextField();
                movieRatingField.setPromptText("Rating (0-5)");
                Button movieRateButton = new Button("Submit");
                movieRateButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                
                movieRateButton.setOnAction(e->{
                    try {
                        float rating = Float.parseFloat(movieRatingField.getText());
                        booking.getMovie().setMovieRating(rating);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Movie rated successfully.");
                        alert.show();
                    } catch (NumberFormatException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid rating. Please try again.");
                        alert.show();
                    }
                });
                movieRatingRow.getChildren().addAll(movieLabel, movieRatingField, movieRateButton);
                rateMovieBox.getChildren().add(movieRatingRow);
            }
        } 
        else {
            
            Label noMoviesLabel = new Label("No movies available to rate.");
            noMoviesLabel.setStyle("-fx-text-fill: white;");
            rateMovieBox.getChildren().add(noMoviesLabel);
        }
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(10));
        mainLayout.setStyle("-fx-background-color: black;");
        mainLayout.getChildren().addAll(welcomelabel1, bookinghistory, bookinghistoryGrid, rateBookingLabel, ratebookinginputs, rateMovieLabel, rateMovieBox);

       
        Scene scene = new Scene(mainLayout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
  launch(args);
    }
    
}
