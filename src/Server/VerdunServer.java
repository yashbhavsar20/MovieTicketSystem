    package Server;

    import Implementation.implementation;

    import java.net.DatagramPacket;
    import java.net.DatagramSocket;
    import java.rmi.Naming;
    import java.rmi.RemoteException;
    import java.util.HashMap;

    public class VerdunServer {
        public static void main(String[] args) throws RemoteException {
            implementation i;
            i = new implementation();
            i.setName("VER");
//            HashMap<String,Integer> add1=new HashMap<>();
//            add1.put("ATWA123456",76);
//            HashMap<String,HashMap<String,Integer>>add=new HashMap<>();
//            add.put("AVATAR",add1);
//
//            i.hashMapCustomer.put("ATWC123456",add);

            try {
                //LocateRegistry.createRegistry(4555);

                Naming.rebind("VERServer", i);//object will be stored in rmi registry
                System.out.println("Verdun server ready");
            } catch (Exception e) {
                System.out.println(e);
            }
            while (true) {
                try (DatagramSocket aSocket = new DatagramSocket(6887)) {
                    String result = "";
                    byte[] buffer = new byte[1000];
                    DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                    aSocket.receive(request);
                    String data = new String(request.getData()).trim();
                    String[] data1=data.split(":");

                    switch (data1[0])
                    {
                        case "getBookingSchedule":
                            result=i.getServerBookingSchedule(data1[1]);
                            break;
                        case "bookMovieTickets":
                            String[] para=data1[1].split(" ");
                            System.out.println(para[0]+para[1]+para[2]+Integer.parseInt(para[3]));
                            result=i.serverbookMovieTickets(para[0],para[2],para[1],Integer.parseInt(para[3]));
                            System.out.println("result"+result);
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