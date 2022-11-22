package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;

public class ChatClient {
    static Thread userThread, clientThread;
    static Socket socketInstance;
    static boolean connflag;
    public static void main(String[] args) {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        ServerListener listener;
        connflag = true;
        while (true){
            try {
                socketInstance = new Socket(host, port);
            }
            catch (SocketException e){
                //do nothing, will be caught again down the line
                connflag = false;
            }
            catch (IOException exc){
                exc.printStackTrace();
            }
            listener = new ServerListener(socketInstance);
            clientThread = new Thread(listener);
            clientThread.start();

            
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
                        listener.username = username;
                        firstLoop = false;
                        System.out.print("@" + username + ": ");
                        continue;
                    }
                    System.out.print("@" + username + ": ");
                    out.write("@"+ username + ": " + input);
                    out.flush();    
                }
            }
            catch (IOException ioe){
                System.out.println("Can't connect to server!");
            }
        }
    }
}
