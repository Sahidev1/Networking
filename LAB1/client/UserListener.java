package client;

import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;


public class UserListener implements Runnable {
    private String message;
    private final String CLOSE_PROGRAM = "!exit";
    private Socket socket;
    private OutputStream out;
    private int outLen;

    public UserListener (Socket socketInstance){
        this.socket = socketInstance;
    }

    public void run(){
        message = "";
        Console console = System.console();
        String userInput;
        try{
            out = socket.getOutputStream();
            do {
                userInput = console.readLine("bloop: ");
                out.write(userInput.getBytes());                
            } while (!message.equals(CLOSE_PROGRAM));
        } catch (IOException exc){
            System.out.println(exc);
        }
    }
}
