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
    private HashMap<String, GuessingGame> gameInstances;
    private long threadID;
    private InputStream in;
    private OutputStream out;
    private final int READ_BUFFER_SIZE = 1024;
    private byte[] read_buffer;
    private GuessingGame game;
    private boolean isContinuedSession;

    public HTTPHandler(Socket socket, HashMap<String, GuessingGame> gameInstances){
        this.socket = socket;
        this.gameInstances = gameInstances;
        isContinuedSession = false;
        threadID = Thread.currentThread().getId();
    }

    public void run(){
        System.out.println("thread activated");
        read_buffer = new byte[READ_BUFFER_SIZE];

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
                handleGame(req);
                /* 
                if(req.isValid()){
                    req.generateParameters(); 
                }
                if(req.isValid() && req.isNoParameters() && !isContinuedSession){
                    resp = new HTTPresponse("200", "OK", htmlObj.getHtmlWithMessage("Guess the number between 0 and 100!"), game);
                    isContinuedSession = true;
                    write = resp.generateHTTPresponse();
                }
                if(req.isValid() && isContinuedSession){
                    String addMsg = "";
                    PrintDebugger.debug("boop");
                    if (req.doesParameterNameExist("guess")){
                        int guessval = Integer.parseInt(req.getParameterValue("guess"));
                        if(game.guess(guessval)){
                            addMsg += "The number " + guessval + " is correct!";
                            //game.initGame();// restart game
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
                } */
                
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

    private synchronized void handleGame (HTTPRequest req){
        if (req.doesContainCookie() && gameInstances.containsKey(req.getCookie())){
            this.game = gameInstances.get(req.getCookie());
            isContinuedSession = true;
        }
        else {
            this.game = new GuessingGame(threadID);
            gameInstances.put(game.getCookie(), game);
        }
    }

    private void debug(){
        PrintDebugger.debug();
    }
}