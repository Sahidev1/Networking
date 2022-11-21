package client;

public class ChatClient {
    static UserListener userListener;
    static Thread thread;
    public static void main(String[] args) {
        userListener = new UserListener();
        thread = new Thread(userListener);
        thread.start();
    }
}
