/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miucinema;
import java.time.LocalTime;
import java.util.Optional;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.*;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;

public class ReceptionistScene extends Scene {
    
    Label l = new Label("What do you need to do?");
    Button create = new Button("Create Booking");
    Button cancel = new Button("Cancel Booking");
    Button exit = new Button("Exit");

    HBox hb = new HBox(15, create, cancel);

    public ReceptionistScene(Parent parent, double d1, double d2) {
        super(parent, d1, d2);
        l.setFont(Font.font("Arial", FontWeight.BOLD, 18)); 
        create.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 16));
        cancel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 16)); 
        exit.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 16)); 

        create.setPrefWidth(150);
        cancel.setPrefWidth(150);
        exit.setPrefWidth(150);
        create.setPrefHeight(40);
        cancel.setPrefHeight(40);
        exit.setPrefHeight(40);

      create.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-color: #388E3C; -fx-border-width: 2px;");
      cancel.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-border-color: #D32F2F; -fx-border-width: 2px;");
      exit.setStyle("-fx-background-color: lightgray; -fx-text-fill: black; -fx-border-color: black; -fx-border-width: 2px;");
      
      create.setOnAction(e->{
      Stage stage = (Stage) create.getScene().getWindow();
      stage.setScene(createBooking());
      });
      
      exit.setOnAction(e -> {
            Stage stage = (Stage) exit.getScene().getWindow();
            stage.close();
        });
      
      cancel.setOnAction(e->{
      Stage stage = (Stage) cancel.getScene().getWindow();
      stage.setScene( cancelbooking());
      });

        hb.setPadding(new Insets(5, 15, 10, 15));
        hb.setAlignment(Pos.CENTER);
        
        VBox vb = new VBox(15, l, hb, exit);
        vb.setPadding(new Insets(10));
        vb.setAlignment(Pos.CENTER);
        setRoot(vb);
    }
public Scene createBooking() {
    
    Label guestname = new Label("Enter guest username: ");
    guestname.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    guestname.setStyle("-fx-text-fill: #2C3E50;");

    TextField gname = new TextField();
    gname.setStyle("-fx-background-color: #ECF0F1; -fx-border-color: #BDC3C7;");
    gname.setPrefHeight(40);

    Label gpass = new Label("Enter guest password: ");
    gpass.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    gpass.setStyle("-fx-text-fill: #2C3E50;");

    PasswordField pass = new PasswordField();
    pass.setStyle("-fx-background-color: #ECF0F1; -fx-border-color: #BDC3C7;");
    pass.setPrefHeight(40);

   
    Label moviename = new Label("Select movie name: ");
    moviename.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    moviename.setStyle("-fx-text-fill: #2C3E50;");

    ObservableList<String> mnames = FXCollections.observableArrayList();
    for (Movie m : Movie.movies) {
        mnames.add(m.getMovieName());
    }
   ComboBox<String> cbm = new ComboBox<>(mnames);
cbm.setStyle("-fx-background-color: #ECF0F1; -fx-border-color: #BDC3C7;");
cbm.setPrefHeight(40);
cbm.setPrefWidth(200);

    Label showname = new Label("Select the show: ");
    showname.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    showname.setStyle("-fx-text-fill: #2C3E50;");

    ObservableList<LocalTime> showtime = FXCollections.observableArrayList();
    for (Show s : Show.shows) {
        showtime.add(s.getShowTime());
    }
    
ComboBox<LocalTime> cbs = new ComboBox<>(showtime);
cbs.setStyle("-fx-background-color: #ECF0F1; -fx-border-color: #BDC3C7;");
cbs.setPrefHeight(40);
cbs.setPrefWidth(200);

    Label seats = new Label("Enter how many seats to book: ");
    seats.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    seats.setStyle("-fx-text-fill: #2C3E50;");

   TextField s = new TextField();
   s.setPrefColumnCount(2);  
   s.setMaxWidth(100);  
   s.setStyle("-fx-background-color: #ECF0F1; -fx-border-color: #BDC3C7;");
   s.setPrefHeight(40);
   s.setTranslateX(42);

    GridPane grid = new GridPane();
    grid.setVgap(15);  
    grid.setHgap(15); 
    grid.setPadding(new Insets(20));
    grid.setStyle("-fx-background-color: #F7F9F9;");
    grid.add(guestname, 0, 0);
    grid.add(gname, 1, 0);
    grid.add(gpass, 0, 1);
    grid.add(pass, 1, 1);
    grid.add(moviename, 0, 2);
    grid.add(cbm, 1, 2);
    grid.add(showname, 0, 3);
    grid.add(cbs, 1, 3);
    grid.add(seats, 0, 4);
    grid.add(s, 1, 4);

    Button submit = new Button("Submit");
    Button cancel = new Button("Cancel");

    submit.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-color: #388E3C; -fx-border-width: 2px;");
    cancel.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-border-color: #D32F2F; -fx-border-width: 2px;");
    submit.setPrefWidth(150);
    cancel.setPrefWidth(150);
    submit.setPrefHeight(40);
    cancel.setPrefHeight(40);
    
     submit.setOnAction(e->{
      bookingAlert();
      Stage stage = (Stage) submit.getScene().getWindow();
      stage.setScene(new ReceptionistScene(new HBox(),400,200));
      });
     
      cancel.setOnAction(e->{
      Stage stage = (Stage) submit.getScene().getWindow();
      stage.setScene(new ReceptionistScene(new HBox(),400,200));
      });
    
    HBox buttonBox = new HBox(20, submit, cancel);
    buttonBox.setAlignment(Pos.CENTER);

    grid.add(buttonBox, 0, 5, 2, 1);
    grid.setAlignment(Pos.CENTER);

    Scene createBookingScene = new Scene(grid, 400, 400);
    return createBookingScene;
}

   private void bookingAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Booking Successful");
        alert.setHeaderText(null);
        alert.setContentText("The booking has been successfully created!");
        alert.showAndWait();
    }
public Scene cancelbooking(){ 
   Label message = new Label("Enter the id of the booking you want to ");
   Label cancel = new Label("CANCEL");
   cancel.setStyle("-fx-text-fill: #e61809; -fx-underline: true; -fx-font-family: 'Impact'; -fx-font-size: 20px; -fx-font-weight: bold;");
   message.setFont(Font.font("Arial", FontWeight.BOLD, 16));
   message.setStyle("-fx-text-fill: #2C3E50;");
   Button submit = new Button("Submit");
   Button cancel2 = new Button("Cancel");
   submit.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-color: #388E3C; -fx-border-width: 2px;");
   cancel2.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-border-color: #D32F2F; -fx-border-width: 2px;");
   submit.setPrefWidth(150);
   cancel2.setPrefWidth(150);
   submit.setPrefHeight(40);
   cancel2.setPrefHeight(40);
    
   TextField txt = new TextField();
   txt.setPrefWidth(250);
   txt.setStyle("-fx-background-color: #ECF0F1; -fx-border-color: #BDC3C7;");
   txt.setPrefHeight(40);
    
   submit.setOnAction(e -> {
       Alert alert = new Alert(AlertType.WARNING);
       alert.setTitle("Cancel confirmation");
       alert.setHeaderText("submiting will permenently delete this booking.");
       alert.setContentText("Are you sure you want to cancel this booking?");
       
       Optional<ButtonType> result = alert.showAndWait();
       Stage stage = (Stage) submit.getScene().getWindow();
       
       if (result.isPresent() && result.get() == ButtonType.OK) {
           for(Booking b : Booking.bookings) {
               if(b.getBookingID() == Integer.parseInt(txt.getText())) {
                   Booking.bookings.remove(b);
                   break;
               }
           }
       }
       stage.setScene(new ReceptionistScene(new HBox(), 400, 200));
   });
    
   cancel2.setOnAction(e -> {
       Stage stage = (Stage) cancel2.getScene().getWindow();
       stage.setScene(new ReceptionistScene(new HBox(), 400, 200));
   });

   HBox hb = new HBox(5, message, cancel);
   hb.setAlignment(Pos.CENTER); 

   HBox hb2 = new HBox(5, submit, cancel2);
   hb2.setAlignment(Pos.CENTER);

   VBox vb = new VBox(20, hb, txt, hb2);
   vb.setAlignment(Pos.CENTER);  
   vb.setPadding(new Insets(20));

   Scene cancelbookingscene = new Scene(vb, 500, 300); 
   return cancelbookingscene;
}
}