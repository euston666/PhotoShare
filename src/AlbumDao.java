package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class AlbumDao {
    private static final String GET_USER_ID =
        "SELECT uid FROM users WHERE email = ?";

    private static final String GET_OWNER_ID =
        "SELECT ownid FROM albums WHERE aid = ?";

    private static final String GET_ALBUM_ID =
        "SELECT aid FROM albums WHERE aname = ?";

    private static final String GET_ALBUM_NAME =
        "SELECT aname FROM albums WHERE aid = ?";

    private static final String GET_USER_ALBUMS =
        "SELECT aname FROM albums WHERE ownid = ?";

    private static final String GET_ALL_ALBUMS =
        "SELECT aname FROM albums ORDER BY aname ASC";

    private static final String CHECK_ALBUM_NAME =
        "SELECT COUNT(*) FROM albums WHERE aname = ? AND ownid = ?";

    private static final String CREATE_NEW_ALBUM =
        "INSERT INTO albums (aname, ownid, datecreate) VALUES (?, ?, ?)";

    private static final String DELETE_ALBUM =
        "DELETE FROM albums WHERE aname = ?";

    public int getOwnerID(int aid) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(GET_OWNER_ID);
            stmt.setInt(1, aid);
            rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
            else return -1;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try { rs.close(); }
                catch (SQLException e) { ; }
                rs = null;
            }

            if (stmt != null) {
                try { stmt.close(); }
                catch (SQLException e) { ; }
                stmt = null;
            }

            if (conn != null) {
                try { conn.close(); }
                catch (SQLException e) { ; }
                conn = null;
            }
        }
    }

    public String getAlbumName(int aid) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(GET_ALBUM_NAME);
            stmt.setInt(1, aid);
            rs = stmt.executeQuery();
            if (rs.next()) return rs.getString(1);
            else return "";

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try { rs.close(); }
                catch (SQLException e) { ; }
                rs = null;
            }

            if (stmt != null) {
                try { stmt.close(); }
                catch (SQLException e) { ; }
                stmt = null;
            }

            if (conn != null) {
                try { conn.close(); }
                catch (SQLException e) { ; }
                conn = null;
            }
        }
    }

    public boolean delete(String albumname) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(DELETE_ALBUM);
            stmt.setString(1, albumname);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try { rs.close(); }
                catch (SQLException e) { ; }
                rs = null;
            }

            if (stmt != null) {
                try { stmt.close(); }
                catch (SQLException e) { ; }
                stmt = null;
            }

            if (conn != null) {
                try { conn.close(); }
                catch (SQLException e) { ; }
                conn = null;
            }
        }
    }

    public int getAlbumID(String albumName) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        int aid = 0;
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(GET_ALBUM_ID);
            stmt.setString(1, albumName);
            rs = stmt.executeQuery();
            if (rs.next()) {
                aid = rs.getInt(1);
            }
            return aid;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try { rs.close(); }
                catch (SQLException e) { ; }
                rs = null;
            }

            if (stmt != null) {
                try { stmt.close(); }
                catch (SQLException e) { ; }
                stmt = null;
            }

            if (conn != null) {
                try { conn.close(); }
                catch (SQLException e) { ; }
                conn = null;
            }
        }
    }

    public List<String> getAllalbums() {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<String> albums = new ArrayList<String>();

        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(GET_ALL_ALBUMS);
            rs = stmt.executeQuery();

            while (rs.next()) {
                albums.add(rs.getString(1));
            }
            return albums;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try { rs.close(); }
                catch (SQLException e) { ; }
                rs = null;
            }

            if (stmt != null) {
                try { stmt.close(); }
                catch (SQLException e) { ; }
                stmt = null;
            }

            if (conn != null) {
                try { conn.close(); }
                catch (SQLException e) { ; }
                conn = null;
            }
        }
    }

    public List<String> getAlbums(String username) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<String> albums = new ArrayList<String>();
        int uid = -1;

        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(GET_USER_ID);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                uid = rs.getInt(1);
            }
            try { stmt.close(); }
            catch (SQLException e) { ; }

            stmt = conn.prepareStatement(GET_USER_ALBUMS);
            stmt.setInt(1, uid);
            rs = stmt.executeQuery();

            while (rs.next()) {
                albums.add(rs.getString(1));
            }
            return albums;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try { rs.close(); }
                catch (SQLException e) { ; }
                rs = null;
            }

            if (stmt != null) {
                try { stmt.close(); }
                catch (SQLException e) { ; }
                stmt = null;
            }

            if (conn != null) {
                try { conn.close(); }
                catch (SQLException e) { ; }
                conn = null;
            }
        }
    }

    public int create(int uid, String albumname) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DbConnection.getConnection();

            stmt = conn.prepareStatement(CHECK_ALBUM_NAME);
            stmt.setString(1, albumname);
            stmt.setInt(2, uid);
            rs = stmt.executeQuery();

            if (!rs.next()) return 0;
            else if (rs.getInt(1) > 0) return 1; // album already exists

            stmt = conn.prepareStatement(CREATE_NEW_ALBUM);
            stmt.setString(1, albumname);
            stmt.setInt(2, uid);
            stmt.setDate(3, new java.sql.Date(new Date().getTime()));
            stmt.executeUpdate();
            return 2;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try { rs.close(); }
                catch (SQLException e) { ; }
                rs = null;
            }

            if (stmt != null) {
                try { stmt.close(); }
                catch (SQLException e) { ; }
                stmt = null;
            }

            if (conn != null) {
                try { conn.close(); }
                catch (SQLException e) { ; }
                conn = null;
            }
        }
    }
}
