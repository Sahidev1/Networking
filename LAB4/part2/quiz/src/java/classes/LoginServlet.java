/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author ali
 */
public class LoginServlet extends HttpServlet {
    
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);
        UserBean user;
        RequestDispatcher dispatch;
        if (session.isNew()){
            //New session, user is not logged in
            user = new UserBean();
            session.setAttribute("user", user);
        }
        else {
            user = (UserBean) session.getAttribute("user");
            if (user.isIsLoggedIn()){
                //forward to router servlet
                dispatch = request.getRequestDispatcher("Router");
                dispatch.forward(request, response);
            }
            else {
                dispatch = request.getRequestDispatcher("WEB-INF/login.jsp");
                dispatch.forward(request, response);//forward to login page
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
        RequestDispatcher dispatch;
        UserBean user;
        if (session.isNew()){
            user = new UserBean();
            session.setAttribute("user", user);
            dispatch = request.getRequestDispatcher("Router");
            dispatch.forward(request, response);
            return;
        }

        user = (UserBean) session.getAttribute("user");
        String dbPassword = null;
        String reqUsername = null;
        String reqPassword = null;
        int id = -1;


        if (isValidParams (request)){
            reqUsername = request.getParameter("username");
            reqPassword = request.getParameter("password");

            try {
                Context initContext = new InitialContext();
                Context envContext = (Context) initContext.lookup("java:/comp/env");
                DataSource ds = (DataSource) envContext.lookup("jdbc/derby");
                Connection conn = ds.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(genLoginQuery(reqUsername));
                if (rs.next()){

                    dbPassword = rs.getString("password");
                }
                if (dbPassword != null){
                    rs = stmt.executeQuery(genGetIDQuery(reqUsername));
                    if (rs.next()){
   
                        id = rs.getInt("ID");
                    }
                }
                conn.close();
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        if (dbPassword != null && dbPassword.equals(reqPassword)){
            user.setUsername(reqUsername);
            user.setUser_id(id);
            user.setIsLoggedIn(true);
            dispatch = request.getRequestDispatcher("QuizServlet");
            dispatch.forward(request, response);
        }
        else {
            dispatch = request.getRequestDispatcher("WEB-INF/login.jsp");
            dispatch.forward(request, response);//for
        }
    }
    
    private String genLoginQuery (String username){
        return "SELECT password FROM users WHERE username='" + username + "'";
    }
    
    private String genGetIDQuery (String username){
        return "SELECT ID FROM users WHERE username='" + username + "'";
    }
    
    private boolean isValidParams (HttpServletRequest req){
        Map<String, String[]> paramTable = req.getParameterMap();
        if (paramTable.size() != 2) return false;
        if (!paramTable.containsKey("username") || !paramTable.containsKey("password")) return false;
        if (paramTable.get("username").length != 1 || paramTable.get("password").length != 1) return false;
        if (paramTable.get("username")[0].length() <= 0 || paramTable.get("password")[0].length() <= 0) return false;
        return true;
    }

}
