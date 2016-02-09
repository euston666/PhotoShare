<%@ page import="photoshare.TagDao" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head><title>Recommend Tags</title></head>
    <head><link rel="stylesheet" type="text/css" href="news-magazine/layout/styles/layout.css"></head>

    <body>
        <h1>Recommend Tags</h1>

        <%
        TagDao tagDao = new TagDao();

        boolean showForm = true;
        int userID = Integer.valueOf(request.getParameter("uid"));

        String words = request.getParameter("words");
        String[] tags = null;
        if (words != null) { tags = words.split("\\s*,\\s*"); }

        List<String> searchTags = new ArrayList<String>();
        List<String> recommends = new ArrayList<String>();
        if (tags != null) {
            for (String tag : tags) { searchTags.add(tag); }
            recommends = tagDao.recommendTags(tags);
        }
        %>

        <b>(Separate tags with commas)</b>
        <br><br><br>
        <form action="recommendtags.jsp?uid=<%= userID %>" method="post">
            <input type="text" name="words">
            <input type="submit" value="Recommend">
        </form>

        <br><br>
        <%
        if (recommends.size() > 0) { %>
            <br>
            <table>
            <tr>
            <%
            for (String tag: recommends) {
                if (!searchTags.contains(tag)) { %>
                    <td>
                    <a href="picsOfTag.jsp?tag=<%= tag %>&sessionid=<%= userID %>"><%= tag %></a>
                    </td>
                    <%
                }
            }
            %>
        </tr>
        </table>
        <%
        }
        else if (words != null) { %>
            <br>
            <b>Sorry, no tags to recommend.</b>
            <%
        }
        %>

        <br><br>
        <b>Return to <a href="index.jsp">Home Page</a>.</b>
    </body>
</html>
