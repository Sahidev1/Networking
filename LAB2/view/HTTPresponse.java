package view;

import model.GuessingGame;

public class HTTPresponse {
    private static final String CRLF = System.lineSeparator();
    private final String SP = " ";
    private final String HTTP_VERSION = "HTTP/1.1";
    private final String CONN_CLOSE = "Connection: close";
    private String response_status_code;
    private String reason_phrase;
    private String body;
    private final String CONTENT_TYPE = "Content-Type: text/html";
    private final String CONTENT_LENGTH_HFIELD = "Content-Length:";
    private final String SET_COOKIE_HFIELD = "Set-Cookie:";
    private String cookie;
    private GuessingGame game;

    public HTTPresponse (String stat_code, String RF, String body, GuessingGame game){
        this.response_status_code = stat_code;
        this.reason_phrase = RF;
        this.body = body;
        this.game = game;
        this.cookie = game.getCookie();
    }    

    public HTTPresponse (String stat_code, String RF, String body){
        this.response_status_code = stat_code;
        this.reason_phrase = RF;
        this.body = body;
        this.cookie = null;
    }

    public HTTPresponse (String stat_code, String RF){
        this.response_status_code = stat_code;
        this.reason_phrase = RF;
        this.cookie = null;
    }

    public String generateHTTPresponse (){
        int expirationTime = 60*60;
        int contentLen = body == null? 0 : body.getBytes().length;
        StringBuilder response = new StringBuilder();
        response.append(HTTP_VERSION + SP + 
            response_status_code + SP + reason_phrase + SP + CRLF);
        response.append(CONTENT_TYPE + CRLF);
        response.append(CONTENT_LENGTH_HFIELD + SP + contentLen + CRLF);
        if (!game.isCookieSet()){
            response.append(SET_COOKIE_HFIELD + SP + cookie + ";max-age=" + expirationTime + ";"+ CRLF);
            game.setCookieSet(true);
        }
        response.append(CRLF);
        if (body != null){
            response.append(body);
        }
        return response.toString();
    }

    public void setBody(String body) {
        this.body = body;
    }
}
