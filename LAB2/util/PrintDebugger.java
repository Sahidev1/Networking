package util;

public class PrintDebugger {
    private static int debugIndex = 0;

    public static synchronized void debug(){
        long threadID = Thread.currentThread().getId();
        System.out.println("Debug(callNr: " + (++debugIndex) 
        +", Thread: " + threadID +")");
    }

    public static synchronized void debug(String msg){
        long threadID = Thread.currentThread().getId();
        System.out.println("Debug(callNr: " + (++debugIndex) 
        +", Thread: " + threadID +", Message: " + msg +")");
    }
}
