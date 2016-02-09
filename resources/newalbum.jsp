<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.AlbumDao" %>
<%@ page import="photoshare.UserDao" %>
<jsp:useBean id="album"
             class="photoshare.Album" />
<jsp:setProperty name="album" property="*"/>

<html>
    <head><title>New Album</title></head>
    <head><link rel="stylesheet" type="text/css" href="news-magazine/layout/styles/layout.css"></head>

    <body>
        <h1>Add A New Album</h1>

        <%
        int userID = Integer.valueOf(request.getParameter("uid"));
        int result = -1;

        if (!album.getAname().equals("")) {
            AlbumDao albumDao = new AlbumDao();
            result = albumDao.create(userID, album.getAname());
        }
        %>

        <br>
        <form action="newalbum.jsp?uid=<%= userID %>" method="post">
            <b>Album Name: </b><input type="text" name="aname"/>
            <input type="submit" value="Create"/>
        </form>

        <%
        if (result == 0) { %>
            <br>
            <b>Database error</b>
            <%
        }

        if (result == 1) { %>
            <br>
            <b>Album '<%= album.getAname() %>' already exists.</b><br>
            <%
        }

        if (result == 2) { %>
            <br>
            <b>Album '<%= album.getAname() %>' is created.</b><br>
            <%
        }
        %>

        <br><br>
        <b>Go to <a href="showpictures.jsp?uid=<%= userID %>">Albums & Photos</a>.</b><br>
        <b>Return to <a href="index.jsp">Home Page</a>.</b>
    </body>
</html>
