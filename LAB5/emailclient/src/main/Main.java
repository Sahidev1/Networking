/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import client.EmailReader;
import client.EmailReaderProcedure;
import client.EmailSender;
import client.EmailSenderProcedure;
import java.io.Console;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ali
 */
public class Main {
    private final static Map<String, EmailSender> emailSenders = new HashMap<>();
    private final static Map<String, EmailReader> emailReaders = new HashMap<>();
    private static Console console = System.console();
    private final static EmailSenderProcedure senderProc = new EmailSenderProcedure(console, emailSenders);
    private final static EmailReaderProcedure readerProc = new EmailReaderProcedure(console, emailReaders);
    
    public static void main (String[] args){
        boolean mainRun = true;
        int opt;
        
        while (mainRun){
            try {
                System.out.println("Options:\n1) Email sender\n2) Email reader\n3) Exit program");
                opt = Integer.parseInt(console.readLine(">"));
                switch (opt){
                    case 1: 
                        senderProc.run();
                        break;
                    case 2:
                        readerProc.run();
                        break;
                    case 3:
                        mainRun = false;
                        break;
                    default:
                        System.out.println ("Unknown command, type 3 to exit");
                        break;
                }

            } catch (NumberFormatException e){
                e.printStackTrace();
                System.out.println("Please enter a numeric command!");
            }
        }
    } 
}
