package server;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ClientMessager implements Runnable {
    private List<ThreadMessageQ> mQueues;
    private OutputStream currOut;

    public ClientMessager (List<ThreadMessageQ> mQueues){
        this.mQueues = mQueues;
    }

    public void run(){
        while (true){
            messageClients();
        }
    }

    private void messageClients (){
        synchronized(mQueues){
        for (ThreadMessageQ threadMessageQ : mQueues) {
            if (!threadMessageQ.isOutSet()) continue;
            currOut = threadMessageQ.getOut();
            while (!threadMessageQ.isEmpty()){
                try {
                    currOut.write(threadMessageQ.getMessage().getBytes());
                } catch (IOException e){
                    System.out.println("messageClients method Error!");
                    e.getStackTrace();
                }
            }
        }
        }
    }
}
