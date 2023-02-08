package Implementation;

//import Interfacee.Client_Interface;
//import Interfacee.Interface;
import Interfacee.Client_Interface;
import Interfacee.Interface;
import Interfacee.Server_Interface;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.rmi.*;
import java.rmi.server.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class implementation extends UnicastRemoteObject implements  Client_Interface ,Server_Interface {
    public static HashMap<String, Integer> port = new HashMap<>();

    static {
        port.put("ATW", 6890);
        port.put("VER", 6887);
        port.put("OUT", 6889);
    }

    String acronym;
    TimeUnit time = TimeUnit.SECONDS;
    String temp1;
    String temp2;
    String servername;

    private String message;
    public HashMap<String, HashMap<String, Integer>> hashMap = new HashMap<>();
    public HashMap<String, HashMap<String, HashMap<String, Integer>>> hashMapCustomer = new HashMap<>();


    public implementation() throws RemoteException {
        super();
    }
    public void setName(String serverName)
    {
        this.servername=serverName;
    }

    public String bookMovieTickets(String customerID, String movieID, String movieName, int numberOfTickets) throws Exception {
        String ret = "";
        if (customerID.substring(0, 3).equals(movieID.substring(0, 3))) {
            if (hashMap == null) {
                return "No Data Found";
            }
            System.out.println("heyaaaa");

            boolean check1 = hashMap.containsKey(movieName);

            if (hashMap.containsKey(movieName)) {
                if (hashMap.get(movieName).containsKey(movieID)) {
                    int capacity = hashMap.get(movieName).get(movieID);
                    if (capacity >= numberOfTickets) {
                        hashMap.get(movieName).put(movieID, capacity - numberOfTickets);
                        if (!hashMapCustomer.containsKey(customerID)) {
                            HashMap<String, HashMap<String, Integer>> addressMap = new HashMap<>();
                            HashMap<String, Integer> addressMap1 = new HashMap<>();
                            addressMap1.put(movieID, numberOfTickets);
                            addressMap.put(movieName, addressMap1);
                            hashMapCustomer.put(customerID, addressMap);
                            ret="DONE SUCSSESFULLY";
                        } else {
                            HashMap<String, HashMap<String, Integer>> addressMap = hashMapCustomer.get(customerID);
                            if (!addressMap.containsKey(movieName)) {
                                HashMap<String, Integer> addressMap1 = new HashMap<>();
                                addressMap1.put(movieID, numberOfTickets);
                                addressMap.put(movieName, addressMap1);
                                hashMapCustomer.put(customerID, addressMap);

                            } else {
                                HashMap<String, Integer> addmap = addressMap.get(movieName);
                                addmap.put(movieID, numberOfTickets);
                                addressMap.put(movieName, addmap);
                                hashMapCustomer.put(customerID, addressMap);

                            }
                            ret="DONE SUCSSESFULLY";


                        }


                    } else {
                        ret = "Only" + capacity + "no of tickets available";
                    }

                } else {
                    ret = "No Shows Available for this MovieId";
                }


            } else {
                ret = "No Bookings for this MovieName Available";
            }
            return ret;

        } else {
            new Thread() {
                public void run() {
                    temp1 = udpThread("bookMovieTickets:" + customerID + " " + movieName + " " + movieID + " " + numberOfTickets, port.get(movieID.substring(0, 3)));
                    System.out.println("hello" + temp1);

                }
            }.start();
            try {
                time.sleep(2L);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }
        return temp1;
    }


    public String getBookingSchedule(String customerID) throws Exception {

        String ret = "";
        if (hashMapCustomer.containsKey(customerID)) {
            HashMap<String, HashMap<String, Integer>> addMap = hashMapCustomer.get(customerID);
            for (var letterEntry : addMap.entrySet()) {
                String letter = letterEntry.getKey();
                // ...
                for (var nameEntry : letterEntry.getValue().entrySet()) {
                    ret = ret + nameEntry.getValue() + " " + "Tickets have been Booked for Movie:" + letter + " " + "MovieID:" + nameEntry.getKey() + "\n";
                    // ...
                }
            }
        }
        if(this.servername.equals("ATW")){
            new Thread() {
                public void run() {
                    temp1 = udpThread("getBookingSchedule:" + customerID, port.get(customerID.substring(0,3)));
                    System.out.println("hello" + temp1);
                }
            }.start();

            new Thread() {
                public void run() {
                    temp2 = udpThread("getBookingSchedule:" + customerID, port.get(customerID.substring(0,3)));
                    System.out.println("hello" + temp2);
                }
            }.start();


        }
        else if (this.servername.equals("VER")){
            new Thread() {
                public void run() {
                    temp1 = udpThread("getBookingSchedule:" + customerID, port.get(customerID.substring(0,3)));
                    System.out.println("hello" + temp1);
                }
            }.start();

            new Thread() {
                public void run() {
                    temp2 = udpThread("getBookingSchedule:" + customerID, port.get(customerID.substring(0,3)));
                    System.out.println("hello" + temp2);
                }
            }.start();

        }
        else {
            new Thread() {
                public void run() {
                    temp1 = udpThread("getBookingSchedule:" + customerID, port.get(customerID.substring(0,3)));
                    System.out.println("hello" + temp1);
                }
            }.start();

            new Thread() {
                public void run() {
                    temp2 = udpThread("getBookingSchedule:" + customerID, port.get(customerID.substring(0,3)));
                    System.out.println("hello" + temp2);
                }
            }.start();

        }


        try {
            time.sleep(2L);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return ret + temp1 + temp2;

    }


    public String cancelMovieTickets(String customerID, String movieID, String movieName, int numberOfTickets) throws Exception {
        int tickets;
        String ret=" ";
        if(customerID.substring(0,3).equals(movieID.substring(0,3)))
        {
            if(hashMapCustomer.containsKey(customerID))
            {
                if(hashMapCustomer.get(customerID).containsKey(movieName))
                {
                    if (hashMapCustomer.get(customerID).get(movieName).containsKey(movieID))
                    {
                       tickets= hashMap.get(movieName).get(movieID);
                       hashMap.get(movieName).put(movieID,tickets+numberOfTickets);
                       ret="DONE SUCCESSFULLY";
                    }
                    else {
                        ret="WRONG MOVIE ID";
                    }
                }
                else {
                    ret="NO MOVIE BOOKINGS FOUND";
                }
            }
            else{
                ret="NO BOOKINGS FOUND";

            }
        }
        else {
            new Thread() {
                public void run() {
                    temp1 = udpThread("cancelMovieTickets:" + customerID + " " + movieID + " " + movieName + " " + numberOfTickets, port.get(movieID.substring(0, 3)));
                    System.out.println("hello" + temp1);


                }
            }.start();
            try {
                time.sleep(2L);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }


        }
        return (ret+temp1).trim();



    }




    public String addMovieSlots(String movieID, String movieName, int bookingCapacity) {
        int capacity;
        if (hashMap.containsKey(movieName)) {
            HashMap<String, Integer> addslot = hashMap.get(movieName);
            addslot.put(movieID, bookingCapacity);
            hashMap.put(movieName, addslot);
            System.out.println("done");
        } else {

            HashMap<String, Integer> addslot = new HashMap<>();
            addslot.put(movieID, bookingCapacity);
            hashMap.put(movieName, addslot);
            System.out.println("else done");
        }
        capacity = hashMap.get(movieName).get(movieID);

        return "movie slots added successfully:" + capacity;


    }


    public void removeMovieSlots(String movieID, String movieName) {

    }


    public String listMovieShowAvailability(String movieName) {
        System.out.println("heya");

        String ret = "";
        if (hashMap.containsKey(movieName)) {

            for (var letter : hashMap.get(movieName).entrySet()) {
                ret = ret + " " + letter.getKey() + " " + letter.getValue() + "\n";
                System.out.println(ret);
            }
        }
        else {
            System.out.println(this.servername);
        }

        if(this.servername.equals("ATW"))
        {
            new Thread() {
                public void run() {
                    temp1 = udpThread("listMovieShowAvailability:"+movieName, port.get("VER"));
                    System.out.println("hello" + temp1);


                }
            }.start();
            new Thread() {
                public void run() {
                    temp2 = udpThread("listMovieShowAvailability:" +movieName, port.get("OUT"));
                    System.out.println("hello" + temp1);


                }
            }.start();

        } else if (this.servername.equals("VER")) {
            new Thread() {
                public void run() {
                    temp1 = udpThread("listMovieShowAvailability:" +movieName, port.get("ATW"));
                    System.out.println("hello" + temp1);


                }
            }.start();
            new Thread() {
                public void run() {
                    temp2 = udpThread("listMovieShowAvailability:"+movieName, port.get("OUT"));
                    System.out.println("hello" + temp1);
                }
                }.start();

        }
        else  {
            new Thread() {
                public void run() {
                    temp1 = udpThread("listMovieShowAvailability:" +movieName, port.get("ATW"));
                    System.out.println("hello" + temp1);


                }
            }.start();
            new Thread() {
                public void run() {
                    temp2 = udpThread("listMovieShowAvailability:"+movieName, port.get("VER"));
                    System.out.println("hello" + temp1);
                }
            }.start();

        }


        try {
            time.sleep(2L);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if((ret+temp1+temp2).trim().equals(""))
        {
            return "NO MOVIE SHOWS AVAILABLE";
        }
        else {

            return ret+temp1+temp2;
        }

    }


    public String adminBookMovieTickets(String customerID, String movieID, String movieName, int numberOfTickets) throws Exception {
        String ret = "";
        if (customerID.substring(0, 3).equals(movieID.substring(0, 3))) {
            if (hashMap == null) {
                return "No Data Found";
            }
            System.out.println("heyaaaa");

            boolean check1 = hashMap.containsKey(movieName);

            if (hashMap.containsKey(movieName)) {
                if (hashMap.get(movieName).containsKey(movieID)) {
                    int capacity = hashMap.get(movieName).get(movieID);
                    if (capacity >= numberOfTickets) {
                        hashMap.get(movieName).put(movieID, capacity - numberOfTickets);
                        if (!hashMapCustomer.containsKey(customerID)) {
                            HashMap<String, HashMap<String, Integer>> addressMap = new HashMap<>();
                            HashMap<String, Integer> addressMap1 = new HashMap<>();
                            addressMap1.put(movieID, numberOfTickets);
                            addressMap.put(movieName, addressMap1);
                            hashMapCustomer.put(customerID, addressMap);
                            ret="DONE SUCSSESFULLY";
                        } else {
                            HashMap<String, HashMap<String, Integer>> addressMap = hashMapCustomer.get(customerID);
                            if (!addressMap.containsKey(movieName)) {
                                HashMap<String, Integer> addressMap1 = new HashMap<>();
                                addressMap1.put(movieID, numberOfTickets);
                                addressMap.put(movieName, addressMap1);
                                hashMapCustomer.put(customerID, addressMap);

                            } else {
                                HashMap<String, Integer> addmap = addressMap.get(movieName);
                                addmap.put(movieID, numberOfTickets);
                                addressMap.put(movieName, addmap);
                                hashMapCustomer.put(customerID, addressMap);

                            }
                            ret="DONE SUCSSESFULLY";


                        }


                    } else {
                        ret = "Only" + capacity + "no of tickets available";
                    }

                } else {
                    ret = "No Shows Available for this MovieId";
                }


            } else {
                ret = "No Bookings for this MovieName Available";
            }
            return ret;

        } else {
            new Thread() {
                public void run() {
                    temp1 = udpThread("bookMovieTickets:" + customerID + " " + movieName + " " + movieID + " " + numberOfTickets, port.get(movieID.substring(0, 3)));
                    System.out.println("hello" + temp1);

                }
            }.start();
            try {
                time.sleep(2L);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }
        return temp1;
    }


    public String adminGetBookingSchedule(String customerID) throws Exception {
        String ret = "";
        if (hashMapCustomer.containsKey(customerID)) {
            HashMap<String, HashMap<String, Integer>> addMap = hashMapCustomer.get(customerID);
            for (var letterEntry : addMap.entrySet()) {
                String letter = letterEntry.getKey();
                // ...
                for (var nameEntry : letterEntry.getValue().entrySet()) {
                    ret = ret + nameEntry.getValue() + " " + "Tickets have been Booked for Movie:" + letter + " " + "MovieID:" + nameEntry.getKey() + "\n";
                    // ...
                }
            }
        } else {
            ret = "No Bookings found for this CustomerID";
        }


        return ret;


    }


    public void adminCancelMovieTickets(String customerID, String movieID, String movieName, int numberOfTickets) throws Exception {

    }

    public String udpThread(String data, int port) {
        String result = "";
        try (DatagramSocket aSocket = new DatagramSocket()) {
            System.out.println("jfffhf"+data.getBytes().length+data);
            DatagramPacket request = new DatagramPacket(data.getBytes(), data.getBytes().length,
                    InetAddress.getByName("localhost"), port);
            aSocket.send(request);
            System.out.println("data sent to " + port);

            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(reply);
            aSocket.close();
            result = new String(reply.getData()).trim();
            System.out.println("HIII"+result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getServerBookingSchedule(String customerID) throws Exception {

        String ret = "";
        if (hashMapCustomer.containsKey(customerID)) {
            HashMap<String, HashMap<String, Integer>> addMap = hashMapCustomer.get(customerID);
            for (var letterEntry : addMap.entrySet()) {
                String letter = letterEntry.getKey();
                // ...
                for (var nameEntry : letterEntry.getValue().entrySet()) {
                    ret = ret + nameEntry.getValue() + " " + "Tickets have been Booked for Movie:" + letter + " " + "MovieID:" + nameEntry.getKey() + "\n";
                    // ...
                }
            }
        }
        return ret;
    }
    public String serverbookMovieTickets(String customerID, String movieID, String movieName, int numberOfTickets) throws Exception {
        String ret = "";

            if (hashMap == null) {
                return "No Data Found";
            }

            boolean check1 = hashMap.containsKey(movieName);
        System.out.println(check1);

            if (hashMap.containsKey(movieName)) {
                if (hashMap.get(movieName).containsKey(movieID)) {
                    int capacity = hashMap.get(movieName).get(movieID);
                    if (capacity >= numberOfTickets) {
                        hashMap.get(movieName).put(movieID, capacity - numberOfTickets);
                        if (!hashMapCustomer.containsKey(customerID)) {
                            HashMap<String, HashMap<String, Integer>> addressMap = new HashMap<>();
                            HashMap<String, Integer> addressMap1 = new HashMap<>();
                            addressMap1.put(movieID, numberOfTickets);
                            addressMap.put(movieName, addressMap1);
                            hashMapCustomer.put(customerID, addressMap);

                        } else {
                            HashMap<String, HashMap<String, Integer>> addressMap = hashMapCustomer.get(customerID);
                            if (!addressMap.containsKey(movieName)) {
                                HashMap<String, Integer> addressMap1 = new HashMap<>();
                                addressMap1.put(movieID, numberOfTickets);
                                addressMap.put(movieName, addressMap1);
                                hashMapCustomer.put(customerID, addressMap);


                            } else {
                                HashMap<String, Integer> addmap = addressMap.get(movieName);
                                addmap.put(movieID, numberOfTickets);
                                addressMap.put(movieName, addmap);
                                hashMapCustomer.put(customerID, addressMap);


                            }
                            ret="DONE SUCCESSFULLY";


                        }
                        ret="DONE SUCCESSFULLY";


                    } else {
                        ret = "Only" + capacity + "no of tickets available";
                    }

                } else {
                    ret = "No Shows Available for this MovieId";
                }


            } else {
                ret = "No Bookings for this MovieName Available";
            }
            return ret;

        }

    public String serverCancelMovieTickets(String customerID, String movieID, String movieName, int numberOfTickets) throws Exception {
        int tickets;
        String ret=null;


            if(hashMapCustomer.containsKey(customerID))
            {
                if(hashMapCustomer.get(customerID).containsKey(movieName))
                {
                    if (hashMapCustomer.get(customerID).get(movieName).containsKey(movieID))
                    {
                        tickets= hashMap.get(movieName).get(movieID);
                        hashMap.get(movieName).put(movieID,tickets+numberOfTickets);
                        ret="DONE SUCCESSFULLY";
                    }
                    else {
                        ret="DATABASE DOES NOT CONTAIN THIS MOVIE ID";
                    }
                }
                else {
                    ret="DATABASE DOES NOT CONTAIN THIS MOVIE";
                }
            }
            else{
                ret="INVALID CLIENT ID";

            }
            return  ret;
        }
    public String serverListMovieShowAvailability(String movieName) {
        String ret = "";
        if (hashMap.containsKey(movieName)) {
            for (var letter : hashMap.get(movieName).entrySet()) {
                ret = ret + " " + letter.getKey() + " " + letter.getValue() + "\n";
            }
        }
       return ret;
    }



}