package Server;

import Implementation.implementation;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;

public class AtwaterServer {
    public static void main(String[] args) throws RemoteException {
        implementation i = new implementation();
        i.setName("ATW");
        try {

            Naming.rebind("ATWServer", i);//object will be stored in rmi registry
            System.out.println("Atwater server ready");
        } catch (Exception e) {
            System.out.println(e);
        }
        while (true) {
            try (DatagramSocket aSocket = new DatagramSocket(6890)) {
                String result = "";
                byte[] buffer = new byte[1000];
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);


                String data = new String(request.getData()).trim();
                System.out.println(data);
                String[] data1=data.split(":");
                switch (data1[0])
                {
                    case "getBookingSchedule":
                        result=i.getServerBookingSchedule(data1[1]);
                        break;
                    case "bookMovieTickets":
                        String[] para=data1[1].split(" ");
                        result=i.serverbookMovieTickets(para[0],para[1],para[2],Integer.parseInt(para[3]));
                        break;
                    case "cancelMovieTickets":
                        String[] para1=data1[1].split(" ");
                        System.out.println(para1[0]+para1[1]+para1[2]+Integer.parseInt(para1[3]));
                        result=i.serverCancelMovieTickets(para1[0],para1[2],para1[1],Integer.parseInt(para1[3]));
                        System.out.println("result"+result);
                        break;
                    case "listMovieShowAvailability":
                        result=i.serverListMovieShowAvailability(data1[1]);
                        System.out.println("result"+result);
                        break;
                }


                DatagramPacket reply = new DatagramPacket(result.getBytes(), result.length(), request.getAddress(),
                        request.getPort());
                aSocket.send(reply);

            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
}