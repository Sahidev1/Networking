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
import java.util.StringTokenizer;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean hasAttr;
        response.setContentType("text/html;charset=UTF-8");
        Enumeration<String> en = request.getAttributeNames();
        hasAttr = hasAttributes(en);
        HashSet attrSet = getAttributeSet(en);
        String method = request.getMethod();
        HttpSession session;
        RequestDispatcher dispatch;
        PrintWriter writer = response.getWriter();
        if (!hasAttr && isValidMethod(method)){
            session = request.getSession(false);
            if (session == null){
                session = request.getSession();
                GuessBean guess = new GuessBean();
                session.setAttribute("game", guess);
                dispatch = request.getRequestDispatcher("/game.jsp");
                dispatch.forward(request, response);
            }
        }
        if (hasAttr && isValidAttributeSet(attrSet) && isValidMethod (method)){
            response.setStatus(200);
            session = request.getSession(false);
            if (session == null){
                session = request.getSession();
                GuessBean guess = new GuessBean();
                session.setAttribute("game", guess);
                dispatch = request.getRequestDispatcher("/game.jsp");
                dispatch.forward(request, response);
            }
            else {
                session.getAttribute("game");
            }
        }
        
    }
    
    private boolean isValidMethod (String method){
        return method == "GET" || method == "POST";
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    private boolean isValidAttributeSet (HashSet set){
        return set.contains("guess") && set.size() == 1;
    }
    
    private HashSet getAttributeSet (Enumeration<String> strEnum){
        if (hasAttributes(strEnum)){
            return null;
        }
        HashSet<String> set = new HashSet<>();
        while (hasAttributes(strEnum)){
            set.add(strEnum.nextElement());
        }
        return set;
    }
    
    private boolean hasAttributes (Enumeration<String> strEnum){
        return strEnum.hasMoreElements();
    }
}
