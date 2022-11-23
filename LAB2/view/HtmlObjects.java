package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HtmlObjects {
    private static HtmlObjects singleton = new HtmlObjects();
    private String htmlString;

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

    public static HtmlObjects getHtmlObjects(){
        return singleton;
    }

    public String getHtmlString() {
        return htmlString;
    }  
} 