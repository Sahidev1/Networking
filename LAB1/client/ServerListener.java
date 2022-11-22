package client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ServerListener  implements Runnable{
    private byte[] message;
    private Socket socket;
    private InputStream in;
    private int msgLen;

    public ServerListener (Socket socketInstance){
        this.socket = socketInstance;
    }

    public void run(){
        message = new byte[255];
        try {
            in = socket.getInputStream();
            while(true){
                msgLen = in.read(message);
                if(msgLen != -1){
                     System.out.println(new String(message,0,msgLen));
                     message = new byte[255];            
                }
            }
        } catch (IOException exc){
            System.out.println(exc);
        }
    }
}
