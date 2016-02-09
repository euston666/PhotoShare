<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.TagDao" %>
<%@ page import="java.util.List" %>

<html>
    <head><title>Most Popular Tags</title></head>
    <head><link rel="stylesheet" type="text/css" href="news-magazine/layout/styles/layout.css"></head>

    <body>

        <%
        TagDao tagDao = new TagDao();

        int userID = Integer.valueOf(request.getParameter("uid"));
        %>

        <h1>Most Popular Tags</h1>

        <br>
        <table border="1">
        <tr>
            <td width="50%" align="center"><b>Tag</b></td>
            <td width="50%" align="center"><b>Rank</b></td>
        </tr>
        <%
        List<String> tags = tagDao.topTags();
        int i = 0;
        for (String tag : tags) {
            i++;
            %>
            <tr>
                <td width="50%" align="center"><a href="picsOfTag.jsp?tag=<%= tag %>&sessionid=<%= userID %>"><%= tag %></a><br></td>
                <td width="50%" align="center"><%= i %></td>
            </tr>
            <%
        }
        %>
        </table>

        <br>
        <b>Return to <a href="index.jsp">Home Page</a>.</b>
    </body>
</html>
