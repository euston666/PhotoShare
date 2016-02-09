<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head><title>Photoshare Login</title></head>
    <head><link rel="stylesheet" type="text/css" href="news-magazine/layout/styles/layout.css"></head>

    <body>
        <h1>Welcome to PhotoShare</h1>

        <br>
        <form method="POST" action="j_security_check">
            <table >
                <tr><td align="right"><b>Email:</b></td><td><input type="text" name="j_username"></td></tr>
                <tr><td align="right"><b>Password:</b></td><td><input type="password" name="j_password"></td></tr>
                <tr><td align="right"></td><td align="left"><input type="submit" value="Login"/></td></tr>
            </table>
        </form>

        <h2>First time here? <a href="newuser.jsp">Register now</a>.</h2>
        <h2><a href="browsephotos.jsp">Browse through some photos</a>.</h2>
        <h2><a href="searchpublicphotos.jsp?uid=0">Search some photos</a>.</h2>
    </body>
</html>
