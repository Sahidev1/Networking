package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final int READ_BUFFER_SIZE = 255; 
    private Socket socket;
    private final byte[] readBuffer = new byte[READ_BUFFER_SIZE];
    InputStream in;
    OutputStream out;
    
    public ClientHandler(Socket socket){
        this.socket = socket;
    }

    public void run(){
        try{
            in = socket.getInputStream();
            out = socket.getOutputStream();
            int len = in.read(readBuffer);
            String readStr = new String(readBuffer, 0, len);
            StringBuilder response = new StringBuilder("This is respone!");
            out.write(response.toString().getBytes());
            socket.close();
        } catch (IOException exc){
            System.out.println(exc);
        }
    }
}
