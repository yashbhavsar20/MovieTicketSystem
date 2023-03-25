
package Client;

import Interfacee.Client_Interface;
import Interfacee.Server_Interface;

import java.io.FileWriter;
import java.io.IOException;
import java.rmi.Naming;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class client {
    static Scanner scanner;
    static String Id1;

    public client() {
    }

    public static void main(String[] args) {
        try {
            String Status="";
            String log="";
            System.out.println("Please enter the Id");
            String Id = scanner.nextLine();
            Id1=Id;
            boolean IsAdmin = checkAdmin(Id);
            boolean IsCustomer = checkCustomer(Id);
            String port;
            boolean redFlag;
            int select;
            String clientBookMovieId;
            String clientBookMovieName;
            int clientBookNoOfTickets;
            String clientCancelMovieId;
            String clientCancelMovieName;
            if (IsAdmin) {
                port = getPort(Id);
                Server_Interface adminServerInterface = (Server_Interface)Naming.lookup(port);
                redFlag = false;

                do {
                    displayAdminMenu();
                    select = Integer.parseInt(scanner.nextLine());
                    switch (select) {
                        case 1:
                            Status="Transfer";
                            log="admin sent request for adding slot";
                            System.out.println("Please enter movieID you want to Add1");
                            clientBookMovieId = scanner.nextLine();
                            System.out.println("Please enter movieName you want to Add1");
                            clientBookMovieName = scanner.nextLine();
                            System.out.println("Please enter booking Capacity for this movie you want to Add1");
                            clientBookNoOfTickets = Integer.parseInt(scanner.nextLine());
                            System.out.println( adminServerInterface.addMovieSlots(clientBookMovieId, clientBookMovieName, clientBookNoOfTickets));
                            writeToLog("ADD MOVIE SLOTS",clientBookMovieId+""+clientBookMovieName+" "+clientBookNoOfTickets,Status,log);

                            break;
                        case 2:
                            Status="Transfer";
                            log="admin sent request for removing slot";

                            System.out.println("Please enter movieID you want to Remove");
                            clientCancelMovieId = scanner.nextLine();
                            System.out.println("Please enter movieName you want to Remove");
                            clientCancelMovieName = scanner.nextLine();
                            adminServerInterface.removeMovieSlots(clientCancelMovieId, clientCancelMovieName);
                            writeToLog("REMOVE MOVIE SLOTS",clientCancelMovieId+""+clientCancelMovieName,Status,log);
                            break;
                        case 3:
                            Status="Transfer";
                            log="admin sent request for displaying show availability";

                            System.out.println("Please enter movieName For Displaying Movie Show Availibility");
                            String listMovieShow = scanner.nextLine();
                            System.out.println(adminServerInterface.listMovieShowAvailability(listMovieShow));
                            writeToLog("LIST MOVIE AVAILABILITY",listMovieShow,Status,log);
                            break;
                        case 4:
                            Status="Transfer";
                            log="admin sent request for booking movie show for client";

                            System.out.println("Please Enter customer Id you want to Book for");
                            String bookCustomerID = scanner.nextLine();
                            System.out.println("Please enter movieId you want to Book");
                            String bookMovieId = scanner.nextLine();
                            System.out.println("Please enter Movie name for Booking");
                            String bookMovieName = scanner.nextLine();
                            System.out.println("Please Enter Number of tickets you want to book");
                            int bookNoOfTickets = Integer.parseInt(scanner.nextLine());
                            System.out.println(adminServerInterface.adminBookMovieTickets(bookCustomerID, bookMovieId, bookMovieName, bookNoOfTickets));
                            writeToLog("ADMIN BOOK MOVIE TICKETS",bookCustomerID+" "+bookMovieId+" "+bookMovieName+" "+bookNoOfTickets,Status,log);
                            break;
                        case 5:
                            Status="Transfer";
                            log="admin sent request for getting movie schedule";

                            System.out.println("Please Enter customer Id for displaying Movie Schedule");
                            String getBookingScheduleCustomerID = scanner.nextLine();
                            System.out.println( adminServerInterface.adminGetBookingSchedule(getBookingScheduleCustomerID));
                            writeToLog("ADMIN GET MOVIE SCHEDULE",getBookingScheduleCustomerID,Status,log);
                            break;
                        case 6:
                            Status="Transfer";
                            log="admin sent request for cancelling movie tickets";

                            System.out.println("Please Enter customer Id you want to Cancel for");
                            String cancelCustomerID = scanner.nextLine();
                            System.out.println("Please enter movieId you want to Cancel");
                            String cancelMovieId = scanner.nextLine();
                            System.out.println("Please enter Movie name for Cancelling Tickets");
                            String cancelMovieName = scanner.nextLine();
                            System.out.println("Please Enter Number of tickets you want to Cancel");
                            int cancelNoOfTickets = Integer.parseInt(scanner.nextLine());
                            System.out.println(adminServerInterface.adminCancelMovieTickets(cancelCustomerID, cancelMovieId, cancelMovieName, cancelNoOfTickets));
                            writeToLog("ADMIN GET MOVIE SCHEDULE",cancelCustomerID+" "+cancelMovieId+" "+cancelMovieName+" "+cancelNoOfTickets,Status,log);
                            break;
                        case 7:
                            redFlag = true;
                    }
                } while(!redFlag);
            }

            if (IsCustomer) {
                port = getPort(Id);
                System.out.println(port);
                Client_Interface clientServerInterface = (Client_Interface)Naming.lookup(port);
                redFlag = false;

                do {
                    displayClientMenu();
                    select = Integer.parseInt(scanner.nextLine());
                    switch (select) {
                        case 1:
                            Status="Transfer";
                            log="request sent for booking movie tickets";
                            System.out.println("Please enter movieId you want to Book");
                            clientBookMovieId = scanner.nextLine();
                            System.out.println("Please enter Movie name for Booking");
                            clientBookMovieName = scanner.nextLine();
                            System.out.println("Please Enter Number of tickets you want to book");
                            clientBookNoOfTickets = Integer.parseInt(scanner.nextLine());
                            System.out.println(clientServerInterface.bookMovieTickets(Id, clientBookMovieId, clientBookMovieName, clientBookNoOfTickets));
                            writeToLog("Book movie tickets",Id+" "+clientBookMovieId+" " +clientBookMovieName+" "+ clientBookNoOfTickets,Status,log);
                            break;
                        case 2:
                            Status="Transfer";
                            log="request sent for getting movie Schedule";

                            System.out.println(clientServerInterface.getBookingSchedule(Id));
                            writeToLog("get movie schedule",Id,Status,log);
                            break;
                        case 3:
                            Status="Transfer";
                            log="request sent to cancel movie tickets";

                            System.out.println("Please enter movieId you want to Cancel");
                            clientCancelMovieId = scanner.nextLine();
                            System.out.println("Please enter Movie name for Cancelling Tickets");
                            clientCancelMovieName = scanner.nextLine();
                            System.out.println("Please Enter Number of tickets you want to Cancel");
                            int clientCancelNoOfTickets = Integer.parseInt(scanner.nextLine());
                            System.out.println(clientServerInterface.cancelMovieTickets(Id, clientCancelMovieId, clientCancelMovieName, clientCancelNoOfTickets));
                            writeToLog("Cancel movie tickets",Id+" "+clientCancelMovieId+" " +clientCancelMovieName+" "+ clientCancelNoOfTickets,Status,log);
                            break;
                        case 4:
                            Status="Transfer";
                            log="request sent to cancel movie tickets";

                            System.out.println("Please enter old movieId you want to Cancel");
                           String clientoldMovieId = scanner.nextLine();
                            System.out.println("Please enter old Movie name for Cancelling Tickets");
                           String clientoldMovieName = scanner.nextLine();
                            System.out.println("Please enter new movieId you want to Cancel");
                           String clientnewMovieId = scanner.nextLine();
                            System.out.println("Please enter new Movie name for Cancelling Tickets");
                           String clientnewMovieName = scanner.nextLine();
                            System.out.println("Please Enter Number of tickets you want to Cancel");
                            int clientNoOfTickets = Integer.parseInt(scanner.nextLine());
                            System.out.println(clientServerInterface.exchangeTickets(Id, clientoldMovieName, clientoldMovieId, clientnewMovieId,clientnewMovieName,clientNoOfTickets));
                           // writeToLog("Cancel movie tickets",Id+" "+clientCancelMovieId+" " +clientCancelMovieName+" "+ clientCancelNoOfTickets,Status,log);
                            break;

                    }
                } while(!redFlag);
            }
        } catch (Exception var23) {
            System.out.println(var23);
        }


    }

    public static Boolean checkAdmin(String Id) {
        return Id.charAt(3) == 'A' ? true : false;
    }

    public static Boolean checkCustomer(String Id) {
        return Id.charAt(3) == 'C' ? true : false;
    }

    public static String getPort(String Id) {
        if (Id.substring(0, 3).equals("ATW")) {
            return "ATWServer";
        } else {
            return Id.substring(0, 3).equals("VER") ? "VERServer" : "OUTServer";
        }
    }

    public static void displayAdminMenu() {
        System.out.println("Please select one of the following");
        System.out.println("1.Add Movie Slots\n2.Remove Movie Slots\n3.List Movie Shows Availability\n4.Book Movie Tickets\n5.Get Booking Schedule\n6.Cancel Movie Tickets\n7.Exit");
    }

    public static void displayClientMenu() {
        System.out.println("Please select one of the following");
        System.out.println("1.Book Movie Tickets\n2.Get Booking Schedule\n3.Cancel Movie Tickets\n4.exchange tickets\n5.Exit");
    }
    public static void writeToLog(String operation, String params, String status, String responceDetails) {
        try {
            FileWriter myWriter = new FileWriter("C:\\\\Users\\\\yashb\\\\IdeaProjects\\\\DSD_demo\\\\src\\\\Logs\\\\"+Id1,true);
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String log = dateFormat.format(LocalDateTime.now()) + " : " + operation + " : " + params + " : " + status
                    + " : " + responceDetails + "\n";
            myWriter.write(log);
            myWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    static {
        scanner = new Scanner(System.in);
    }
}
