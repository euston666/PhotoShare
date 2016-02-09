<%@ page import="photoshare.TagDao" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head><title>Add Tags</title></head>
    <head><link rel="stylesheet" type="text/css" href="news-magazine/layout/styles/layout.css"></head>

    <body>
        <head><title>Add Tags</title></head>

            <%
            TagDao tagDao = new TagDao();
            boolean showForm = true;
            int sessionID = Integer.valueOf(request.getParameter("sessionid"));
            int pictureID = Integer.valueOf(request.getParameter("id"));
            String words = request.getParameter("words");
            String[] tags = null;
            if (words != null) tags = words.split("\\s*,\\s*");

            boolean success = false;
            if (words != null) {
                for (String tag: tags) {
                    if (tagDao.createTag(tag, pictureID)) success = true;
                }
                showForm = false;
            }
            %>

            <h1>Add Tags</h1>

            <%
            if (showForm) { %>
                <b>(Separate tags with commas)</b>

                <br><br><br>
                <form action="addtags.jsp?id=<%= pictureID %>&sessionid=<%= sessionID %>" method="post">
                    <input type="text" name="words">
                    <input type="submit" value="Add Tags">
                </form>
                <%
            }
            else if (success) { %>
                <br>
                <b>Add tags successfully.</b>
                <%
            }
            else { %>
                <br>
                <b>Add tags failed. Tags already added.</b>
                <%
            }
            %>
            <br><br><br>
            <b>Return to <a href="picture.jsp?id=<%= pictureID %>&sessionid=<%= sessionID %>">the picture</a>.</b>
    </body>
</html>
