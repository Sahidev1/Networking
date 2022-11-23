package view;

public class HTTPresponse {
    private static final String CRLF = System.lineSeparator();
    private final String SP = " ";
    private final String HTTP_VERSION = "HTTP/1.1";
    private final String CONN_CLOSE = "Connection: close";
    private String response_status_code;
    private String reason_phrase;
    private String body;
    private final String CONTENT_TYPE = "Content-Type: text/html";
    private static String CONTENT_LENGTH_HFIELD = "Content-Length:";

    public HTTPresponse (String stat_code, String RF, String body){
        this.response_status_code = stat_code;
        this.reason_phrase = RF;
        this.body = body;
    }

    public HTTPresponse (String stat_code, String RF){
        this.response_status_code = stat_code;
        this.reason_phrase = RF;
    }

    public String generateHTTPresponse (){
        int contentLen = body == null? 0 : body.getBytes().length;
        StringBuilder response = new StringBuilder();
        response.append(HTTP_VERSION + SP + 
            response_status_code + SP + reason_phrase + SP + CRLF);
        response.append(CONTENT_TYPE + CRLF);
        response.append(CONTENT_LENGTH_HFIELD + SP + contentLen + CRLF);
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
