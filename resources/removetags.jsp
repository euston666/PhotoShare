<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.TagDao" %>
<%@ page import="java.util.List" %>
<html>
    <head><title>Delete Tags</title></head>
    <head><link rel="stylesheet" type="text/css" href="news-magazine/layout/styles/layout.css"></head>

    <body>
        <%
        PictureDao pictureDao = new PictureDao();
        TagDao tagDao = new TagDao();

        int sessionID = Integer.valueOf(request.getParameter("sessionid"));
        int pictureID = Integer.valueOf(request.getParameter("pid"));
        String words = request.getParameter("words");
        String[] tags = null;
        if (words != null) {
            tags = words.split("\\s*,\\s*");
            for (String tag: tags) {
                tagDao.removeTag(tag, pictureID);
            }
        }
        %>

        <h1>Remove Tags</h1>

        <b>(Separate tags with commas)</b>

        <br><br><br>
        <form action="removetags.jsp?pid=<%= pictureID %>&sessionid=<%= sessionID %>" method="post">
            <input type="text" name="words">
            <input type="submit" value="Remove Tags">
        </form>

        <%
        if (words != null) { %>
            <br>
            <b>Tags removed.</b>
            <%
        }
        %>
        <br><br><br>
        <b>Return to <a href="picture.jsp?id=<%= pictureID %>&sessionid=<%= sessionID %>">the picture</a>.</b>
    </body>
</html>
