<%@ page import="photoshare.PictureDao" %>
<%@ page import="java.util.List" %>
<html>
    <head><title>Delete Photo</title></head>
    <head><link rel="stylesheet" type="text/css" href="news-magazine/layout/styles/layout.css"></head>

    <body>
        <%
        PictureDao pictureDao = new PictureDao();

        int pictureID = Integer.valueOf(request.getParameter("pid"));
        int userID = Integer.valueOf(request.getParameter("uid"));
        boolean success = false;
        success = pictureDao.delete(pictureID);

        if (success) { %>
            <h2>Delete photo successfully.</h2>
            <b>Return to <a href="showpictures.jsp?uid=<%= userID %>">Albums & Photos</a>.</b>
            <%
        }
        else { %>
            <h2>Delete photo failed.</h2>
            <b>Return to <a href="showpictures.jsp?uid=<%= userID %>">Albums & Photos</a>.</b>
            <%
        }
        %>
    </body>
</html>
