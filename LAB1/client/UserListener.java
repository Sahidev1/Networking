package client;

import java.util.Scanner;

public class UserListener implements Runnable {
    private String message;
    private final String CLOSE_PROGRAM = "!exit";
    private Scanner in;
    
    public UserListener (){
        in = new Scanner(System.in);
    }

    public void run(){
        message = "";
        do {
            while(!in.hasNext());
            message = in.nextLine();
            
        } while (!message.equals(CLOSE_PROGRAM));
    }
}
