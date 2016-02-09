<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.TagDao" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="java.util.List" %>

<html>
    <head><title>Photos Of Tag</title></head>
    <head><link rel="stylesheet" type="text/css" href="news-magazine/layout/styles/layout.css"></head>

    <body>
        <%
        int sessionID = Integer.valueOf(request.getParameter("sessionid"));
        String tag = request.getParameter("tag");
        %>

        <h1>Photos Of Tag: <%= tag%></h1>

        <h3><a href="allPicsOfTag.jsp?tag=<%= tag %>&sessionid=<%= sessionID%>">View Public Photos Of This Tag</a></h3>
        <%
        if (sessionID != 0) { %>
            <h3><a href="myPicsOfTag.jsp?tag=<%= tag %>&sessionid=<%= sessionID%>">View My Photos Of This Tag</a></h3>
            <%
        }
        %>

        <%
        if (sessionID != 0) { %>
            <br>
            <b>Return to <a href="showpictures.jsp?uid=<%= sessionID%>">Albums & Photos</a>.</b>
            <%
        }
        else { %>
            <br>
            <b>Return to <a href="login.jsp">Login Page</a>.</b><br>
            <b>Return to <a href="browsephotos.jsp">Browse through some photos</a>.</b><br>
            <b>Return to <a href="searchpublicphotos.jsp?uid=0">Search some photos</a>.</b><br>
            <%
        }
        %>
    </body>
</html>
