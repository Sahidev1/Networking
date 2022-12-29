/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;
import javax.mail.internet.MimeBodyPart;

/**
 *
 * @author ali
 */
public class EmailReader {
    private Properties props;
    private Session session;
    private final String host;
    private final int port;
    private final String emailAddr;
    private final String username;
    private final String password;
    private final static String SEPERATOR = "---------------------------------------------------------------------------------------------------------------------------------------------------------------";
    private final static String NEWLINE = "\n";
    
    public EmailReader (String host, int port, String emailAddr, String password){
        this.host = host;
        this.port = port;
        this.emailAddr = emailAddr;
        this.username = emailAddr.split("@")[0];
        this.password = password;
        initSession();
    }
    
    public String readInbox (){
        return readBox("inbox");
    }
    
    public String readBox (String boxName){
        StringBuilder sb = new StringBuilder();
        try {
            Store store = session.getStore("imap");
            store.connect(host, port, username, password);
            Folder folder = store.getFolder(boxName);
            folder.open(Folder.READ_WRITE);
            int totalMessages = folder.getMessageCount();
            int newMessages = folder.getNewMessageCount();
            int unread = folder.getUnreadMessageCount();
            UIDFolder ufolder = (UIDFolder) folder;
            Message[] msges = folder.getMessages();
            sb.append("* " + totalMessages + " total messages\n");
            sb.append("* " + newMessages + " new messages\n");
            sb.append("* " + unread + " unread messages\n");
            int j = 0;
            int maxDisplay = 10;
            long currUID = 0;
            for (int i = msges.length - 1; i > 0; i--){
                currUID = ufolder.getUID(msges[i]);
                appendln(sb, SEPERATOR);
                appendln(sb, "UID: " + currUID);
                appendln(sb, "SUBJECT: " + msges[i].getSubject());
                appendln(sb, "DATE: " + msges[i].getReceivedDate().toString());
                if (j++ > maxDisplay) break;
            }
            appendln(sb, SEPERATOR);
            appendln(sb, "MAX VALID UID: " + ufolder.getUID(msges[msges.length - 1]));
            folder.close();
            store.close();
        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        } 
        
        return sb.toString();
    }
    
    public String getInboxMessageStringFormatted (long uid){
        return getInboxMessage(uid);
    }
    
    public String getInboxMessage (long uid){
        return getMessage(uid, "inbox");
    }
    
    public String getMessage(long uid, String boxName){
        Message msg = null;
        String msgStr = null;
        try {
            Store store = session.getStore("imap");
            store.connect(host, port, username, password);
            Folder folder = store.getFolder(boxName);
            folder.open(Folder.READ_WRITE);
            UIDFolder ufolder = (UIDFolder) folder;
            msg = ufolder.getMessageByUID(uid);
            msgStr = messageFormatter (msg);
            folder.close();
            store.close();
        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return msgStr;
    }
    
    public String messageFormatter (Message msg){
        StringBuilder sb = new StringBuilder();
        if (msg == null) return "no message with UID found";
        try {
            int msgnr = msg.getMessageNumber();
            String contentType = msg.getContentType();
            Address[] fromAddresses = msg.getFrom();
            Date receivDate = msg.getReceivedDate();
            String subject = msg.getSubject();
            appendln(sb, SEPERATOR);
            appendln(sb, "CONTENT-TYPE: " + contentType);
            appendln(sb, "RECEIVE DATE: " + receivDate.toString());
            sb.append("MESSAGE FROM SENDER(S): ");
            for (Address addr: fromAddresses){
                sb.append(addr.toString() + ", ");
            }
            appendln(sb, "");
            appendln(sb, "SUBJECT: " + subject);
            appendln(sb, "");
            appendln(sb, "___ MESSAGE CONTENT BELOW ____");
            partHandler((Object) msg, sb);
            /*
            if (msg.getContentType().toLowerCase().contains("text/html")){
                appendln(sb, (String)msg.getContent());
            }*/
            /*else {
                MimeMessage mimemsg = (MimeMessage) msg;
                InputStream in = mimemsg.getInputStream();
                byte[] buffer = new byte[in.available() + 10];
                int readBytes = in.read(buffer, 0, buffer.length);
                String contentStr = new String(buffer, 0, readBytes);
                appendln(sb, contentStr);
                appendln(sb, "");
            }*/
            
            
        } catch (MessagingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) { 
            ex.printStackTrace();
        }
        return sb.toString();
    }
    
    private void partHandler (Object obj, StringBuilder sb) throws IOException, MessagingException{
        if (obj instanceof Message){
            Message msg = (Message) obj;
            if (msg.getContentType().toLowerCase().contains("text/html")){
                String body = (String) msg.getContent();
                appendln(sb, body);
            }
            else {
                partHandler (msg.getContent(), sb);
            }
        }
        else if (obj instanceof Multipart){
            Multipart mpart = (Multipart) obj;
            int count = mpart.getCount();
            for (int i = 0; i < count; i++){
                partHandler(mpart.getBodyPart(i), sb);
            }
        }
        else if (obj instanceof InputStream){
            InputStream in = (InputStream) obj;
            String streamData = inputStreamReader (in);
            appendln(sb, streamData);
        }
        else if (obj instanceof String){
            appendln(sb, (String) obj);
        }
        else if (obj instanceof MimeBodyPart){
            MimeBodyPart mim = (MimeBodyPart) obj;
            partHandler(mim.getContent(), sb);
        }
        else {
            System.out.println("none: " + obj.getClass().getName());
        }
    }
    
    private String inputStreamReader (InputStream in){
        final int buffer_size = 2048;
        byte[] buffer = new byte[buffer_size];
        StringBuilder dataBuilder = new StringBuilder ();
        
        try {
            int len;
            while ((len = in.read(buffer, 0, buffer_size)) > 0){
                dataBuilder.append(new String(buffer, 0, len));
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        
        return dataBuilder.toString();
    }
    
    private void appendln (StringBuilder sb,String s){
        sb.append(s + NEWLINE);
    }
    
    private void initSession (){
        props = new Properties();
        props.setProperty("mail.store.protocol", "imap");
        props.setProperty("mail.imap.host", host);
        props.setProperty("mail.imap.port", String.valueOf(port));
        props.setProperty("mail.imap.ssl.enable","true");
        session = Session.getInstance(props);
    }
}
