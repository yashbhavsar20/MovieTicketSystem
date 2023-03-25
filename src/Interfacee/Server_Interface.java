package Interfacee;

import java.rmi.*;
public interface Server_Interface extends Remote {
    public String addMovieSlots (String movieID, String movieName, int bookingCapacity) throws Exception;
    public String removeMovieSlots(String movieID,String movieName) throws Exception;
    public String listMovieShowAvailability(String movieName) throws Exception;
    public String  adminBookMovieTickets (String customerID,String movieID,String movieName,int  numberOfTickets) throws Exception;
    public String   adminGetBookingSchedule (String customerID) throws Exception ;
    public String  adminCancelMovieTickets (String customerID, String movieID,String  movieName, int numberOfTickets) throws Exception;
   // public String ServerExchangeTickets (String customerID, String movieID, String new_movieID,String  new_movieName, int numberOfTickets);
}
