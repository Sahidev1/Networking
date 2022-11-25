package controller;

import java.net.InetAddress;
import java.util.HashMap;

import model.GuessingGame;

public class Controller {
    private HTTPServer server;
    private Thread serverThread;
    private final int MAX_BACKLOG = 32;
    private HashMap<String, GuessingGame> gameInstances;
    private InetAddress serverIP;
    private int serverPort;

    public Controller (InetAddress serverIP, int serverPort){
        System.out.println("controller started");
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.gameInstances = new HashMap<>();
        server = new HTTPServer(this);
        serverThread = new Thread(server);
        serverThread.start();
    }

    public HashMap<String, GuessingGame> getGameInstances() {
        return gameInstances;
    }

    public InetAddress getServerIP() {
        return serverIP;
    }

    public int getServerPort() {
        return serverPort;
    }

    public int getMaxBacklog() {
        return MAX_BACKLOG;
    }   

}
