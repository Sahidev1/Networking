package util;

public class PrintDebugger {
    private static int debugIndex = 0;

    public static synchronized void debug(){
        long threadID = Thread.currentThread().getId();
        System.out.println("Debug(callNr: " + (++debugIndex) 
        +", Thread: " + threadID +")");
    }
}
