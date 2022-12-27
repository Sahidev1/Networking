/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.Console;
import java.util.Map;

/**
 *
 * @author ali
 */
public class EmailReaderProcedure implements Procedure {
    private Console console;
    private Map<String, EmailReader> emailReaders;
    private EmailReader currReader;
    
    public EmailReaderProcedure (Console console, Map<String, EmailReader> emailReaders){
        this.console = console;
        this.emailReaders = emailReaders;
        currReader = null;
    }
    
    public void run (){
        boolean run = true;
        while (run){
            try{
                println ("Options:\n1) Check inbox\n2) Get message by UID from inbox" 
                + "\n3) Add new reader email\n4) Set current email\n5) Exit reader");
                int choice = Integer.parseInt(console.readLine(">"));
                run = handleChoice (choice);
            } catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
    }
    
    private boolean handleChoice (int choice){
        switch (choice){
            case 1:
                if (currReader != null){
                    String inboxInfo = currReader.readInbox();
                    println("___ INBOX CHECK ___");
                    println(inboxInfo);
                } else {
                    println("Please set email address to read from first");
                }
                break;
            case 2:
                if (currReader != null){
                    println("Enter message UID to read message content");
                    long uid = Long.parseLong(console.readLine(">"));
                    String message = currReader.getInboxMessageStringFormatted(uid);
                    println(message);
                } else {
                    println("Please set email address to read from first");
                }
                break;
            case 3:
                println("Enter imap host:");
                String host = console.readLine(">");
                println("Enter imap port:");
                int port = Integer.parseInt(console.readLine(">"));
                println ("Enter reader email address: ");
                String readerEmail = console.readLine(">");
                println ("Enter email password: ");
                String password = String.valueOf(console.readPassword(">"));
                emailReaders.put(readerEmail, new EmailReader (host, port, readerEmail, password));
                break;
            case 4:
                println ("Available emails to pick from: ");
                for (String email: emailReaders.keySet()){
                    println(email);
                }
                println("Enter email address you would like to pick: ");
                String pickedEmail = console.readLine(">"); 
                if (emailReaders.containsKey(pickedEmail)){
                    currReader = emailReaders.get(pickedEmail);
                } else {
                    println ("no such email exists among readers");
                }
                break;
            case 5:
                println("Exiting email reader");
                return false;  
            default:
                println("Invalid command, type 5 to exit");
                break;     
        }
        return true;
    }
    
    private static void println(String s){
        System.out.println(s);
    }
}
