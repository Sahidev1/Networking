<%-- 
    Document   : quiz.jsp
    Created on : Dec 14, 2022, 7:59:12 PM
    Author     : ali
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="handler" class="classes.QuizHandlerBean" scope="session"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz</title>
    </head>
    <body>
        <h1>Welcome to the quiz page!</h1>
        <c:set var="reqpick" value="${handler.reqValid}"/>
        <c:if test="${!reqpick}">
            Pick a Quiz to answer:
            <form method="POST" action="/quiz/QuizServlet">
                <select name="pickquizid" id="options">
                    <c:forEach items="${handler.getQuizMap().values()}" var="cquiz">
                        <option value="${cquiz.getQuiz_id()}"> ${cquiz.getQuizName()}</option>
                    </c:forEach>
                </select>
                <p>
                    <input type="submit" value="Submit"/>
                </p>
            </form>
        </c:if>
        <c:if test="${reqpick}">
            <p> Former score: ${handler.getQuizMap().get(handler.getRequestQuizID()).getLastQuizPoints()} </p>
            <form method="POST" action="/quiz/QuizServlet">
                <c:forEach items="${handler.getQuizMap().get(handler.getRequestQuizID()).getQuestions()}" var="question">
                    <p> Question: ${question.getQtext()} </p>
                        <c:forEach items="${question.getKeyArr()}" var="options">
                            <p><input type="checkbox" id="${question.getQid()}" name="${question.getQid()}" value="${options}"/> ${options} </p>
                        </c:forEach>
                </c:forEach>
                            <input type="submit" value="Submit"/>
            </form>           
        </c:if>
    </body>
</html>
