<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.TagDao" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<html>
    <head><title>Recommend Photos</title></head>
    <head><link rel="stylesheet" type="text/css" href="news-magazine/layout/styles/layout.css"></head>

    <body>

        <h1>Photos You May Like</h1>

        <table>
        <tr>
        <%
        TagDao tagDao = new TagDao();
        PictureDao pictureDao = new PictureDao();

        int userID = Integer.valueOf(request.getParameter("uid"));
        List<Integer> mypictureIDs = pictureDao.getUserPics(userID);
        List<String> mytopTags = tagDao.myTopTags(userID);
        List<Integer> pictureIDs = tagDao.disjunctiveQuery(mytopTags);

        for (Integer pictureID : pictureIDs) {
            if (!mypictureIDs.contains(pictureID)) { %>
                <td>
                    <a href="/photoshare/picture.jsp?id=<%= pictureID %>&sessionid=<%= userID %>">
                    <img src="/photoshare/img?t=1&pid=<%= pictureID %>"/>
                    </a>
                </td>
                <%
            }
        }
        %>
        </tr>
        </table>

        <br>
        <b>Return to <a href="showpictures.jsp?uid=<%= userID %>">Albums & Photos</a>.</b><br>
        <b>Return to <a href="index.jsp">Home Page</a>.</b>
    </body>
</html>
