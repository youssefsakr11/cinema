package miucinema;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.util.Random;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User implements Serializable{
    public static ArrayList<User> users = new ArrayList<>();
     private String username;
     private String password ;
     private int userID;
     private String userType;
     
     public User(){
     username = null;
     password = null;
     userID = 0;
     userType = null;
     }

     public User(String username ,String password){
     this.username = username;
     this.password=password;
     
 }
     
     //SETTERS
    public void setID(int ID){
this.userID=ID;
}
    public void setUserName(String username){
    this.username = username;
    }
   public void setPassword(String password){
   this.password = password;
   }
   public void setUserType(String userType){
   this.userType = userType;
   }
    //GETTERS
public String getUserName(){
return username;
}
public String getPassword(){
return password;
}
public int getUserID(){
return userID;
}
public String getUserType(){
return userType;
}

@Override
public String toString(){
return userID + "\n" + username + "\n" + password;
}

public void createUserID(){
Random random = new Random();
userID = random.nextInt(999);
}
//either clear arraylist after validating or at start of Admin
public static void WriteUser(String userType) throws IOException{
       File file;
        switch (userType) {
            case "Admin":
                file = new File("src/Files/Admins.txt");
                break;
            case "Receptionist":
                file = new File("src/Files/Receptionists.txt");
                break;
            default:
                file = new File("src/Files/Guests.txt");
                break;
        }
        try(ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(file))){ //Overwrites + ensures output is closed after try-catch statement 
       write.writeObject(users);
}catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
}


public static void ReadUser(String userType , Scanner input) throws IOException{
  File file;
        switch (userType) {
            case "Admin":
                file = new File("src/Files/Admins.txt");
                break;
            case "Receptionist":
                file = new File("src/Files/Receptionists.txt");
                break;
            default:
                file = new File("src/Files/Guests.txt");
                break;
        }
        if (!file.exists()) {
        System.out.println(userType + " file does not exist.");
        return; 
    }
    try (ObjectInputStream read= new ObjectInputStream(new FileInputStream(file))) {
 users = (ArrayList<User>) read.readObject(); //static cast to turn binary into type object of class movies
} catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading file: " + e.getMessage()); 
}
}


     public boolean logIn() throws IOException {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Log In");
    System.out.println("--------");
    while (true) {
        System.out.println("Account Type:\n1. Admin\n2. Receptionist\n3. Guest");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        switch (choice) {
            case 1:
                userType = "Admin";
                break;
            case 2:
                userType = "Receptionist";
                break;
            case 3:
                userType = "Guest";
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                continue; // Restart the loop for a valid choice
        }

        // Load user data from the file
        ReadUser(userType, scanner);

        // Log the userType and users list
        System.out.println("Loaded userType: " + userType);
        System.out.println("Users loaded: " + users.size() + " users.");

        System.out.print("User ID: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid ID format. Please try again.");
            scanner.nextLine(); // Clear invalid input
            continue;
        }
        userID = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        System.out.print("Password: ");
        password = scanner.nextLine();
        if (password.isEmpty()) {
            System.out.println("Password cannot be empty. Please try again.");
            continue;
        }

        // Validate credentials
       boolean credentialsValid = false;
        for (User user : User.users) { 
            // DEBUG LINE
            System.out.println("Checking user: " + user.getUserName() + ", UserID: " + user.getUserID() + ", UserType: " + user.getUserType());

            // Check if the userID matches and then validate the password
            if (user.getUserID() == userID && password.equals(user.getPassword())) {
                System.out.println("Welcome, " + user.getUserName() + "!");
                credentialsValid = true;
                break;
            }
        }

        if (credentialsValid) {
            return true; // Successfully logged in
        } else {
            System.out.println("Error: Invalid credentials. Please try again.");
        }
    // If we exit the loop without a valid login
    System.out.println("Account not found. Returning to the main menu.");
    return false;
     }
 }
public static boolean logIn(String role, String username, String password) throws IOException {
    // Load users from the file associated with the selected role
    ReadUser(role, new Scanner(System.in));

    // Check if the file is empty or if no users are loaded
    if (users.isEmpty()) {
        System.out.println("No users found in the " + role + " file.");
        return false;
    }

    // Validate the credentials
    for (User user : users) {
        if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
            System.out.println("Welcome, " + username + " as " + role + "!");
            return true;
        }
    }

    // If no match is found, return false
    System.out.println("Error: Incorrect username or password for the selected role.");
    return false;
}


}

//STEPS OF SIGN UP
//Input credentials (UserID & UserType & Password & username)
//Save Credentials in an object of User -> utilizing constructor & setters
//add that object to the user arraylist
//call writeUser function
