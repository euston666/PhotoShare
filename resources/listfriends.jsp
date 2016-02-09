<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.FriendDao" %>
<%@ page import="java.util.List" %>

<html>
    <head><title>Friend List</title></head>
    <head><link rel="stylesheet" type="text/css" href="news-magazine/layout/styles/layout.css"></head>

    <body>

        <%
        FriendDao friendDao = new FriendDao();

        int userID = Integer.valueOf(request.getParameter("uid"));
        %>

        <h1>Friends List</h1>

        <br>
        <%
        List<String> friends = friendDao.getFriends(userID);
        if (friends.size() == 0) { %>
            <h2>You don't have any friends yet!</h2>
            <a href="">
            <%
        }
        else {
            for (String friend : friends) { %>
                <b><%= friend %></b><br>
                <%
            }
        }
        %>

        <br><br>
        <b>Return to <a href="index.jsp">Home Page</a>.</b><br>
        <b>Return to <a href="searchfriends.jsp?uid=<%= userID %>">Search for friends</a>.</b>
    </body>
</html>
