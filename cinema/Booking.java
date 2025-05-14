package miucinema;
import java.io.File;
import java.util.ArrayList;
import java.io.*;
import java.util.*;

public class Booking implements Serializable{
    File file = new File("src/Files/Bookings.txt"); 
public static ArrayList<Booking> bookings=new ArrayList<>(); 

  //attributes
private int bookingID; //Ticket ID
private int numofseats; 
private float rating; 
private Guest guest;
private transient Movie movie; // Make movie transient to prevent serialization
private String movieName; // Store movieName explicitly
private transient Show show; 
private float payment;
//Constructor
public Booking(Movie movie , Show show ,Guest guest,int numofseats,float payment){
    this.movie = movie;
    this.movieName = movie.getMovieName(); //at this point, eh lazmt el movie object? can just fetch it directly mel arraylist?
    this.show = show;
    this.guest=guest;
    //this.hall = show.getHall();
    this.numofseats= numofseats;
    createBookingID();
    this.payment=payment;
}
public int getBookingID(){
return bookingID;
}
public float getRating(){
return rating;
}
public void setRating(float rating){
    this.rating=rating;
}
public int getSeatsBooked(){
return numofseats;
}
public String getMovieName(){
return movie.getMovieName();
}
public Movie getMovie(){
    return this.movie;
}

public void createBookingID(){
    Random random = new Random();
    this.bookingID = random.nextInt(999);
}

public static void writeBooking() throws FileNotFoundException{
      File file = new File("src/Files/srcBooking.txt");
      try(ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(file))){  
       write.writeObject(bookings);
}catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
}
     
public static void readBooking() throws FileNotFoundException{
     File file = new File("src/Files/srcBooking.txt");
    if (!file.exists()) {
        System.out.println("Booking file does not exist.");
        return; 
    }
     try (ObjectInputStream read= new ObjectInputStream(new FileInputStream(file))) {
     //movie.setMovieName((Movie)read.readObject());
 bookings = (ArrayList<Booking>) read.readObject(); //static cast to turn binary into type object of class movies
} catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading file: " + e.getMessage()); 
}
}
     
//da 3shan el guest y view el details bt3to
//    //overload
//public String toString(int index){
//    return bookingID + "\n" +  Movie.movies.get(index).getMovieName() + "\n" + Show.shows.get(index).getHall()+ "\n" + getSeatsBooked() + "\n" + rating;
//}

/*
public void bookSeats(){
for(int i = 0; )
}
*/

}