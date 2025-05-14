package miucinema;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.util.ArrayList;
import javafx.beans.property.*;
import javafx.util.StringConverter;

public class EditScreeningScene {

    private final Stage stage;
    private final Scene scene; 
    String imagePath = null;

    public EditScreeningScene(Stage stage) {
        this.stage = stage;

        VBox sidebar = SideBar.createSidebar(stage);

        // Center Pane
        VBox centerPane = new VBox(10);
        centerPane.setPadding(new Insets(20));
        
        //Image viewer
        ImageView imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(150);
        
        //COMBO BOX
        ComboBox<String> hallComboBox = new ComboBox<>();
        hallComboBox.getItems().addAll("MIU", "IMAX", "VOX", "VIP");
        hallComboBox.setPromptText("Select Hall");
       hallComboBox.setStyle(
         "-fx-background-color: white; " +                    
          "-fx-border-color: silver; " +                     
         "-fx-border-width: 1.2; " +                        
         "-fx-font-size: 12px; " +                          
          "-fx-text-fill: #333333; " +       //color of text          
          "-fx-border-radius: 5; " +                      
          "-fx-background-radius: 5; "     //rounded corners           
);
        
        
        //TextFields
         TextField movieTitleField = new TextField();
        movieTitleField.setPromptText("Movie Title");

        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setPromptText("Screening Date");
        
        //what is LocalTime ? 
        //Is this an inner/annonymous class?
        //setvaluefactory manages the value of a control
        //TIME SPINNER
Spinner<LocalTime> startTimeSpinner = new Spinner<>();
startTimeSpinner.setValueFactory(new SpinnerValueFactory<>() { //Inner class 
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); //sets formate of timer spinner
    private LocalTime value = LocalTime.of(10, 0); // Default start time, LocalTime stores value
    {
        setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalTime time) {
                return time == null ? "" : time.format(formatter);
            }
            @Override
            public LocalTime fromString(String string) {
                return string.isEmpty() ? null : LocalTime.parse(string, formatter);
            }
        });
        setValue(value);
    }
    @Override
    public void increment(int steps) {
        if (value != null) {
            value = value.plusMinutes(steps * 5); // Increment by 5 minutes
            setValue(value);
        }
    }
    @Override
    public void decrement(int steps) {
        if (value != null) {
            value = value.minusMinutes(steps * 5); // Decrement by 5 minutes
            setValue(value);
        }
    }
});
        
        Slider priceSlider = new Slider(5, 50, 15); // Set price range, min is 5 max is 50, default is 15
        priceSlider.setShowTickMarks(true); //shows the small vertical lines (marks)
        priceSlider.setShowTickLabels(true); //shows number labels
        priceSlider.setMajorTickUnit(5); //shows label with an increment of 5 each label
        
        
        //what is %.2f , what happens if we remove obs & oldval and only leave newVal
                Label priceLabel = new Label("Price: $15.00");
        priceSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            priceLabel.setText(String.format("Price: $%.2f", newVal.doubleValue()));
        }); 
        //doubleValue() won't work if obs & oldVal aren't there
       
        
        //Buttons
        Button insertButton = new Button("Insert");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");
        Button clearButton = new Button("Clear");
        
        HBox actionButtons1 = new HBox(10, insertButton, updateButton);
        actionButtons1.setAlignment(Pos.CENTER);
        
        HBox actionButtons2 = new HBox(10, deleteButton, clearButton);
        actionButtons2.setAlignment(Pos.CENTER);
        
    centerPane.getChildren().addAll(imageView,
            new Label("Movie Title:"), movieTitleField,
            new Label("Hall: ") , hallComboBox,
            new Label("Day:"), startDatePicker,
            new Label("Start Time:"), startTimeSpinner,
            new Label("Price:"), priceSlider, priceLabel,
            actionButtons1, actionButtons2);

        // Table View
        // Define the TableView for screenings
    TableView<Show> screeningTable = new TableView<>();

// Define columns

            TableColumn<Show, String> movieNameColumn = new TableColumn<>("Movie Name");
            movieNameColumn.setCellValueFactory(cellData -> 
           new SimpleStringProperty(cellData.getValue().getMovie().getMovieName()));

            TableColumn<Show, String> durationColumn = new TableColumn<>("Duration");
            durationColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getMovie().getMovieDuration()));

            TableColumn<Show, LocalDate> dayColumn = new TableColumn<>("Day");
            dayColumn.setCellValueFactory(cellData -> 
            new SimpleObjectProperty<>(cellData.getValue().getDay()));

            TableColumn<Show, LocalTime> startTimeColumn = new TableColumn<>("Start Time");
            startTimeColumn.setCellValueFactory(cellData -> 
             new SimpleObjectProperty<>(cellData.getValue().getShowTime()));

            TableColumn<Show, String> hallColumn = new TableColumn<>("Hall");
            hallColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getHall()));
            
            TableColumn<Show, Double> priceColumn = new TableColumn<>("Price");
            priceColumn.setCellValueFactory(cellData -> 
            new SimpleDoubleProperty(cellData.getValue().getTicketPrice()).asObject());

            TableColumn<Show, Double> revenueColumn = new TableColumn<>("Revenue");
            revenueColumn.setCellValueFactory(cellData -> 
            new SimpleDoubleProperty(cellData.getValue().getRevenue()).asObject());

            TableColumn<Show, Integer> bookingsColumn = new TableColumn<>("Bookings");
            bookingsColumn.setCellValueFactory(cellData -> 
             new SimpleIntegerProperty(cellData.getValue().getNumOfBooking()).asObject());
            
            // Add all columns to the table
            screeningTable.getColumns().addAll(movieNameColumn , durationColumn , startTimeColumn, dayColumn , hallColumn, priceColumn, bookingsColumn ,revenueColumn);
            screeningTable.setPrefWidth(450);

            // Populate the table with data
            screeningTable.getItems().addAll(Show.shows); // Assuming `Show.shows` contains the list of `Show` objects.
        
            TextField searchField = new TextField();
        searchField.setPromptText("Search");

        VBox rightPane = new VBox(10, searchField, screeningTable);
        rightPane.setPadding(new Insets(20));
        
        
       //addListener gets triggered when an action that changes value occurs -> In this case, it is used to "listen" to the selection (click) changes of screeningTable
       //SelectionModel keeps track of the selected items
       //SelectedItemProperty() returns a "property" representing the selected item
    screeningTable.getSelectionModel().selectedItemProperty().addListener((obs, curValue , newValue) -> { //addListener MUST take 3 parameters
//obs is the observable value that triggers the change , oldValue is the old(current to be changed) value , newValue is the value that will be set to the current value (so it becomes old)
    if (newValue != null) {
        // Set Movie Title
        movieTitleField.setText(newValue.getMovie().getMovieName());

        // Set Date Picker
        startDatePicker.setValue(newValue.getDay());

        // Set Price Slider and Label
        priceSlider.setValue(newValue.getTicketPrice());
        priceLabel.setText(String.format("Price: $%.2f", newValue.getTicketPrice())); //%.2f means two decimal place double

        // Set Hall ComboBox
        hallComboBox.setValue(newValue.getHall());

        // Set Start Time Spinner
        startTimeSpinner.getValueFactory().setValue(newValue.getShowTime());

        // Set ImageView (if applicable)
        if (newValue.getMovie().getMovieCover() != null) {
            imagePath = newValue.getMovie().getMovieCover();
            File imageFile = new File(imagePath);
            imageView.setImage(new Image(imageFile.toURI().toString()));
        }
    }
});
    
        // Main Layout
        BorderPane root = new BorderPane();
        root.setLeft(sidebar);
        root.setCenter(centerPane);
        root.setRight(rightPane);

        this.scene = new Scene(root, 900, 600);
        
//
//DELETE BUTTON
//
      deleteButton.setOnAction(e -> {
     Show selectedShow = screeningTable.getSelectionModel().getSelectedItem();
    String movieName = movieTitleField.getText().trim();
    if (movieName.isEmpty()) {
        showAlert(Alert.AlertType.WARNING , "Error" , "Please Fill Credentials.");
        return;
    }

    boolean found = false;
    int selectedShowID = selectedShow.getShowID();
    for (int i = 0; i < Show.shows.size(); i++) {
        if (Show.shows.get(i).getShowID() == selectedShowID) {
            Show.shows.remove(i); // Remove the screening
            screeningTable.getItems().remove(i); // remove it from the TableView
            found = true;

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Show removed successfully.", ButtonType.OK);
            alert.showAndWait();
            break;
        }
    }

    if (!found) {
         showAlert(Alert.AlertType.WARNING , "Error" , "Show Doesn't Exist.");
    }
});

           
//
//CLEAR BUTTON
//
//movieTitleField , hallComboBox , startDatePicker , startTimeSpinner , priceSlider , priceLabel
    clearButton.setOnAction(event -> {
    movieTitleField.clear();
    hallComboBox.setValue(null);
    startDatePicker.setValue(null);
    startTimeSpinner.getValueFactory().setValue(LocalTime.of(10, 0)); //default time
    priceSlider.setValue(15); //default price
    imageView.setImage(null); // Clear the image if any
    ///////////////////////////////////////////////////
    screeningTable.getSelectionModel().clearSelection(); 
    ///////////////////////////////////////////////////
});
    
//
//EDIT BUTTON
//
 updateButton.setOnAction(e -> {
        Show selectedShow = screeningTable.getSelectionModel().getSelectedItem(); //fetch selected objecet/row
          if (selectedShow == null) {
        showAlert(Alert.AlertType.WARNING, "Error" , "Please select a movie to edit.");
        return;
    }
          int selectedShowID = selectedShow.getShowID();
          /*     new Label("Movie Title:"), movieTitleField,
            new Label("Hall: ") , hallComboBox,
            new Label("Day:"), startDatePicker,
            new Label("Start Time:"), startTimeSpinner,
            new Label("Price:"), priceSlider, priceLabel,*/
    for (Show show : Show.shows) {
        if (show.getShowID() == selectedShowID) {
            if (!show.getMovie().getMovieName().equalsIgnoreCase(movieTitleField.getText().trim())) {
                show.getMovie().setMovieName(movieTitleField.getText().trim());
            }
            // Update fields only if they are different
              if (!show.getDay().equals(startDatePicker.getValue())) {
                show.setDay(startDatePicker.getValue());
            }

            if (!show.getShowTime().equals(startTimeSpinner.getValue())) {
                show.setShowTime(startTimeSpinner.getValue());
            }

            if (!show.getHall().equals(hallComboBox.getValue())) {
                show.setHall(hallComboBox.getValue());
            }

            if (show.getTicketPrice() != priceSlider.getValue()) {
                show.setTicketPrice((float)priceSlider.getValue());
            }
              if (imagePath != null && !imagePath.equals(show.getMovie().getMovieCover())) {
                show.getMovie().setMovieCover(imagePath);
            }
            showAlert(Alert.AlertType.INFORMATION , "Succesful Edit" , "Updated Successfully!");
            screeningTable.refresh(); // Refresh TableView to view updates
            break;
   }
    }
});
 
 //
 //INSERT BUTTON
 //
 insertButton.setOnAction(e -> {
    // Validate input fields
    if (movieTitleField.getText().isEmpty() || 
        hallComboBox.getValue() == null || 
        startDatePicker.getValue() == null || 
        startTimeSpinner.getValue() == null) {
        showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled.");
        return;
    }

    // Create a new Show object
    //(int showid,String showname, LocalTime startTime, LocalDate day , String hall,int ticketprice,int totalseats)
     Show newShow = new Show(startTimeSpinner.getValue() , startDatePicker.getValue() , hallComboBox.getValue() , (float)priceSlider.getValue() , 0);
     newShow.getMovie().setMovieName(movieTitleField.getText().trim());
     newShow.getMovie().setMovieCover(imagePath != null ? imagePath : null);
    // Add the new Show to the table and data source
    screeningTable.getItems().add(newShow);
    Show.shows.add(newShow); // Assuming Show.shows is the data source
});
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText("Error");
    alert.setContentText(message);
    //alert.setButtonType("OK");
    alert.showAndWait();
}

    public Scene getScene() {
        return scene;
    }
}