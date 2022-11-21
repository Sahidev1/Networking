package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    
    public ClientHandler(Socket socket){
        this.socket = socket;
    }

    public void run(){
        try{
            InputStream in = socket.getInputStream();
        } catch (IOException exc){

        }
    }
}
