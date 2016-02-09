<%@ page import="photoshare.CommentDao" %>
<%@ page import="photoshare.LikeDao" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.AlbumDao" %>
<%@ page import="photoshare.TagDao" %>
<%@ page import="org.apache.commons.fileupload.FileUploadException" %>
<%@ page import="java.util.List" %>
<html>
    <head><title>Photo</title></head>
    <head><link rel="stylesheet" type="text/css" href="news-magazine/layout/styles/layout.css"></head>

    <body>
        <%
        PictureDao pictureDao = new PictureDao();
        AlbumDao albumDao = new AlbumDao();
        TagDao tagDao = new TagDao();
        LikeDao likeDao = new LikeDao();
        CommentDao commentDao = new CommentDao();

        int sessionID = Integer.valueOf(request.getParameter("sessionid"));
        int pictureID = Integer.valueOf(request.getParameter("id"));
        int albumID = pictureDao.getAlbumID(pictureID);
        int userID = albumDao.getOwnerID(albumID);

        String action = request.getParameter("action");
        String comment = request.getParameter("comment");

        String albumName = albumDao.getAlbumName(albumID);
        String caption = pictureDao.getCaption(pictureID);
        List<String> tags = tagDao.getPictureTags(pictureID);
        %>

        <img src="/photoshare/img?pid=<%= pictureID %>"/>
        <br><br>

        <b>Album: </b><%= albumName %><br>
        <b>Caption: </b><% if (caption.equals("")) { %>Null<% } else { %><%= caption %><%} %><br>
        <b>Tags: </b><br>
        <table>
        <tr><%
            if (tags.size() == 0) %>Null<%
            else {
                for (String word: tags) { %>
                    <td><a href="picsOfTag.jsp?tag=<%= word %>&sessionid=<%= sessionID %>"><%= word %></a></td>
                    <%
                }
            }
            %>
        </tr>
        </table>

        <br>
        <%
        if (action != null && action.equals("LIKE")) {
            likeDao.createLike(sessionID, pictureID);
        }

        boolean isLike = likeDao.checkLike(sessionID, pictureID);
        if (isLike) { %>
            <b>You liked this photo</b><br>
            <%
        }
        else {
            if (sessionID != userID && sessionID != 0) { %>
                <form action="picture.jsp?id=<%= pictureID %>&sessionid=<%= sessionID %>" method ="post">
                    <input type="submit" name="action" value="LIKE">
                </form>
                <%
            }
        }
        %>

        <br><br>
        <h3><%= likeDao.numLiked(pictureID) %> people liked this photo</h3>
        <%
        List<String> users = likeDao.whoLiked(pictureID);

        for (String user: users) { %>
            <%= user %><br>
            <%
        }
        %>

        <%
        if (sessionID == userID) { %>
            <br><br><br>
            <h3><a href="addtags.jsp?id=<%= pictureID %>&sessionid=<%= sessionID %>">Add Tags</a></h3>
            <h3><a href="removetags.jsp?pid=<%= pictureID %>&sessionid=<%= sessionID %>">Remove Tags</a></h3>
            <h3><a href="removepic.jsp?pid=<%= pictureID %>&uid=<%= userID %>">Delete this photo</a></h3>
            <%
        }
        %>
        
        <%
        boolean addComment = false;

        if (userID != sessionID) { %>
            <br><br>
            <h3>Add Comments</h3>
            <form action="picture.jsp?id=<%= pictureID %>&sessionid=<%= sessionID %>" method="post">
                <input type="text" name="comment"/><br>
                <input type="submit" name="action" value="Add Comment">
            </form>
            <%
        }

        if (action != null && action.equals("Add Comment") && comment != null) {
            addComment = commentDao.create(comment, sessionID, pictureID);
        }

        if (!addComment && action != null && action.equals("Add Comment")) { %>
            <br>
            <b>Create comment failed.</b>
            <%
        }
        %>

        <br><br>
        <h3>User Comments</h3>
        <%
        List<String> userComments = commentDao.getAllComments(pictureID);
        for (String userComment: userComments) { %>
            <%= userComment %> <br>
            <%
        }
        %>

        <br><br><br><br><br>
        <%
        if (sessionID != 0) { %>
            <b>Return to <a href="showpictures.jsp?uid=<%= sessionID %>">Albums & Photos</a>.</b>
            <%
        }
        else { %>
            <b>Return to <a href="login.jsp">Login Page</a>.</b><br>
            <b>Return to <a href="browsephotos.jsp">Browse through some photos</a>.</b><br>
            <b>Return to <a href="searchpublicphotos.jsp?uid=0">Search some photos</a>.</b><br>
            <%
        }
        %>

    </body>
</html>

