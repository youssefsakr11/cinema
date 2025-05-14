package miucinema;


import java.io.File;
import java.time.LocalDate;
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

public class AddMoviesScene {

    private final Stage stage;
    private final Scene scene;
    String imagePath = null;

    public AddMoviesScene(Stage stage) {
        this.stage = stage;

        VBox sidebar = SideBar.createSidebar(stage);

        // Center Pane
        VBox centerPane = new VBox(10); // Use VBox for vertical stacking
        centerPane.setPadding(new Insets(20));
        
        //Image viewer
        ImageView imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(150);
        Button importButton = new Button("Import");
            
        //TextFields
        TextField movieTitleField = new TextField();
        movieTitleField.setPromptText("Movie Title");
        TextField genreField = new TextField();
        genreField.setPromptText("Genre");
        TextField durationField = new TextField();
        durationField.setPromptText("Duration");
        
        //Buttons
        Button insertButton = new Button("Insert");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");
        Button clearButton = new Button("Clear");
        
        HBox actionButtons1 = new HBox(10, insertButton, updateButton);
        actionButtons1.setAlignment(Pos.CENTER);
        
        HBox actionButtons2 = new HBox(10, deleteButton, clearButton);
        actionButtons2.setAlignment(Pos.CENTER);
        
        centerPane.getChildren().addAll(imageView, importButton, 
            new Label("Movie Title:"), movieTitleField,
            new Label("Genre:"), genreField,
            new Label("Duration:"), durationField,
            actionButtons1, actionButtons2);

        // Table View
        TableView<Movie> movieTable = new TableView<>();
        TableColumn<Movie, String> titleColumn = new TableColumn<>("Movie Title");
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMovieName()));

        TableColumn<Movie, String> genreColumn = new TableColumn<>("Genre");
        genreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMovieGenre()));

        TableColumn<Movie, String> durationColumn = new TableColumn<>("Duration");
        durationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMovieDuration()));

       TableColumn<Movie, Integer> bookingsColumn = new TableColumn<>("Bookings");
       bookingsColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMovieBooking()).asObject());

        TableColumn<Movie, Double> revenueColumn = new TableColumn<>("Revenue");
        revenueColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getMovieRevenue()).asObject());
         
       movieTable.getColumns().addAll(titleColumn, genreColumn, durationColumn, bookingsColumn, revenueColumn);
        movieTable.setPrefWidth(450);
        movieTable.getItems().addAll(Movie.movies);

        TextField searchField = new TextField();
        searchField.setPromptText("Search");

        VBox rightPane = new VBox(10, searchField, movieTable);
        rightPane.setPadding(new Insets(20));

        movieTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    if (newValue != null) {
        // Populate the text fields with the selected movie's data
        movieTitleField.setText(newValue.getMovieName());
        genreField.setText(newValue.getMovieGenre());
        durationField.setText(newValue.getMovieDuration());
        if (newValue.getMovieCover() != null) {
            imagePath = newValue.getMovieCover();
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
        //~~EVENT HANDLING~~
        //
        
        //DELETE BUTTON
        deleteButton.setOnAction(e -> {
    String movieName = movieTitleField.getText().trim();
    if (movieName.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a movie name to delete.", ButtonType.OK);
        alert.showAndWait();
        return;
    }

    boolean found = false;
    for (int i = 0; i < Movie.movies.size(); i++) {
        if (Movie.movies.get(i).getMovieName().equalsIgnoreCase(movieName)) {
            Movie.movies.remove(i); // Remove the movie
            movieTable.getItems().remove(i); // Update the TableView
            found = true;

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Movie removed successfully.", ButtonType.OK);
            alert.showAndWait();
            break;
        }
    }

    if (!found) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Movie not found.", ButtonType.OK);
        alert.showAndWait();
    }
});

        
        //INSERT BUTTON
    insertButton.setOnAction(e -> {
    String title = movieTitleField.getText().trim();
    String genre = genreField.getText().trim();
    String duration = durationField.getText().trim();

    if (title.isEmpty() || genre.isEmpty() || duration.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill in all fields.", ButtonType.OK);
        alert.showAndWait();
        return;
    } 
    boolean movieExists = false;
    for (Movie movie : Movie.movies) {
        if (movie.getMovieName().equalsIgnoreCase(title)) {  // Case insensitive comparison
            movieExists = true;
            break;
        }
    }

    if (movieExists) {
        // Show an alert if the movie already exists
        Alert alert = new Alert(Alert.AlertType.WARNING, "This movie already exists.", ButtonType.OK);
        alert.showAndWait();
        return;
    }
    
    Movie newMovie = new Movie(title, genre, duration , imagePath); //ADD IMAGE PATH STRING VARIABLE TO MOVIE CLASS
    newMovie.createMovieID();
    Movie.movies.add(newMovie);
    //ADD SCREENING TIME
    movieTable.getItems().add(newMovie); // Update the TableView

    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Movie added successfully.", ButtonType.OK);
    alert.showAndWait();
});
    
    
    //
    //EDIT BUTTON
    //
    updateButton.setOnAction(e -> {
        Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
          if (selectedMovie == null) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a movie to edit.", ButtonType.OK);
        alert.showAndWait();
        return;
    }
          int selectedMovieID = selectedMovie.getMovieID();
          
    for (Movie movie : Movie.movies) {
        if (movie.getMovieID() == selectedMovieID) {
            if (!movie.getMovieName().equalsIgnoreCase(movieTitleField.getText().trim())) {
                movie.setMovieName(movieTitleField.getText().trim());
            }
            // Update fields only if they are different
            if (!movie.getMovieGenre().equalsIgnoreCase(genreField.getText().trim())) {
                movie.setMovieGenre(genreField.getText().trim());
            }
            if (!movie.getMovieDuration().equals(durationField.getText().trim())) {
                movie.setMovieDuration(durationField.getText().trim());
            }
            if (imagePath != null && !imagePath.equals(movie.getMovieCover())) {
                       movie.setMovieCover(imagePath);  // Update image path
                    }
            movieTable.refresh(); // Refresh TableView to reflect updates

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Movie updated successfully.", ButtonType.OK);
            alert.showAndWait();
            break;
   }
    }
});

    //CLEAR BUTTON
    clearButton.setOnAction(event -> {
    movieTitleField.clear();
    genreField.clear();
    durationField.clear();
    imageView.setImage(null); // Clear the image if any
});

    //
    //IMPORT BUTTON
    //
importButton.setOnAction(e -> {
    // Create a FileChooser
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
    fileChooser.setTitle("Select an Image");

    // Open the file chooser and get the selected file
    File selectedFile = fileChooser.showOpenDialog(stage);
    // If a file was selected, load and display it
    if (selectedFile != null) {
        // Create an Image object from the selected file
        Image image = new Image(selectedFile.toURI().toString());
        imagePath = selectedFile.getAbsolutePath();

        // Set the image to the ImageView
        imageView.setImage(image);
    }
});

    }

    public Scene getScene() {
        return scene;
    }
}

//understand receptionist class in order to link screening/shows to this scene
//ADD IMAGE PATH STRING VARIABLE TO MOVIE