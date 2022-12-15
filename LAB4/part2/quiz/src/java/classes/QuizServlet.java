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
import java.util.List;
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
public class QuizServlet extends HttpServlet {
    
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        //out.flush();
        HttpSession session = request.getSession(true);
        UserBean user;
        RequestDispatcher dispatch;
        QuizHandlerBean handler;

        if (session.isNew()){
            //New session, user is not logged in

            user = new UserBean();
            session.setAttribute("user", user);
            //dispatch to login servlet
            dispatch = request.getRequestDispatcher("LoginServlet");
            dispatch.forward(request, response);
        }
        else {
            user = (UserBean) session.getAttribute("user");

            if (!user.isIsLoggedIn()) {
                
                //forward to login servlet
                dispatch = request.getRequestDispatcher("LoginServlet");
                dispatch.forward(request, response);
            }
            else {
                Object attr = session.getAttribute("handler");
                if (attr == null){
                    handler = new QuizHandlerBean();
                    initQuizHandler (handler, user, out);
                    session.setAttribute("handler", handler);
                }
                else {
                    handler = (QuizHandlerBean) attr;
                }
                
                if (request.getParameter("pickquizid") != null){
                    String paramVal = request.getParameter("pickquizid");
                    handler.setRequestQuizID(Integer.parseInt(paramVal));
                }
                
                if (handler.isReqValid()){
                    int reqQuizID = handler.getRequestQuizID();
                }
                
                dispatch = request.getRequestDispatcher("WEB-INF/quiz.jsp");
                dispatch.forward(request, response);
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
        UserBean user;
        RequestDispatcher dispatch;
        QuizHandlerBean handler;

        if (session.isNew()){
            //New session, user is not logged in
    
            user = new UserBean();
            session.setAttribute("user", user);
            //dispatch to login servlet
            dispatch = request.getRequestDispatcher("LoginServlet");
            dispatch.forward(request, response);
        }
        else {
            user = (UserBean) session.getAttribute("user");

            if (!user.isIsLoggedIn()) {
                
                //forward to login servlet
                dispatch = request.getRequestDispatcher("LoginServlet");
                dispatch.forward(request, response);
            }
            else {
                Object attr = session.getAttribute("handler");
                if (attr == null){
                    handler = new QuizHandlerBean();
                    initQuizHandler (handler, user, out);
                    session.setAttribute("handler", handler);
                }
                else {
                    handler = (QuizHandlerBean) attr;
                }
                
                if (request.getParameter("pickquizid") != null){
                    String paramVal = request.getParameter("pickquizid");
                    handler.setRequestQuizID(Integer.parseInt(paramVal));
                }
                
                dispatch = request.getRequestDispatcher("WEB-INF/quiz.jsp");
                dispatch.forward(request, response);
            }
        }
    }
    
    private void initQuizHandler(QuizHandlerBean handler, UserBean user, PrintWriter out){
        Map<Integer, Quiz> qMap = handler.getQuizMap();
        try {
            int userid = user.getUser_id();
            Quiz q;
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/derby");
            Connection conn = ds.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM quizzes");
            while(rs.next()){
                q = new Quiz();
                q.setQuiz_id(rs.getInt("id"));
                q.setUser_id(userid);
                q.setQuizName(rs.getString("subject"));
                qMap.put(q.getQuiz_id(), q);
            }
            for (Quiz quiz: qMap.values()){
                rs = stmt.executeQuery(genGetScoreStmt(quiz.getQuiz_id(), quiz.getUser_id()));
                while (rs.next()){
                    quiz.setLastQuizPoints(rs.getInt("score"));
                }
                
                rs = stmt.executeQuery(genGetQuestionsStmt(quiz.getQuiz_id()));
                while (rs.next()){
                    quiz.addQuestion(rs.getInt("id"), rs.getString("text"), rs.getString("options"), rs.getString("answer"));
                }
            }
            out.println("henlo :)");
        } catch (Exception e){
            out.println(e.getMessage());
        }
        
    }
    
    private String genGetQuestionsStmt (int quizId){
        return "SELECT questions.* FROM selector, questions WHERE selector.quiz_id=" + quizId + " AND selector.question_id = questions.id";
    }
    
    private String genGetScoreStmt (int quizID, int userID){
        return "SELECT score FROM results WHERE " + userID + "= 1 " + "AND quiz_id=" + quizID;
    }
}

