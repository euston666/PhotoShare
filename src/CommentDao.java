package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class CommentDao {
    private static final String GET_UID_OWNER =
        "SELECT uid FROM users WHERE email = ?";

    private static final String GET_USER_NAME =
        "SELECT firstname, lastname FROM users WHERE uid = ?";

    private static final String NEW_COMMENT_STMT =
        "INSERT INTO comments (ctext, owner, pid, addtime) VALUES (?, ?, ?, ?)";

    private static final String GET_COMMENT_STMT =
        "SELECT owner, ctext, addtime FROM comments WHERE pid = ? " +
        "ORDER BY addtime ASC";

    private static final String GET_PID_OWNER =
        "SELECT a.ownid FROM pictures p, albums a WHERE p.myalbumid = a.aid AND p.pid = ?";

    public boolean create(String comment, int uid, int pid) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(NEW_COMMENT_STMT);
            stmt.setString(1, comment);
            stmt.setInt(2, uid);
            stmt.setInt(3, pid);
            stmt.setLong(4, new Date().getTime());
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

    public List<String> getAllComments(int pid) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        List<String> comments = new ArrayList<String>();
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(GET_COMMENT_STMT);
            stmt.setInt(1, pid);
            rs1 = stmt.executeQuery();

            while (rs1.next()) {
                int uid = rs1.getInt(1);
                stmt = conn.prepareStatement(GET_USER_NAME);
                stmt.setInt(1, uid);
                rs2 = stmt.executeQuery();
                rs2.next();
                String userName = rs2.getString(1) + " " + rs2.getString(2);
                comments.add(userName + " : " + rs1.getString(2));
            }
            return comments;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (rs1 != null && rs2 != null) {
                try { rs1.close(); rs2.close(); }
                catch (SQLException e) { ; }
                rs1 = null;
                rs2 = null;
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
