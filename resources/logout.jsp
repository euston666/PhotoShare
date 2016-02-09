<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head><title>Logout</title></head>
    <head><link rel="stylesheet" type="text/css" href="news-magazine/layout/styles/layout.css"></head>

    <body>
        <h1>You are logged out.</h1>

        <% session.invalidate(); %>
        <b>Return to <a href="/photoshare/index.jsp">Login Page</a>.</b>
    </body>
</html>
