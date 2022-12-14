/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ali
 */
public class QuizServlet extends HttpServlet {
    
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        //out.flush();
        HttpSession session = request.getSession(true);
        User user;
        RequestDispatcher dispatch;

        if (session.isNew()){
            //New session, user is not logged in

            user = new User();
            session.setAttribute("user", user);
            //dispatch to login servlet
            dispatch = request.getRequestDispatcher("LoginServlet");
            dispatch.forward(request, response);
        }
        else {
            user = (User) session.getAttribute("user");

            if (!user.isIsLoggedIn()) {
                
                //forward to login servlet
                dispatch = request.getRequestDispatcher("LoginServlet");
                dispatch.forward(request, response);
            }
            else {

            }
        }
    }
    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        //out.flush();
        HttpSession session = request.getSession(true);
        User user;
        RequestDispatcher dispatch;

        if (session.isNew()){
            //New session, user is not logged in
    
            user = new User();
            session.setAttribute("user", user);
            //dispatch to login servlet
            dispatch = request.getRequestDispatcher("LoginServlet");
            dispatch.forward(request, response);
        }
        else {
            user = (User) session.getAttribute("user");

            if (!user.isIsLoggedIn()) {
                
                //forward to login servlet
                dispatch = request.getRequestDispatcher("LoginServlet");
                dispatch.forward(request, response);
            }
            else {
      
            }
        }
    }
}
