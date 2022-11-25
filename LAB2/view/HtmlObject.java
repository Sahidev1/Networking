package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HtmlObject {
    private static HtmlObject singleton = new HtmlObject();
    private String htmlString;
    private static final String MSG_LINE = "</div>";

    private HtmlObject (){
        StringBuilder sb = new StringBuilder();
        try {
            File htmlFile = new File("view/index.html");
            Scanner reader = new Scanner(htmlFile);  
            while (reader.hasNextLine()){
                sb.append(reader.nextLine());
            }
            reader.close();;
        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        }
        htmlString = sb.toString();
    }

    public String getHtmlWithMessage (String msg){
        StringBuilder sb = new StringBuilder(htmlString);
        sb.insert(sb.indexOf(MSG_LINE), msg);
        return sb.toString();
    }

    public static HtmlObject getHtmlObject(){
        return singleton;
    }

    public String getHtmlString() {
        return htmlString;
    }  
} 