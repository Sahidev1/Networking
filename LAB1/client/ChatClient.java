package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ChatClient {
    static UserListener userListener;
    static Thread userThread, clientThread;
    static Socket socketInstance;
    static final String USER_NAME = "%USER_NAME%=";
    public static void main(String[] args) {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        try {
            socketInstance = new Socket(host, port);
            clientThread = new Thread(new ServerListener(socketInstance));
            clientThread.start();

        }
        catch (IOException exc){
            System.out.println(exc.getMessage());
            System.exit(1);
        }

        
        BufferedWriter out;
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String input;
        String username = "guest";
        
        boolean firstLoop = true;
        try {
            out = new BufferedWriter(new OutputStreamWriter(socketInstance.getOutputStream()));
            System.out.println("Enter username!");

            while ((input = stdin.readLine()) != null){
                if (firstLoop){
                    username = input;
                    firstLoop = false;
                    continue;
                }
                out.write("@"+ username + ": " + input);
                out.flush();    
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
