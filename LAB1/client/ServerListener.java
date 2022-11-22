package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerListener  implements Runnable{
    private Socket socket;
    private InputStream in;
    public String username = "userUnknown";

    public ServerListener (Socket socketInstance){
        this.socket = socketInstance;
    }

    public void run(){
        try {
            in = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String msg;
            while((msg = reader.readLine()) != null && ChatClient.connflag){
                if (msg.split(" ").length > 1){
                    System.out.println("\n" + msg);
                    System.out.print("@" + username + ": ");
                } 
                else {
                    System.out.print("\b");
                }
            }
        } catch (IOException exc){
            System.out.println(exc);
        }
        finally{
            //System.out.println("Thread " + Thread.currentThread().getId() + " killed");
        }
    }
}
