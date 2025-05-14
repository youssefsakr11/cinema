package miucinema;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class Guest extends User{    
    public ArrayList<Booking> historyOfbookings; 
    public static ArrayList<Guest> guests=new ArrayList<>();


  public Guest (String username,String password){
super(username,password);
    }
   
    public void viewBookingDetails(){
        System.out.println("Your booking history");
        
        for(Booking history:historyOfbookings){ 
            System.out.println(history);
        }
    
    }
  public void ratebooking(){ 
  System.out.println("enter booking id: ");
  Scanner input=new Scanner(System.in);
  int id=input.nextInt();
   System.out.println("please rate your experience: ");
    float rating=input.nextFloat();
    for(Booking h:historyOfbookings){
      if(h.getBookingID()==id){
          h.setRating(rating);
      }
    }
   }
  
public void rateMovie(){  
Scanner input=new Scanner(System.in);
for(Booking h:historyOfbookings){
     System.out.println("please rate " + h.getMovieName()+": ");
     float rating=input.nextFloat();
     h.getMovie().setMovieRating(rating);
}
}
  
public static void writeGuest(){
      File file = new File("src/Files/Guests.txt");
      try(ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(file))){  
       write.writeObject(guests);
}catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
}
public static void readGuest() { 
    File file = new File("src/Files/Guests.txt");
    if (!file.exists()) {
        System.out.println("Guests file does not exist.");
        return; 
    }
     try (ObjectInputStream read= new ObjectInputStream(new FileInputStream(file))) {
 guests = (ArrayList<Guest>) read.readObject();
} catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading file: " + e.getMessage()); 
}
}

}
