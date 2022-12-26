/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import client.EmailSender;
import java.io.Console;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ali
 */
public class Main {
    private final static Map<String, EmailSender> emailSenders = new HashMap<>();
    private static Console console = System.console();
    
    public static void main (String[] args){
        emailSenderProcedure();
    } 
    
    private static void emailSenderProcedure (){
        EmailSender currSender = null;
        boolean run = true;
        
        while (run){
            println("Options:\n1) Send message using current email"
                    + "\n2) Add new sender email\n3) set current email\n enter anything else to exit email sending");
            int choice = Integer.parseInt(console.readLine(">"));
            if (choice == 1){
                createAndSendEmail(currSender);
            }
            else if (choice == 2){
                println("Enter smtp host:");
                String smtpHost = console.readLine(">");
                println("Enter smtp port:");
                String smtpPort = console.readLine(">");
                println("Enter sender email address:");
                String senderEmail = console.readLine(">");
                println("Enter email address password:");
                String password = new String(console.readPassword(">"));
                emailSenders.put(senderEmail, new EmailSender(smtpHost, smtpPort, senderEmail, password));
            }
            else if (choice == 3){
                println("Available emails to pick: ");
                for (String email: emailSenders.keySet()){
                    println(email);
                }
                println("Enter email address you would like to pick: ");
                String pickedEmail = console.readLine(">");
                if (emailSenders.containsKey(pickedEmail)){
                    currSender = emailSenders.get(pickedEmail);
                } else {
                    println("no such email exists among senders");
                }
            }
            else {
                println("Exiting mail sender");
                run = false;
            }
        }
    }
    
    private static void createAndSendEmail (EmailSender currSender){
        while (currSender != null){
            println ("Options:\n1) add recipient\n2) Set subject\n3) write email message\n4) send email\n5) remove recipient\n6) exit message sending");
            switch (Integer.parseInt(console.readLine(">"))){
                case 1:
                    println ("Enter recipient email:");
                    currSender.addRecipient(console.readLine(">"));
                    break;
                case 2:
                    println ("Enter message subject: ");
                    currSender.writeSubject(console.readLine(">"));
                    break;
                case 3:
                    println ("Write message, to end message type CRLF'.'CRLF");
                    currSender.writeMessage(writeEmailBody());
                    break;
                case 4:
                    println ("email sent");
                    currSender.sendMessage();
                    break;
                case 5: 
                    println ("Enter recipient email to remove:");
                    currSender.removeRecipient(console.readLine(">"));
                    break;
                case 6:
                    return;
                default: 
                    break;
            }
        }
    }
    
    private static String writeEmailBody (){
        StringBuilder sb = new StringBuilder ();
        boolean keepWriting = true;
        String read = "";
        while(keepWriting){
            read = console.readLine(">");
            if (read.equals(".")) keepWriting = false;
            else sb.append(read + "\n");
        }
        return sb.toString();
    }
    
    private static void println (String s){
        System.out.println(s);
    }
}
