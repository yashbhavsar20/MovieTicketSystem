package Interfacee;

import java.rmi.*;

public interface Interface extends Remote{
    public int say(int number1,int number2) throws Exception;

}