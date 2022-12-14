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
public class Router extends HttpServlet {

    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);
        User user;
        RequestDispatcher dispatch;
        String dispatchPath;
        if (session.isNew()){
            //New session, user is not logged in
            user = new User();
            session.setAttribute("user", user);
            dispatchPath = "LoginServlet";
            //dispatch to login servlet
        }
        else {
            user = (User) session.getAttribute("user");
            if (user.isIsLoggedIn()){
                //forward to db servlet
                dispatchPath = "QuizServlet";
            }
            else {
                //forward to login servlet
                dispatchPath = "LoginServlet";
            }
        }
        dispatch = request.getRequestDispatcher(dispatchPath);
        dispatch.forward(request, response);
    }
}
