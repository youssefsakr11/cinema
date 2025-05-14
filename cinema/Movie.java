package miucinema;

 //everytime a movie is booked, update movieRevenue & movieBooking IN FILE
//throws means if that error occurs neglegt it
//Scanner parameter is recieve input stream (can be website, file, terminal input, etc)
//non static method can be read from static 
//static can only be called in static things

import java.util.ArrayList;
import java.util.Random;
import java.io.IOException;
import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Duration;


//remove movieID -> unecessary
//remove rating?

public class Movie implements Serializable{
    private static final long serialVersionUID = -5152118359789506630L;  // Make sure it matches the serialized file
    private String movieName , movieGenre, movieDuration , movieCover; 
    private int movieID , movieBooking ; //num of bookings per movie
    private double movieRating , movieRevenue;
    public static ArrayList<Movie> movies = new ArrayList();
    
    // Default constructor
   public Movie() {
        movieName = null;
        movieGenre = null;
        movieDuration = null;
        movieID = 0;
        movieRating = 0.0;
        movieRevenue = 0.0;
        movieBooking = 0;
        movieID = 0;
    }  
  
    public Movie(String movieName , String movieGenre , String movieDuration , String movieCover){
        this.movieName = movieName;
        this.movieGenre = movieGenre;
        this.movieDuration = movieDuration;
        this.movieCover = movieCover;
      //  this.movieRating = movieRating;
    }
    
     //Getters
     public String getMovieName(){
    return movieName;
    }
    public int getMovieID(){
    return movieID;
    }
    public int getMovieBooking(){
    return movieBooking;
    }
    public double getMovieRevenue(){
    return movieRevenue;
    }
    public String getMovieGenre(){
    return movieGenre;
    }
    public String getMovieDuration(){
    return movieDuration; 
    }
    public double getMovieRating(){
    return movieRating;
    }
    public String getMovieCover(){
    return movieCover;
    }
    public void createMovieID(){
    Random random = new Random();
    movieID = random.nextInt(999);
    }
 
//Setters
    public void setMovieName(String movieName){
    this.movieName = movieName;
    }
    public void setMovieGenre(String movieGenre){
    this.movieGenre = movieGenre;
    }
    public void setMovieDuration(String movieDuration){
   this.movieDuration = movieDuration;
    }
    public void setMovieRating(double movieRating){
    this.movieRating = movieRating;
    }
    public void setMovieBooking(int movieBooking){
    this.movieBooking = movieBooking; //updated with every new booking
    }
    public void setMovieRevenue(double movieRevenue){
    this.movieRevenue = movieRevenue; //updated with every new booking
    }
    public void setMovieID(int movieID){
        this.movieID = movieID;
    }
    public void setMovieCover(String movieCover){
    this.movieCover = movieCover;
    }
    //if you want to remove movie, remove it's object from Arraylist and when you write it file you OVERWRITE
  
  
    @Override
  public String toString() { //toString must be public to avoid compiler error
            return  movieID + "\n" + movieName + "\n" + movieGenre + "\n" + movieDuration + "\n"+ movieRating + "\n" + movieBooking + "\n" + movieRevenue; //so that when you write to file, booking & revenue isn't altered    
  }

 
public static void readMovie() { 
    File file = new File("src/Files/MovieList.txt");
    if (!file.exists()) {
        System.out.println("Movie file does not exist.");
        return; 
    }
     try (ObjectInputStream read= new ObjectInputStream(new FileInputStream(file))) {
 movies = (ArrayList<Movie>) read.readObject(); //static cast to turn binary into type object of class movies
} catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading file: " + e.getMessage()); 
}
}


public static void writeMovie(){
      File file = new File("src/Files/MovieList.txt");
      try(ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(file))){ //Overwrites + ensures output is closed after try-catch statement 
       write.writeObject(movies);
}catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
}
}
