package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class LikeDao {

    private static final String CHECK_LIKE_STMT =
        "SELECT COUNT(*) FROM likes WHERE userid = ? AND pictureid = ?";

    private static final String NEW_LIKE_STMT =
        "INSERT INTO likes (userid, pictureid) VALUES (?, ?)";

    private static final String WHO_LIKE_STMT =
        "SELECT firstname, lastname FROM likes, users " +
        "WHERE likes.userid = users.uid AND likes.pictureid = ?";

    private static final String NUM_LIKED_STMT =
        "SELECT COUNT(*) FROM likes WHERE pictureid = ?";

    private static final String GET_UID_OWNER =
        "SELECT uid FROM users WHERE email = ?";

    public boolean checkLike(int uid, int pid) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(CHECK_LIKE_STMT);
            stmt.setInt(1, uid);
            stmt.setInt(2, pid);
            rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) != 0) {
                return true;
            }
            return false;

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

    public int numLiked(int pid) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<String> people = new ArrayList();
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(NUM_LIKED_STMT);
            stmt.setInt(1, pid);
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

    public List<String> whoLiked(int pid) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<String> users = new ArrayList<String>();
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(WHO_LIKE_STMT);
            stmt.setInt(1, pid);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String userName = rs.getString(1) + " " + rs.getString(2);
                users.add(userName);
            }
            return users;

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

    public boolean createLike(int uid, int pid) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(NEW_LIKE_STMT);
            stmt.setInt(1, uid);
            stmt.setInt(2, pid);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            return false;

        } finally {
            if (rs != null) {
                try { rs.close(); }
                catch (SQLException e) { ; }
                rs = null;
            }

            if (stmt != null) {
                try { stmt.close(); }
                catch (SQLException e) { ;
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
}
