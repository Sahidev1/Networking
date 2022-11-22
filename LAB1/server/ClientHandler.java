package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private final int READ_BUFFER_SIZE = 255; 
    private Socket socket;
    private final byte[] readBuffer = new byte[READ_BUFFER_SIZE];
    InputStream in;
    OutputStream out;
    private int inLen;
    long threadID;
    int listIndex;

    private List<ThreadMessageQ> mQueue;

    public ClientHandler(Socket socket, List<ThreadMessageQ> mQueue){
        this.socket = socket;
        this.mQueue = mQueue;
    }

    public void run(){
        threadID = Thread.currentThread().getId();
        ThreadMessageQ myTQ;
        synchronized(mQueue){
            myTQ = new ThreadMessageQ(threadID);
            mQueue.add(myTQ);
        }

        try{
            in = socket.getInputStream();
            out = socket.getOutputStream();
            while (true){
                inLen = in.read(readBuffer);
                String readStr = new String(readBuffer, 0, inLen);
                readStr = readStr + System.lineSeparator();
                
                // Printing for debug purposes
                System.out.println(readStr);

                synchronized(mQueue){
                    for (ThreadMessageQ threadMessageQ : mQueue) {
                        if (threadMessageQ.thread_id != threadID){
                            threadMessageQ.addMessage(readStr);
                        }
                        else{
                            while (!threadMessageQ.isEmpty()){
                                out.write(threadMessageQ.getMessage().getBytes());
                            }     
                        }
                        
                    }
                }
            }
        } catch (IOException exc){
            System.out.println(exc);
        }
    }
}
