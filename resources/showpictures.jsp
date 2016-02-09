<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.UserDao" %>
<%@ page import="photoshare.AlbumDao" %>
<%@ page import="org.apache.commons.fileupload.FileUploadException" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="imageUploadBean"
             class="photoshare.ImageUploadBean">
<jsp:setProperty name="imageUploadBean" property="*"/>
</jsp:useBean>

<html>
    <head><title>Show Public Photos</title></head>
    <head><link rel="stylesheet" type="text/css" href="news-magazine/layout/styles/layout.css"></head>

    <body>

        <%
        UserDao userDao = new UserDao();
        AlbumDao albumDao = new AlbumDao();
        PictureDao pictureDao = new PictureDao();

        int userID = Integer.valueOf(request.getParameter("uid"));
        String userName = userDao.getUserName(userID);
        List<String> albums = albumDao.getAllalbums();
        List<String> myalbums = albumDao.getAlbums(userName);
        %>

        <h1>Albums & Photos</h1>

        <b>(View all public photos or your albums)</b>

        <br><br><br>
        <form action="showpictures.jsp?uid=<%= userID %>" method="post">
        <select name="albumname">
        <option value="publicphotos"> all public photos </option>
        <option value="allmyalbums"> all my albums </option>
        <%
        if (albums.size() != 0) {
            for(String album: albums){ %>
                <option value=<%= album%>> <%= album %> </option>
                <%
            }
        }
        %>
        </select>
        <input type="submit" name="action" value="View Photos"/><br/>
        <input type="submit" name="action" value="Delete Album"/><br/>
        </form>

        <br>
        <%
        String albumName = request.getParameter("albumname");
        String action = "";
        if (albumName != null) {
            action = request.getParameter("action");
        }

        if (action.equals("")) { %>

            <h3>All Your Photos</h3>

            <table>
            <tr>
            <%
            List<Integer> pictureIDs = pictureDao.getUserPics(userID);
            for (Integer pictureID : pictureIDs) { %>
                <td><a href="/photoshare/picture.jsp?id=<%= pictureID %>&sessionid=<%= userID %>">
                    <img src="/photoshare/img?t=1&pid=<%= pictureID %>"/></a>
                </td>
                <%
            }
            %>
            </tr>
            </table>
            <%
        }
        else if (action.equals("View Photos") && albumName.equals("publicphotos")) { %>

            <h3>All Public Photos</h3>

            <table>
            <tr>
            <%
            List<Integer> pictureIDs = pictureDao.allPicturesIds();
            for (Integer pictureID : pictureIDs) { %>
                <td><a href="/photoshare/picture.jsp?id=<%= pictureID %>&sessionid=<%= userID %>">
                    <img src="/photoshare/img?t=1&pid=<%= pictureID %>"/></a>
                </td>
                <%
            }
            %>
            </tr>
            </table>
            <%

        }
        else if (action.equals("View Photos") && albumName.equals("allmyalbums")) { %>

            <h3>All Your Photos</h3>

            <table>
            <tr>
            <%
            List<Integer> pictureIDs = pictureDao.getUserPics(userID);
            for (Integer pictureID : pictureIDs) { %>
                <td><a href="/photoshare/picture.jsp?id=<%= pictureID %>&sessionid=<%= userID %>">
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
                <td><a href="/photoshare/picture.jsp?id=<%= pictureID %>&sessionid=<%= userID %>">
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
        
        <%
        if (action.equals("Delete Album")) {
            if (albumName.equals("publicphotos")) { %>
                <br><br>
                <b>Sorry, you can only delete your own albums.</b>
                <%
            }
            else if (albumName.equals("allmyalbums")) {
                int successCount = 0;

                for (String myalbum: myalbums) {
                    boolean success = albumDao.delete(myalbum);
                    if (success) successCount++;
                }

                if (successCount == myalbums.size()) { %>
                    <br><br>
                    <b>Delete all albums successfully.</b>
                    <%
                }
                else { %>
                    <br><br>
                    <b>Delete all albums failed.</b>
                    <%
                }
            }
            else {
                boolean isOwnAlbum = false;
                int ownerID = albumDao.getOwnerID(albumDao.getAlbumID(albumName));
                if (ownerID == userID) isOwnAlbum = true;

                if (isOwnAlbum) {
                    boolean success = false;
                    success = albumDao.delete(albumName);
                    if (success) { %>
                        <br><br>
                        <b>Delete album <%= albumName %> successfully.</b>
                        <%
                    }
                    else { %>
                        <br><br>
                        <b>Delete album <%= albumName %> failed.</b>
                        <%
                    }
                }
                else { %>
                    <br><br>
                    <b>Sorry, you can only delete your own albums.</b>
                    <%
                }
            }
        }
        %>

        <br>
        <h2><a href="recommendphotos.jsp?uid=<%= userID %>">Photos you may also like</a><br></h2>
        <h2><a href="searchpublicphotos.jsp?uid=<%= userID %>">Search public photos</a></h2>
        <h2><a href="searchmyphotos.jsp?uid=<%= userID %>">Search my photos</a><br></h2>

        <br>
        <b>Return to <a href="index.jsp">Home Page</a>.</b>
    </body>
</html>
