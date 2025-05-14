/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miucinema;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;

public class Login extends Application {

    private StackPane formsContainer;
    private VBox loginForm;
    private VBox signupForm;
    private VBox forgotPasswordForm;
    private BorderPane mainPane;
    private VBox leftPane;
    private ComboBox<String> loginRoleComboBox;
    private ComboBox<String> signupRoleComboBox;

    @Override
    public void start(Stage primaryStage) {
        // Main container
        mainPane = new BorderPane();

        // Left Pane
        leftPane = createLeftPane();
        mainPane.setLeft(leftPane);

        // Forms container
        formsContainer = new StackPane();

        // Create Login, Signup, and Forgot Password forms
        loginForm = createLoginForm();
        signupForm = createSignupForm();
        forgotPasswordForm = createForgotPasswordForm();

        formsContainer.getChildren().add(loginForm);
        mainPane.setCenter(formsContainer);

        Scene scene = new Scene(mainPane, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MIU Cinema - Login & Signup");
        primaryStage.show();
    }

    private VBox createLeftPane() {
        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);
        pane.setPadding(new Insets(50));
        pane.setStyle("-fx-background-color: #000000;");

        Label title = new Label("Welcome to MIU Cnnema");
        title.setFont(new Font(24));
        title.setTextFill(Color.WHITE);

        Label description = new Label("Your gateway to cinematic experiences. Sign up or log in to start.");
        description.setTextFill(Color.LIGHTGRAY);
        description.setWrapText(true);
        description.setAlignment(Pos.CENTER);

        Button toggleButton = new Button("Sign Up");
        toggleButton.setStyle("-fx-background-color: #FF4B2B; -fx-text-fill: white;");
        toggleButton.setOnAction(e -> {
            if (formsContainer.getChildren().contains(loginForm)) {
                switchToSignup();
                toggleButton.setText("Log In");
            } else {
                switchToLogin();
                toggleButton.setText("Sign Up");
            }
        });

        pane.getChildren().addAll(title, description, toggleButton);
        return pane;
    }

    private VBox createLoginForm() {
        VBox form = new VBox(10);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(30));
        form.setStyle("-fx-background-color: #8B0000;");

       Label title = new Label();
        title.setFont(new Font(24));
        title.setTextFill(Color.WHITE);

        TextField username = new TextField();
        username.setPromptText("Username/Email");

        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        loginRoleComboBox = new ComboBox<>();
        loginRoleComboBox.getItems().addAll("Admin", "Receptionist", "Guest");
        loginRoleComboBox.setPromptText("Select Role");

        Button loginButton = new Button("Sign In");
        loginButton.setStyle("-fx-background-color: #FF4B2B; -fx-text-fill: white;");
        loginButton.setOnAction(e -> {
            String user = username.getText().trim();
            String pass = password.getText().trim();
            String role = loginRoleComboBox.getValue();

            // Debugging outputs
            System.out.println("Debug: Username: " + user);
            System.out.println("Debug: Password: " + pass);
            System.out.println("Debug: Role: " + role);

            if (user.isEmpty() || pass.isEmpty() || role == null) {
                showAlert(Alert.AlertType.ERROR, "Login Error", "All fields must be filled out.");
            } else {
                try {
                    boolean success = User.logIn(role, user, pass);
                    if (success) {
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Welcome, " + user + "!");
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Login Error", "Incorrect username or password.");
                    }
                } catch (IOException ex) {
                    showAlert(Alert.AlertType.ERROR, "File Error", "Error accessing the user data.");
                }
            }
        });

        Hyperlink forgotPassword = new Hyperlink("Forgot Password?");
        forgotPassword.setTextFill(Color.WHITE);
        forgotPassword.setOnAction(e -> switchToForgotPassword());

        form.getChildren().addAll(title, username, password, loginRoleComboBox, loginButton, forgotPassword);
        return form;
    }

    private VBox createSignupForm() {
        VBox form = new VBox(10);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(30));
        form.setStyle("-fx-background-color: #8B0000;");

        Label title = new Label("Sign Up");
        title.setFont(new Font(24));
        title.setTextFill(Color.WHITE);

        TextField username = new TextField();
        username.setPromptText("Username/Email");

        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        signupRoleComboBox = new ComboBox<>();
        signupRoleComboBox.getItems().addAll("Admin", "Receptionist", "Guest");
        signupRoleComboBox.setPromptText("Select Role");

        Button signupButton = new Button("Sign Up");
        signupButton.setStyle("-fx-background-color: #FF4B2B; -fx-text-fill: white;");
        signupButton.setOnAction(e -> {
            String role = signupRoleComboBox.getValue();
            String user = username.getText().trim();
            String pass = password.getText().trim();

            if (user.isEmpty() || pass.isEmpty() || role == null) {
                showAlert(Alert.AlertType.ERROR, "Signup Error", "All fields must be filled out.");
            } else {
                User newUser = new User(user, pass);
                newUser.setUserType(role);
                newUser.createUserID();
                User.users.add(newUser);

                try {
                    User.WriteUser(role);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Account created successfully!");
                    switchToLogin();
                } catch (IOException ex) {
                    showAlert(Alert.AlertType.ERROR, "File Error", "Error saving the user data.");
                }
            }
        });

        form.getChildren().addAll(title, username, password, signupRoleComboBox, signupButton);
        return form;
    }



    private VBox createForgotPasswordForm() {
        VBox form = new VBox(10);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(30));
        form.setStyle("-fx-background-color: #2F4F4F;");

        Label title = new Label("Forgot Password");
        title.setFont(new Font(24));
        title.setTextFill(Color.WHITE);

        TextField username = new TextField();
        username.setPromptText("Username");

        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Admin", "Receptionist", "Guest");
        roleComboBox.setPromptText("Select Role");

        Button resetButton = new Button("Reset Password");
        resetButton.setStyle("-fx-background-color: #FF4B2B; -fx-text-fill: white;");
        resetButton.setOnAction(e -> {
            String user = username.getText().trim();
            String role = roleComboBox.getValue();

            if (user.isEmpty() || role == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled out.");
                System.out.println("Debug: Username or role is empty");
            } else {
                File file = new File(role + ".txt");
                if (file.exists()) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;
                        boolean userFound = false;
                        while ((line = reader.readLine()) != null) {
                            if (line.split(",")[0].equals(user)) {
                                userFound = true;
                                break;
                            }
                        }
                        if (userFound) {
                            TextInputDialog newPasswordDialog = new TextInputDialog();
                            newPasswordDialog.setHeaderText("Enter New Password");
                            newPasswordDialog.setContentText("New Password:");
                            String newPassword = newPasswordDialog.showAndWait().orElse("");

                            TextInputDialog confirmPasswordDialog = new TextInputDialog();
                            confirmPasswordDialog.setHeaderText("Confirm New Password");
                            confirmPasswordDialog.setContentText("Confirm Password:");
                            String confirmPassword = confirmPasswordDialog.showAndWait().orElse("");

                            if (!newPassword.isEmpty() && newPassword.equals(confirmPassword)) {
                                updatePassword(file, user, newPassword);
                                showAlert(Alert.AlertType.INFORMATION, "Success", "Password reset successfully!");
                                switchToLogin();
                            } else {
                                showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match.");
                            }
                        } else {
                            showAlert(Alert.AlertType.ERROR, "Error", "Username not found.");
                            System.out.println("Debug: Username not found in file");
                        }
                    } catch (IOException ex) {
                        showAlert(Alert.AlertType.ERROR, "File Error", "Error reading the user data.");
                        System.out.println("Debug: IOException occurred");
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Role file does not exist.");
                    System.out.println("Debug: Role file does not exist");
                }
            }
        });

        form.getChildren().addAll(title, username, roleComboBox, resetButton);
        return form;
    }



    private void updatePassword(File file, String username, String newPassword) {
        File tempFile = new File(file.getAbsolutePath() + ".tmp");
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    writer.write(username + "," + newPassword);
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }
        } catch (IOException ex) {
            showAlert(Alert.AlertType.ERROR, "File Error", "Error updating the user data.");
            System.out.println("Debug: IOException during password update");
        }

        if (!file.delete() || !tempFile.renameTo(file)) {
            showAlert(Alert.AlertType.ERROR, "File Error", "Error finalizing the password update.");
            System.out.println("Debug: Error renaming temporary file");
        }
    }

    private void switchToSignup() {
        formsContainer.getChildren().clear();
        formsContainer.getChildren().add(signupForm);
    }

    private void switchToLogin() {
        formsContainer.getChildren().clear();
        formsContainer.getChildren().add(loginForm);
    }

    private void switchToForgotPassword() {
        TranslateTransition transition = new TranslateTransition(Duration.millis(300), formsContainer);
        transition.setByX(-800);
        transition.setOnFinished(e -> {
            formsContainer.getChildren().clear();
            formsContainer.getChildren().add(forgotPasswordForm);
            formsContainer.setTranslateX(800);
            TranslateTransition resetTransition = new TranslateTransition(Duration.millis(300), formsContainer);
            resetTransition.setByX(-800);
            resetTransition.play();
        });
        transition.play();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
