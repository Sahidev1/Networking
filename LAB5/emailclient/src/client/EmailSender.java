/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author ali
 */
public class EmailSender {
    private Properties props;
    private final String fromEmail;
    private final String password;
    private final String smtpHost;
    private final String smtpPort;
    private List<String> recipients;
    private Session session;
    private String body;
    private String subject;
    private String username;
    
    public EmailSender (String smtpHost, String smtpPort, String fromEmail, String password){
        this.fromEmail = fromEmail;
        this.username = fromEmail.split("@")[0];
        this.password = password;
        this.session = null;
        this.body = "";
        this.subject = "no subject";
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.recipients = new ArrayList<>();
        initProps();
        initAuthSession();
    }
    
    public void sendMessage (){
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            
            msg.setFrom(new InternetAddress(fromEmail));
            msg.setReplyTo(new InternetAddress[]{new InternetAddress(fromEmail)});
            msg.setSubject(subject, "UTF-8");
            msg.setText(body, "UTF-8");
            msg.setSentDate(new Date());
            
            InternetAddress[] recipAddrs = new InternetAddress[recipients.size()];
            int i = 0;
            for (String recipient: recipients){
                recipAddrs[i++] = new InternetAddress(recipient);
            }
            msg.setRecipients(Message.RecipientType.TO, recipAddrs);
            Transport.send(msg);
            
        } catch (MessagingException e){
            e.printStackTrace();
        }
    }
    
    public void addRecipient (String recipient){
        recipients.add(recipient);
    }
    
    public boolean removeRecipient (String recipient){
        for(String recip: recipients){
            if (recip.equals(recipient)){
                recipients.remove(recip);
                return true;
            }
        }
        return false;
    }
    
    public void clearRecipientList (){
        recipients.clear();
    }
    
    public void writeSubject (String subject){
        this.subject = subject;
    }
    
    public void writeMessage (String body){
        this.body = body;
    }
    
    private void initAuthSession (){
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        
        Authenticator auth = new Authenticator (){
            protected PasswordAuthentication getPasswordAuthentication (){
                return new PasswordAuthentication(username, password);
            }
        };
        session = Session.getInstance(props, auth);
    }
    
    private void initProps (){
        this.props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
    }
}
