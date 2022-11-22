package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatServer {
    static ServerSocket serverSocket;
    static ClientHandler clientHandler;
    static Thread thread;
    private static final int MAX_BACKLOG = 32;
    static List<String> userNames;

    public static void main(String[] args) {
        userNames = new ArrayList<String>();
        //List<byte[]> messageQueue = new ArrayList<>();
        List<ThreadMessageQ> mQueues = new ArrayList<>();
        try{
            InetAddress ipAddr = InetAddress.getByName(args[0]);
            int serverPort = Integer.parseInt(args[1]);

        
            serverSocket = new ServerSocket(serverPort, MAX_BACKLOG, ipAddr);
            System.out.println("Server IP: " + ipAddr.toString());
            while (true){
                clientHandler = new ClientHandler(serverSocket.accept(),mQueues);
                thread = new Thread(clientHandler);
                thread.start();
            }
        
        } catch (IOException exc){
            System.out.println(exc);
        }
    }
}