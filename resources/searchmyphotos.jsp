<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.TagDao" %>
<%@ page import="java.util.List" %>

<html>
    <head><title>Search My Photos</title></head>
    <head><link rel="stylesheet" type="text/css" href="news-magazine/layout/styles/layout.css"></head>

    <body>
        <%
        TagDao tagDao = new TagDao();
        boolean showSearch = true;

        int sessionID = Integer.valueOf(request.getParameter("uid"));
        %>

        <h1>Search My Photos</h1>

        <form action="myPicsOfTag.jsp" method="post">
            <b>Tag:</b> <input type="text" name="tag">
            <input type="hidden" name="sessionid" value="<%= sessionID %>">
            <input type="submit" value="Search">
        </form>

        <br>
        <b>Return to <a href="showpictures.jsp?uid=<%= sessionID%>">Albums & Photos</a>.</b>
    </body>
</html>

