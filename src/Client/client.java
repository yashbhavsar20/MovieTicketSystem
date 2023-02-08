
package Client;

import Interfacee.Client_Interface;
import Interfacee.Server_Interface;
import java.rmi.Naming;
import java.util.Scanner;

public class client {
    static Scanner scanner;

    public client() {
    }

    public static void main(String[] args) {
        try {
            System.out.println("Please enter the Id");
            String Id = scanner.nextLine();
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
                            System.out.println("Please enter movieID you want to Add1");
                            clientBookMovieId = scanner.nextLine();
                            System.out.println("Please enter movieName you want to Add1");
                            clientBookMovieName = scanner.nextLine();
                            System.out.println("Please enter booking Capacity for this movie you want to Add1");
                            clientBookNoOfTickets = Integer.parseInt(scanner.nextLine());
                            System.out.println( adminServerInterface.addMovieSlots(clientBookMovieId, clientBookMovieName, clientBookNoOfTickets));
                            break;
                        case 2:
                            System.out.println("Please enter movieID you want to Remove");
                            clientCancelMovieId = scanner.nextLine();
                            System.out.println("Please enter movieName you want to Remove");
                            clientCancelMovieName = scanner.nextLine();
                            adminServerInterface.removeMovieSlots(clientCancelMovieId, clientCancelMovieName);
                            break;
                        case 3:
                            System.out.println("Please enter movieName For Displaying Movie Show Availibility");
                            String listMovieShow = scanner.nextLine();
                            System.out.println(adminServerInterface.listMovieShowAvailability(listMovieShow));
                            break;
                        case 4:
                            System.out.println("Please Enter customer Id you want to Book for");
                            String bookCustomerID = scanner.nextLine();
                            System.out.println("Please enter movieId you want to Book");
                            String bookMovieId = scanner.nextLine();
                            System.out.println("Please enter Movie name for Booking");
                            String bookMovieName = scanner.nextLine();
                            System.out.println("Please Enter Number of tickets you want to book");
                            int bookNoOfTickets = Integer.parseInt(scanner.nextLine());
                            System.out.println(adminServerInterface.adminBookMovieTickets(bookCustomerID, bookMovieId, bookMovieName, bookNoOfTickets));
                            break;
                        case 5:
                            System.out.println("Please Enter customer Id for displaying Movie Schedule");
                            String getBookingScheduleCustomerID = scanner.nextLine();
                            System.out.println( adminServerInterface.adminGetBookingSchedule(getBookingScheduleCustomerID));
                            break;
                        case 6:
                            System.out.println("Please Enter customer Id you want to Cancel for");
                            String cancelCustomerID = scanner.nextLine();
                            System.out.println("Please enter movieId you want to Cancel");
                            String cancelMovieId = scanner.nextLine();
                            System.out.println("Please enter Movie name for Cancelling Tickets");
                            String cancelMovieName = scanner.nextLine();
                            System.out.println("Please Enter Number of tickets you want to Cancel");
                            int cancelNoOfTickets = Integer.parseInt(scanner.nextLine());
                            adminServerInterface.adminCancelMovieTickets(cancelCustomerID, cancelMovieId, cancelMovieName, cancelNoOfTickets);
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
                            System.out.println("Please enter movieId you want to Book");
                            clientBookMovieId = scanner.nextLine();
                            System.out.println("Please enter Movie name for Booking");
                            clientBookMovieName = scanner.nextLine();
                            System.out.println("Please Enter Number of tickets you want to book");
                            clientBookNoOfTickets = Integer.parseInt(scanner.nextLine());
                            System.out.println(clientServerInterface.bookMovieTickets(Id, clientBookMovieId, clientBookMovieName, clientBookNoOfTickets));
                            break;
                        case 2:
                            System.out.println(clientServerInterface.getBookingSchedule(Id));
                            break;
                        case 3:
                            System.out.println("Please enter movieId you want to Cancel");
                            clientCancelMovieId = scanner.nextLine();
                            System.out.println("Please enter Movie name for Cancelling Tickets");
                            clientCancelMovieName = scanner.nextLine();
                            System.out.println("Please Enter Number of tickets you want to Cancel");
                            int clientCancelNoOfTickets = Integer.parseInt(scanner.nextLine());
                            System.out.println(clientServerInterface.cancelMovieTickets(Id, clientCancelMovieId, clientCancelMovieName, clientCancelNoOfTickets));
                            break;
                        case 4:
                            redFlag = true;
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
        System.out.println("1.Book Movie Tickets\n2.Get Booking Schedule\n3.Cancel Movie Tickets\n4.Exit");
    }

    static {
        scanner = new Scanner(System.in);
    }
}
