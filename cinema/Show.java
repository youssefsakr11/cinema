package miucinema;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;

import java.io.*;

public class Show {
    public static ArrayList<Show> shows= new ArrayList<>();
    private int showid;
    private String showname;
    private Movie movie;
    private LocalTime startTime;
    private LocalDate day;
    private String hall;
    private int totalseats;
    private int availableseats;
    private float ticketprice;
    private double revenue=0;
    private int numofbooking; //num of booking per receptionist?
//    private String showcategory;
//    private List<Booking> bookings; 
//LIST FROM THE CLASS Booking AND ITS NAME IS bookings as the relationship of the show with Booking is aggregtion 
    public Show(LocalTime startTime, LocalDate day , String hall, float ticketprice , int totalseats){ //availableseats & totalseats is updated at Farida's
    //this.movie=movie;
    this.startTime=startTime;
    this.day = day;
    this.hall=hall;
    this.ticketprice=ticketprice;
    this.totalseats =totalseats;

    }
    //GETTERS
     public int getShowID(){return showid;}
     public double getRevenue(){return this.revenue;}
     public int getNumOfBooking(){return this.numofbooking;}
     public LocalTime getShowTime(){return startTime;}
     public String getSall(){return hall;}
     public int getTotalSeats(){return totalseats;}
     public int getAvailableSeats(){return availableseats;}
     public float getTicketPrice(){return ticketprice;}
     public String getShowName(){return showname;}
     public LocalDate getDay(){return day;}
     public Movie getMovie(){return movie;}
     public String getHall(){return hall;}
     
     //SETTERS
     public void setShowID(int showid){this.showid=showid;}
     public void setShowTime(LocalTime startTime){ this.startTime=startTime;}
     public void setRevenue(double rev){this.revenue=rev;}
     public void setNumOfBooking(int num){this.numofbooking=num;}
     public void setTotalSeats(int totalseats){ this.totalseats =totalseats;}
     public void setTicketPrice(float ticketprice){ this.ticketprice=ticketprice;}
     public void setShowName(String showname){ this.showname=showname;}
     public void setStartTime(LocalTime startTime){this.startTime = startTime;}
     public void setDay(LocalDate day){this.day = day;}
     public void setHall(String hall){this.hall = hall;}

     
public void showdetails(){
     System.out.println("show: "+getShowName());
     System.out.println("Start time: "+ startTime);
     System.out.println("Day: " + day);
     System.out.println("hall: "+hall);
     System.out.println("totalseats: "+totalseats);
     System.out.println("available seats: "+availableseats);
     System.out.println("ticket price is: "+ticketprice);
    }


public static void saveshow(){ //This puts the entire Movie object attributes in the file
     File file = new File("src/Files/Shows.txt");
      try(ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(file))){  
       write.writeObject(shows);
}catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
}

public static void readshow() throws FileNotFoundException{
    File file = new File("src/Files/Shows.txt");
    if (!file.exists()) {
        System.out.println("Shows file does not exist.");
        return; 
    }
     try (ObjectInputStream read= new ObjectInputStream(new FileInputStream(file))) {
 shows = (ArrayList<Show>) read.readObject();
} catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading file: " + e.getMessage()); 
}

}

}