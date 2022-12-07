import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Base64;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class MailSender {
    private final int BUFFER_SIZE = 2048;
    private final String CRLF = "\r\n";

    private Socket initSocket;
    private SSLSocket sslSocket;
    private String host;
    private int port;
    private InputStream in;
    private OutputStream out;
    private byte[] readBuffer;
    private String client;

    public MailSender (String host, int port, String client){
        this.host = host;
        this.port = port;
        this.client = client;
        try {
            initSocket = new Socket(host, port);
            in = initSocket.getInputStream();
            out = initSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        readBuffer = new byte[BUFFER_SIZE];
    }

    public void upgradeSocketTLS (){
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try {
            sslSocket = (SSLSocket) factory.createSocket(initSocket, initSocket.getInetAddress().getHostAddress(),
            initSocket.getPort(), true);    
            in = sslSocket.getInputStream();
            out = sslSocket.getOutputStream();        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String initConnection (){
        String retMsg = "";
        int len;
        try {
            len = in.read(readBuffer, 0, BUFFER_SIZE);
            retMsg = new String(readBuffer, 0, len);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retMsg;
    }

    public String emailSendFrom (String fromEmail){
        String writeMsg = "MAIL FROM:<" + fromEmail + ">";
        return writeSMTP(writeMsg);
    }

    public String emailSendTo (String toEmail){
        String writeMsg ="RCPT TO:<" + toEmail + ">";
        return writeSMTP(writeMsg);
    }

    public String loginSMTP (String username, String password){
        username = Base64.getEncoder().encodeToString(username.getBytes());
        password = Base64.getEncoder().encodeToString(password.getBytes());
        String retMsg = "";
        int len;
        String writeMsg = username + CRLF;
        try {
            out.write(writeMsg.getBytes());
            out.flush();
            len = in.read(readBuffer, 0, BUFFER_SIZE);
            retMsg = new String(readBuffer, 0, len);

            writeMsg = password + CRLF;
            out.write(writeMsg.getBytes());
            out.flush();
            len = in.read(readBuffer, 0, BUFFER_SIZE);
            retMsg = new String(readBuffer, 0, len);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retMsg;
    }

    public String sayHELO(){
        String wrtMSg = "EHLO " + client;
        return writeSMTP(wrtMSg);
    }

    public void closeConnection(){
        try {
            if (sslSocket != null){
                sslSocket.close();
            }
            if (initSocket != null){
                initSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String writeSMTP (String wrtMSg) {
        String retMsg = "";
        int len;
        wrtMSg += CRLF;
        try {
            out.write(wrtMSg.getBytes());
            out.flush();
            len = in.read(readBuffer, 0, BUFFER_SIZE);
            retMsg = new String(readBuffer, 0, len);
            System.out.print("C: " + wrtMSg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retMsg;
    }
}
