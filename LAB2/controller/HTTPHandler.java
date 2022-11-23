package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

import model.GuessingGame;
import util.HTTPRequest;
import util.PrintDebugger;
import view.HTTPresponse;
import view.HtmlObjects;

public class HTTPHandler implements Runnable{
    private Socket socket;
    private HashMap<Long, GuessingGame> gameInstances;
    private long threadID;
    private InputStream in;
    private OutputStream out;
    private final int READ_BUFFER_SIZE = 1024;
    private byte[] read_buffer;


    public HTTPHandler(Socket socket, HashMap<Long, GuessingGame> gameInstances){
        this.socket = socket;
        this.gameInstances = gameInstances;
    }

    public void run(){
        System.out.println("thread activated");
        read_buffer = new byte[READ_BUFFER_SIZE];
        threadID = Thread.currentThread().getId();
        GuessingGame game = new GuessingGame();
        synchronized(gameInstances){
            gameInstances.put(threadID, game);
        }
        String message="";
        String write = "Hello there!";
        int len;
        
        HtmlObjects htmlObj = HtmlObjects.getHtmlObjects();
        HTTPRequest req;
        HTTPresponse resp;
        try {
            out = socket.getOutputStream();
            in = socket.getInputStream();
            while(true){
                if((len = in.read(read_buffer)) > 0){
                    message = new String(read_buffer,0,len);
                }
                req = new HTTPRequest(new String(message));
                if(req.isValid()){
                    debug();
                    debug();
                    req.generateParameters(); 
                }
                if(req.isValid() && req.isNoParameters()){
                    resp = new HTTPresponse("200", "OK", htmlObj.getHtmlWithMessage("Guess the number between 0 and 100!"));
                    write = resp.generateHTTPresponse();
                }
                if(req.isValid() && !req.isNoParameters()){
                    String addMsg = "";
                    if (req.doesParameterNameExist("guess")){
                        int guessval = Integer.parseInt(req.getParameterValue("guess"));
                        if(game.guess(guessval)){
                            addMsg += "The number " + guessval + " is correct!";
                            synchronized(gameInstances){
                                gameInstances.remove(threadID);
                                game = new GuessingGame();
                                gameInstances.put(threadID, game);
                            }
                        }
                        else {
                            if(game.lastGuessLessThan()){
                                addMsg += "Wrong, The number is larger than " + guessval;
                            }
                            else {
                                addMsg += "Wrong, The number is less than " + guessval;
                            }
                        }
                        
                    }
                    resp = new HTTPresponse("200", "OK", htmlObj.getHtmlWithMessage(addMsg));
                    write = resp.generateHTTPresponse();
                }
                out.write(write.getBytes());
                out.flush();
                //debug();
                System.out.println("Message: \n" + message);
            }
            
            //System.out.println("Message: \n" + message);
            //System.out.println(response);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    private void debug(){
        PrintDebugger.debug();
    }
}