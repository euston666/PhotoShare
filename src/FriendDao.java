package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendDao {
    private static final String GET_UID_STMT =
        "SELECT uid FROM users WHERE email = ?";

    private static final String GET_FRIEND_UID_STMT =
        "SELECT friendb FROM friends WHERE frienda = ?";

    private static final String CHECK_FRIEND_STMT =
        "SELECT COUNT(*) FROM friends WHERE frienda = ? AND friendb = ?";

    private static final String CREATE_FRIENDSHIP_STMT =
        "INSERT INTO friends (frienda, friendb) VALUES (?, ?)";

    private static final String SEARCH_USER_STMT =
        "SELECT firstname, lastname, email FROM users WHERE uid = ?";

    public List<String> getFriends(int uid) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;

        List<String> friends = new ArrayList<String>();
        List<Integer> friendsID = new ArrayList<Integer>();
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(GET_FRIEND_UID_STMT);
            stmt.setInt(1, uid);
            rs = stmt.executeQuery();
            while (rs.next()) {
                friendsID.add(rs.getInt(1));
            }

            for (Integer id : friendsID) {
                stmt = conn.prepareStatement(SEARCH_USER_STMT);
                stmt.setInt(1, id);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    friends.add(rs.getString("firstname") + " " +
                                rs.getString("lastname") + ",  " +
                                rs.getString("email"));
                }
            }
            return friends;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { ; }
                rs = null;
            }
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException e) { ; }
                stmt = null;
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { ; }
                conn = null;
            }
        }
    }

    public int createFriendship(int frienda, int friendb) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DbConnection.getConnection();

            stmt = conn.prepareStatement(CHECK_FRIEND_STMT);
            stmt.setInt(1, frienda);
            stmt.setInt(2, friendb);
            rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) return 0; // friendship already exists

            stmt = conn.prepareStatement(CREATE_FRIENDSHIP_STMT);
            stmt.setInt(1, frienda);
            stmt.setInt(2, friendb);
            stmt.executeUpdate();
            return 1;

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
