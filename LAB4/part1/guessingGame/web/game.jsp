<%-- 
    Document   : game
    Created on : Dec 12, 2022, 10:02:30 PM
    Author     : ali
--%>

<jsp:useBean id="guessbean" class="classes.GuessBean" scope="session"/>
<%@page import="classes.GuessBean" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Guessing game</title>
    </head>
    <body>
        <h1>Guessing game</h1>
        <p> Nr of guesses: 
            <%=guessbean.getNrGuesses() %>
        </p>
        <p>
            <%
                String msg = "";
                if(guessbean.getNrGuesses() != 0){
                    msg = "last guess: " + guessbean.getLastGuess();
                }
                out.print(msg);
            %>
        </p>
        <p>
            <%=guessbean.generateLastGuessMsg()%>
        </p>
        <p>
            <%
                if (guessbean.getIsLastGuessRight()){
                    out.print ("New game started, guess the new number!");
                }
            %>
        </p>
        <div>
            <form method="GET" action="/guessingGame/gameServlet">
                <input type="text" name="guess"/>
                <input type="submit" value="submit"/>
            </form>
        </div>
    </body>
</html>
