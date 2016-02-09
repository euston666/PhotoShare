<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.UserDao" %>
<%@ page import="photoshare.FriendDao" %>


<html>
    <head><title>Search Friends</title></head>
    <head><link rel="stylesheet" type="text/css" href="news-magazine/layout/styles/layout.css"></head>

    <body>
        <%
        UserDao userDao = new UserDao();
        FriendDao friendDao = new FriendDao();

        int userID = Integer.valueOf(request.getParameter("uid"));
        String userEmail = userDao.getUserName(userID);
        String email = request.getParameter("email");
        String action = "";
        action = request.getParameter("action");

        int frienda = -1;
        String friendA = request.getParameter("frienda");
        if (friendA != null) frienda = Integer.valueOf(friendA);

        int friendb = -1;
        String friendName = "";
        String friendB = request.getParameter("friendb");
        if (friendB != null) {
            friendb = Integer.valueOf(friendB);
            friendName = userDao.getUserName(friendb);
        }

        String searchResult = "";
        int friendship = -1;

        if (action != null && action.equals("Search")) {
            searchResult = userDao.search(email);
        }
        if (action != null && action.equals("Add Friend")) {
            friendship = friendDao.createFriendship(frienda, friendb);
        }
        %>

        <h1>Search For Friends</h1>

        <br>
        <form action="searchfriends.jsp?uid=<%= userID %>" method="post">
            <b>User Email: </b><input type="text" name="email"/>
            <input type="submit" name="action" value="Search"/>
        </form>

        <%
        if (action != null && action.equals("Search")) {
            int userToAdd = userDao.getUserID(email);
            if (userEmail.equals(email)) { searchResult = "NULL"; }
            %><br><b><%= searchResult %></b><%
            if (!searchResult.equals("NULL")) { %>
                <br><br>
                <form action="searchfriends.jsp?uid=<%= userID %>" method="post">
                    <input type="hidden" name="frienda" value=<%= userID %>>
                    <input type="hidden" name="friendb" value=<%= userToAdd %>>
                    <input type="submit" name="action" value="Add Friend">
                </form>
                <%
            }
        }

        if (action != null && action.equals("Add Friend")){
            if (friendship == 0) { %>
                <br><b><%= userDao.search(friendName) %> is already your friend.</b>
                <%
            }
            if (friendship == 1) { %>
                <br><b>Add friend '<%= userDao.search(friendName) %>' successfully.</b>
                <%
            }
        }
        %>
        <br><br><br>
        <b>Return to <a href="index.jsp">Home Page</a>.</b>
    </body>
</html>
