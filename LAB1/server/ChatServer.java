package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class ChatServer {
    static ServerSocket serverSocket;
    private static final int MAX_BACKLOG = 32;
    public static void main(String[] args) {
        try{
            InetAddress ipAddr = InetAddress.getByName(args[0]);
            int serverPort = Integer.parseInt(args[1]);

        
            serverSocket = new ServerSocket(serverPort, MAX_BACKLOG, ipAddr);
            System.out.println("Server IP: " + ipAddr.toString());
            while (true){
                //thread handler
            }
        
        } catch (IOException exc){
            System.out.println(exc);
        }
    }
}