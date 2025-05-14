package miucinema;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class SideBar extends Application{

    public static VBox createSidebar(Stage stage) {
        Movie.readMovie();
        Show.saveshow();
        VBox sidebar = new VBox(20); //for profile icon
        sidebar.setStyle("-fx-background-color: #7D1515; -fx-padding: 20;");
        sidebar.setAlignment(Pos.CENTER);
        
        ImageView logo = new ImageView(new Image("file:src/Icons/profile.png"));
        logo.setFitWidth(125);
        logo.setPreserveRatio(true);
        Label welcomeLabel = new Label("Welcome, Admin"); //I want to make this "Welcome, AdminName"
        welcomeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18;");
        
        //These buttons will change the scene
        //7oto buttons 3ala asas el requirments bta3tkom
        //maslan receptionist hyob2a 3ndo create booking w cancel booking buttons
        //maslan guest hyob2a 3ndo view bookings w rate receptionist buttons
        Button dashboardButton = new Button("Dashboard");
        Button addMoviesButton = new Button("Add Movies");
        Button customersButton = new Button("Customers");
        Button editScreeningButton = new Button("Edit Screening");
        Button accountsButton = new Button("Accounts");
        Button signOutButton = new Button("Sign Out");
        signOutButton.setStyle("-fx-background-color: #A00000; -fx-text-fill: white;");
        
        //y iterate through el buttons w y set their style
        //ana 3mlto f for loop ashan el buttons 3ndy ktyr
        for (Button button : new Button[]{dashboardButton, addMoviesButton, customersButton, editScreeningButton, accountsButton}) {
            button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16;");
            button.setPrefWidth(150);
        }

        // Add event handlers to navigate between scenes
while(true){ //so that you can iterate between scenes infinitely
        dashboardButton.setOnAction(e -> stage.setScene(new DashboardScene(stage).getScene()));
        addMoviesButton.setOnAction(e -> stage.setScene(new AddMoviesScene(stage).getScene()));
        //customersButton.setOnAction(e -> stage.setScene(new CustomersScene(stage).getScene()));
        
        
        editScreeningButton.setOnAction(e -> stage.setScene(new EditScreeningScene(stage).getScene()));
        //accountsButton.setOnAction(e -> stage.setScene(new AccountsScene(stage).getScene()));
        signOutButton.setOnAction(e -> Movie.writeMovie()); //When I have login GUI, I will make this put you back to login
        
        //add kol el buttons, wel icon wel logos f VBox wa7d
        sidebar.getChildren().addAll(logo, welcomeLabel, dashboardButton, addMoviesButton, customersButton, editScreeningButton, accountsButton , signOutButton);
        return sidebar;
    }
    }
    
     @Override
    public void start(Stage primaryStage) {
        //This is so I can run my GUI code, until we make a main class
        // To start with the Dashboard scene
        DashboardScene dashboardScene = new DashboardScene(primaryStage);
        primaryStage.setTitle("Admin Menu");
        primaryStage.setScene(dashboardScene.getScene());
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

