<%-- 
    Document   : game
    Created on : Dec 12, 2022, 10:02:30 PM
    Author     : ali
--%>

<%@page import="classes.GuessBean" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="guessBean" class="classes.GuessBean"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Guessing game</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <p> Property: 
            <jsp:getProperty name="guessBean" property="nrGuesses"/>
        </p>
        <div>
            <form action="gameServlet" method="POST">
                <label for="guess">Guess the number</label>
                <input type="text" name="guess"> 
                <input type="submit">
            </form>
        </div>
    </body>
</html>
