package server;

import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.Deque;


/**
 * FIFO Queue of messages to send for a particular thread
 */
public class ThreadMessageQ {
    public long thread_id;
    private Deque<String> messageQ;
    private OutputStream out;

    public ThreadMessageQ (long thread_id){
        this.thread_id = thread_id;
        this.messageQ = new ArrayDeque<>();
    }

    public void addMessage (String msg){
        messageQ.addFirst(msg);
    }
    
    public String getMessage(){
        return messageQ.removeLast();
    }

    public boolean isEmpty(){
        return messageQ.isEmpty();
    }

    public OutputStream getOut() {
        return out;
    }

    public void setOut(OutputStream out) {
        this.out = out;
    } 

    public boolean isOutSet (){
        return out != null;
    }
}
