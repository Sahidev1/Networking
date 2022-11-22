package server;

import java.util.ArrayDeque;
import java.util.Deque;


// LIFO QUEUE
public class ThreadMessageQ {
    public long thread_id;
    private Deque<String> messageQ;

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
}
