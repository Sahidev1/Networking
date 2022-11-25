package controller;

import java.io.IOException;
import java.net.ServerSocket;

public class HTTPServer implements Runnable {
    private Controller ctrl;
    private ServerSocket serverSocket;
    private Thread thread;
    HTTPHandler httpHandler;

    public HTTPServer (Controller ctrl){
        this.ctrl = ctrl;
    }

    public void run(){
        try {
            serverSocket = new ServerSocket(ctrl.getServerPort(), ctrl.getMaxBacklog() ,ctrl.getServerIP());
            while(true){
                httpHandler = new HTTPHandler(serverSocket.accept(), ctrl.getGameInstances());
                thread = new Thread(httpHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
