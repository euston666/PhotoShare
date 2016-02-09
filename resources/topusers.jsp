<%@ page import="photoshare.CommentDao" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.UserDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<html>
    <head><title>Most Active Users</title></head>
    <head><link rel="stylesheet" type="text/css" href="news-magazine/layout/styles/layout.css"></head>

    <body>
        <h1>Most Active Users</h1>

        <br>
        <table border="1">
        <tr>
            <td width="50%" align="center"><b>User</b></td>
            <td width="50%" align="center"><b>Rank</b></td>
        </tr>
        <%
        UserDao userDao = new UserDao();
        List<String> topUsers = userDao.topUsers();
        int i = 1;
        for (String user : topUsers) { %>
            <tr>
                <td width="50%" align="center"> <%= user %> </td>
                <td width="50%" align="center"> <%= i++ %> </td>
            </tr>
            <%
        }
        %>
        </table>

        <br>
        <b>Return to <a href="index.jsp">Home Page</a>.</b>
    </body>
</html>

