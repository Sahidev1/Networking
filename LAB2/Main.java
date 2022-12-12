import java.net.InetAddress;
import java.net.UnknownHostException;

import controller.Controller;

public class Main {
    public static void main(String[] args) {
        try {
            InetAddress addr = InetAddress.getByName(args[0]);
            int port = Integer.parseInt(args[1]);
            Controller ctrl = new Controller(addr, port);
        }
        catch (UnknownHostException exc){
            exc.printStackTrace();
        }
    }
}