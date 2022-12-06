import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class MailReader {
    private final String SP = " ";
    private final String LOGIN_CODE = "login";
    private final String CHECK_INBOX = "select inbox";
    private final String LOGOUT = "logout";
    private final int BUFFER_SIZE = 2048;
    private static final String CRLF = "\r\n";

    private SSLSocket socket;
    private SocketFactory socketFactory;
    private String host;
    private int port;
    private InputStream in;
    private OutputStream out;
    private byte[] msgBuffer;
    private int codeNr;
    private String codeStr;

    public MailReader (String host, int port){
        this.host = host;
        this.port = port;
        socketFactory = SSLSocketFactory.getDefault();
        try {
            socket = (SSLSocket) socketFactory.createSocket(host, port);
            in = socket.getInputStream();
            out = socket.getOutputStream();
        } catch (IOException e){
            e.printStackTrace();
        }
        msgBuffer = new byte[BUFFER_SIZE];
        codeNr = 1;
    }

    public String connectIMAP (){
        String retMSg = "";
        int len;
        try {
            len = in.read(msgBuffer);
            retMSg = new String(msgBuffer, 0, len);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retMSg;
    }

    public String loginIMAP (String username, String password){
        String retMsg = "";
        String loginMsg = genCode() + SP + LOGIN_CODE + SP + username + SP + password + CRLF;
        retMsg = simpleImapCmd(retMsg, loginMsg);
        return retMsg;
    }

    public String checkInbox (){
        String retMsg = "";
        String inboxCmd = genCode() + SP + CHECK_INBOX + CRLF;
        retMsg = simpleImapCmd(retMsg, inboxCmd);
        return retMsg;
    }

    public String logOut(){
        String retStr = "";
        String logOutCMd = genCode() + SP + LOGOUT + CRLF;
        retStr = simpleImapCmd(retStr, logOutCMd);
        return retStr;
    }

    public String fetchFull (long uid){
        StringBuilder retStr = new StringBuilder("");
        String fetchCmd = genCode() + " fetch " + uid + " full" + CRLF; 
        fetchIMAPCmd(retStr, fetchCmd);
        return retStr.toString();
    }

    public String fetchHeader (long uid){
        StringBuilder retStr = new StringBuilder("");
        String fetchHeaderCmd = genCode() + " fetch " + uid + " body[header]" + CRLF;
        fetchIMAPCmd(retStr, fetchHeaderCmd);
        return retStr.toString();
    }

    public String fetchBodyText (long uid){
        StringBuilder retStr = new StringBuilder("");
        String bodyTextCmd = genCode() + " fetch " + uid + " body[text]" + CRLF;
        fetchIMAPCmd(retStr, bodyTextCmd);
        return retStr.toString();
    }

    public String getCodeStr() {
        return codeStr;
    }

    public void closeConnection(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fetchIMAPCmd(StringBuilder retStr, String fetchCmd) {
        int len;
        try {
            out.write(fetchCmd.getBytes());
            out.flush();
            while ((len = in.read(msgBuffer, 0, BUFFER_SIZE)) >= BUFFER_SIZE){
                retStr.append(new String(msgBuffer, 0, len));
            }
            retStr.append(new String(msgBuffer, 0, len));
            if (!retStr.toString().contains("The specified message set is invalid") && !retStr.toString().contains("BAD Command received in Invalid state")){
                len = in.read(msgBuffer, 0, BUFFER_SIZE);
                retStr.append(new String(msgBuffer, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String simpleImapCmd(String retMsg, String msg) {
        int len;
        try {
            out.write(msg.getBytes());
            out.flush();
            len = in.read(msgBuffer);
            retMsg = new String(msgBuffer, 0, len);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retMsg;
    }

    private String genCode(){
        StringBuilder codeStr = new StringBuilder(String.valueOf(codeNr));
        while (codeStr.length() < 3){
            codeStr.insert(0, '0');
        }
        codeStr.insert(0, 'a');
        codeNr++;
        this.codeStr = codeStr.toString();
        return this.codeStr;
    }
}
