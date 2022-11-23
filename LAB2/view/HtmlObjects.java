package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HtmlObjects {
    private static HtmlObjects singleton = new HtmlObjects();
    private String htmlString;
    private static final String MSG_LINE = "<div>";

    private HtmlObjects (){
        StringBuilder sb = new StringBuilder();
        try {
            File htmlFile = new File("view/index.html");
            Scanner reader = new Scanner(htmlFile);  
            while (reader.hasNextLine()){
                sb.append(reader.nextLine());
            }
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

    public static HtmlObjects getHtmlObjects(){
        return singleton;
    }

    public String getHtmlString() {
        return htmlString;
    }  
} 