<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.TagDao" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="java.util.List" %>

<html>
    <head><title>My Pictures By Tag</title></head>
    <head><link rel="stylesheet" type="text/css" href="news-magazine/layout/styles/layout.css"></head>

    <body>
        <%
        TagDao tagDao = new TagDao();
        PictureDao pictureDao = new PictureDao();

        int sessionID = Integer.valueOf(request.getParameter("sessionid"));
        String tag = request.getParameter("tag");
        List<Integer> pictureIDs = tagDao.getMyPicsByTag(sessionID, tag);
        %>

        <h1>Your pictures with tag: <%= tag %></h1>

        <br><br>
        <table>
        <tr><%
            for (Integer pictureID : pictureIDs) { %>
                <td><a href="/photoshare/picture.jsp?id=<%= pictureID %>&sessionid=<%= sessionID %>">
                    <img src="/photoshare/img?t=1&pid=<%= pictureID %>"/></a>
                </td>
                <%
            }
            %>
        </tr>
        </table>

        <%
        if (sessionID != 0) { %>
            <br><br>
            <b>Return to <a href="showpictures.jsp?uid=<%= sessionID %>">Albums & Photos</a>.</b><br>
            <b>Return to <a href="index.jsp">Home Page</a>.</b>
            <%
        }
        else { %>
            <br><br>
            <b>Return to <a href="login.jsp">Login Page</a>.</b><br>
            <b>Return to <a href="searchpublicphotos.jsp?uid=0">Search some photos</a>.</b><br>
            <b>Return to <a href="browsephotos.jsp">Browse through some photos</a>.</b>
            <%
        }
        %>
    </body>
</html>
