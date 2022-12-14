<%-- 
    Document   : login
    Created on : Dec 14, 2022, 6:05:24 PM
    Author     : ali
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Welcome to login page!</h1>
        <div>
            <form method="POST" action="/quiz/LoginServlet">
                <p>Username: <input type="text" name="username"/> </p>
                <p>Password: <input type="password" name="password"/> </p>
                <p> <input type="submit" value="Login"/> </p>
            </form>
        </div>
    </body>
</html>
