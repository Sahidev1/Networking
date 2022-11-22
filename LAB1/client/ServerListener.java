package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String msg;
            while((msg = reader.readLine()) != null){
                
                /*msgLen = in.read(message);
                if(msgLen != -1){
                    System.out.println(new String(message,0,msgLen));
                    message = new byte[255];            
                }*/
                if (msg.split(" ").length > 1){
                    System.out.println("\n" + msg);
                } 
                else {
                    System.out.println("\b");
                }
            }
        } catch (IOException exc){
            System.out.println(exc);
        }
    }
}
