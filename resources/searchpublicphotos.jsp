<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.TagDao" %>
<%@ page import="java.util.List" %>

<html>
    <head><title>Search Public Photos</title></head>
    <head><link rel="stylesheet" type="text/css" href="news-magazine/layout/styles/layout.css"></head>

    <body>
        <%
        TagDao tagDao = new TagDao();

        boolean showSearch = true;
        int sessionID = Integer.valueOf(request.getParameter("uid"));
        %>

        <h1>Search Public Photos</h1>
        <b>(Input a tag and show photos of it)</b>

        <br><br><br><br>
        <form action="allPicsOfTag.jsp" method="post">
            <b>Tag:</b> <input type="text" name="tag">
            <input type="hidden" name="sessionid" value="<%= sessionID %>">
            <input type="submit" value="Search">
        </form>

        <%
        if (sessionID != 0) { %>
            <br><br><br>
            <b>Return to <a href="showpictures.jsp?uid=<%= sessionID%>">Albums & Photos</a>.</b>
            <%
        }
        else { %>
            <br><br><br>
            <b>Return to <a href="login.jsp">Login Page</a>.</b><br>
            <b>Return to <a href="browsephotos.jsp">Browse through some photos</a>.</b>
            <%
        }
        %>
    </body>
</html>
