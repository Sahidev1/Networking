/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;

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
            sb.append("* " + totalMessages + " messages exist\n");
            sb.append("* " + newMessages + " new messages\n");
            sb.append("* " + unread + " unread messages\n");
            folder.close();
            store.close();
        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        } 
        
        return sb.toString();
    }
    
    public Message getMessage(long uid, String boxName){
        try {
            Store store = session.getStore("imap");
            store.connect(host, port, username, password);
            Folder folder = store.getFolder(boxName);
            UIDFolder ufolder = (UIDFolder) folder;
            Message msg = ufolder.getMessageByUID(uid);
        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return msg;
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
