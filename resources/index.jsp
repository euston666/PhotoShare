<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.AlbumDao" %>
<%@ page import="photoshare.UserDao" %>
<%@ page import="org.apache.commons.fileupload.FileUploadException" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="imageUploadBean"
             class="photoshare.ImageUploadBean">
<jsp:setProperty name="imageUploadBean" property="*"/>
</jsp:useBean>

<html>
    <head><title>PhotoShare</title>
    <link rel="stylesheet" type="text/css" href="news-magazine/layout/styles/layout.css">
    </head>

    <body>
        <h1>PhotoShare</h1>

        <%
        UserDao userDao = new UserDao();
        AlbumDao albumDao = new AlbumDao();
        PictureDao pictureDao = new PictureDao();

        String userEmail = request.getUserPrincipal().getName();
        int userID = userDao.getUserID(userEmail);
        %>

        <b>Hello <%= userEmail %>, 
        <a href="/photoshare/logout.jsp">Log Out</a></b>.

        <br><br><br>
        <h2>Upload a new photo</h2>
        <b>Albums:</b> <select name="albumname" form="uploadImage">
        <%
        List<String> albums = albumDao.getAlbums(request.getUserPrincipal().getName());
        if (!albums.isEmpty()) {
            for (String album : albums) { %>
                <option value=<%= album %>> <%= album %> </option>
                <%
            }
        }
        %>
        </select>

        <form action="index.jsp" enctype="multipart/form-data" method="post" id="uploadImage">
            <b>Filename:</b> <input type="file" name="filename"/><br>
            <b>Caption:</b> <input type="text" name="caption">
            <input type="submit" name="action" value="Upload"/><br/>
        </form>

        <%
        boolean success = false;
        try {
            Picture picture = imageUploadBean.upload(request);
            if (picture != null && picture.getAlbumname() != null) {
                int aid = albumDao.getAlbumID(picture.getAlbumname());
                success = pictureDao.save(picture, aid);
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        if (success) { %>
            <br><b>Upload photo successfully.</b>
            <%
        }
        %>
        <br><br>

        <h2><a href="showpictures.jsp?uid=<%= userID %>">Albums & Photos</a></h2>

        <h2><a href="/photoshare/newalbum.jsp?uid=<%= userID %>">Add a new album</a></h2>

        <h2><a href="listfriends.jsp?uid=<%= userID %>">List my friends</a></h2>

        <h2><a href="searchfriends.jsp?uid=<%= userID %>">Search for friends</a></h2>

        <h2><a href="recommendtags.jsp?uid=<%= userID %>">Recommend tags</a></h2>

        <h2><a href="toptags.jsp?uid=<%= userID %>">Most popular tags</a></h2>

        <h2><a href="topusers.jsp">Most active users</a></h2>

    </body>
</html>
