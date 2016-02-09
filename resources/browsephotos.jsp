<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.UserDao" %>
<%@ page import="photoshare.AlbumDao" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head><title>Browse Photos</title></head>
    <head><link rel="stylesheet" type="text/css" href="news-magazine/layout/styles/layout.css"></head>

    <body>
        <h1>Browse Through Public Photos</h1>

        <%
        PictureDao pictureDao = new PictureDao();
        AlbumDao albumDao = new AlbumDao();
        UserDao userDao = new UserDao();
        %>

        <b>(Select any public album and view its photos)</b><br><br>

        <br><br>
        <form action="browsephotos.jsp" method="post">
        <select name="albumname">
        <option value="publicphotos"> all photos </option>

        <%
        List<String> albums = albumDao.getAllalbums();
        if (albums.size() != 0) {
            for(String album: albums){ %>
                <option value=<%= album %>><%= album %></option>
                <%
            }
        }
        %>
        </select>
        <input type="submit" name="action" value="View Photos"/><br/>
        </form>

        <br>
        <%
        String albumName = request.getParameter("albumname");
        String action = "";
        if (albumName != null) {
            action = request.getParameter("action");
        }

        if (action.equals("View Photos") && albumName.equals("publicphotos")) { %>

        <h3>All Public Photos</h3>

        <table>
        <tr>
        <%
        List<Integer> pictureIDs = pictureDao.allPicturesIds();
        for (Integer pictureID : pictureIDs) { %>
            <td><a href="/photoshare/picture.jsp?id=<%= pictureID %>&sessionid=0">
                <img src="/photoshare/img?t=1&pid=<%= pictureID %>"/></a>
            </td>
            <%
        }
        %>
        </tr>
        </table>
        <%

        }
        else if (action.equals("View Photos")) { %>

        <h3>Photos of album <%= albumName %>:</h3>
        <table>

        <tr>
        <%
        int aid = albumDao.getAlbumID(albumName);
        List<Integer> pictureIDs = pictureDao.allPicturesforAlbum(aid);
        for (Integer pictureID : pictureIDs) { %>
            <td><a href="/photoshare/picture.jsp?id=<%= pictureID %>&sessionid=0">
                <img src="/photoshare/img?t=1&pid=<%= pictureID %>"/></a>
            </td>
            <%
        }
        %>
        </tr>
        </table>
        <%

        }

        %>

        <br><br>
        <b>Return to <a href="login.jsp">Login Page</a>.</b><br>
        <b>Return to <a href="searchpublicphotos.jsp?uid=0">Search some photos</a>.</b><br>
    </body>
</html>
