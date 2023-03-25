package Interfacee;

import java.rmi.Remote;

public interface Client_Interface extends Remote {
  public String  bookMovieTickets (String customerID,String movieID,String movieName,int  numberOfTickets) throws Exception;
  public String  getBookingSchedule (String customerID) throws Exception ;
 public String  cancelMovieTickets (String customerID, String movieID,String  movieName, int numberOfTickets) throws Exception;
 public String exchangeTickets (String customerID, String old_movieName,String movieID, String new_movieID,String  new_movieName, int numberOfTickets) throws Exception;



}
