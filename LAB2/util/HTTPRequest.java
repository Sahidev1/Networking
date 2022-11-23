package util;

import java.security.cert.CRL;
import java.util.HashMap;

public class HTTPRequest {
    private String requestString;
    private final String CRLF = System.lineSeparator();
    private final String SP = " ";
    private String statusLine;
    private String hostLine;
    private String[] headerFields;
    private boolean reqValid;
    private boolean parameterSyntaxError;
    private boolean noParameters;
    private final String HTTP_VERSION = "HTTP/1.1\r";
    private HashMap<String, String> parameters;

    public HTTPRequest (String requestString){
        this.requestString = requestString;
        parameters = new HashMap<>();
        reqValid = true;
        noParameters = false;
        parameterSyntaxError = false;
        disembowelRequest();
        isRequestValid();
    }

    public String getParameterValue(String paramName){
        if (doesParameterNameExist(paramName)){
            return parameters.get(paramName);
        }
        return "";
    }

    public boolean doesParameterNameExist (String name){
        return parameters.containsKey(name);
    }

    public void generateParameters(){
        if (statusLine.split(SP)[1].equals("/")){
            noParameters = true;
            return;
        }
        String[] params = statusLine.split(SP)[0].split("\\?")[0].split("&");
        String[] param;
        for (String string : params) {
            param = string.split("=");
            if(param.length < 2) {
                parameterSyntaxError = true;
                break;
            }
            parameters.put(param[0], param[1]);
        }
    }
    
    private void disembowelRequest(){
        String[] requestSlices = requestString.split(CRLF);
        statusLine = requestSlices[0];
        hostLine = requestSlices[1];
        headerFields = new String[requestSlices.length - 2];
        for (int i = 0; i < headerFields.length; i++){
            headerFields[i] = requestSlices[i + 2];
        }
    }

    public void isRequestValid(){
        String[] statLine = statusLine.split(SP);
        if ((reqValid = statLine.length < 3)){
            return;
        }
        reqValid = statLine[0].equals("GET");
        reqValid = statLine[1].charAt(0) == '/';
        reqValid = statLine[2].equals(HTTP_VERSION);
    }

    public boolean isParameterSyntaxError() {
        return parameterSyntaxError;
    }

    public boolean isNoParameters() {
        return noParameters;
    }

    public boolean isValid() {
        return reqValid;
    }
}
