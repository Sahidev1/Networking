package classes;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ali
 */
@WebServlet(name = "HTTPHandler", urlPatterns = {"/HTTPHandler"})
public class HTTPHandler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session;
        GuessBean guessBean;
        String guessVal;
        int guessInt = 0;
        PrintWriter out = response.getWriter();
        if (isValidParams(request)){
            guessVal = request.getParameter("guess");
            if (isGuessValNumeral(guessVal)){
                guessInt = Integer.parseInt(guessVal);
            }
            session = request.getSession(false);
            if (session == null){
                session = request.getSession(true);
                guessBean = new GuessBean();
                guessBean.guess(guessInt);
                session.setAttribute("guessbean", guessBean);
                out.println("right num: " + guessBean.getRightNumber());
                out.println(guessBean.getIsLastGuessRight());
                out.flush();
            }
            else {
                guessBean = (GuessBean) session.getAttribute("guessbean");
                guessBean.guess(guessInt);
                session.setMaxInactiveInterval(0);
                out.println("right num: " + guessBean.getRightNumber());
                out.println(guessBean.getIsLastGuessRight());
                out.flush();
            }
        } else {
            out.write("Invalid parameter");
            out.flush();
        }
    }
    
    private boolean isGuessValNumeral (String guessVal){
        byte[] bytes = guessVal.getBytes();
        for (int i = 0; i < bytes.length; i++){
            if (bytes[i] < 48 || bytes[i] > 57) return false;
        }
        return true;
    }
    
    private boolean isValidParams (HttpServletRequest req){
        Map<String,String[]> paraMap = req.getParameterMap();
        return !paraMap.isEmpty() && paraMap.size() == 1 && paraMap.containsKey("guess") && paraMap.get("guess").length <= 1;
    }
 
}
