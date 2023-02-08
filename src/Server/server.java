package Server;

import Implementation.implementation;

import java.rmi.Naming;

public class server {
    public static void main(String[] args) {
        try {
            //LocateRegistry.createRegistry(4555);
            implementation i;
            i = new implementation();
            Naming.rebind("MyImplementation", i);//object will be stored in rmi registry
            System.out.println("Server.server ready");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}