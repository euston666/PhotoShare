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
import java.util.Hashtable;

public class UserDao {

    private static final String GET_USER_UID =
        "SELECT uid FROM users WHERE email = ?";

    private static final String GET_USER_NAME =
        "SELECT email FROM users WHERE uid = ?";

    private static final String CHECK_EMAIL_STMT =
        "SELECT COUNT(*) FROM users WHERE email = ?";

    private static final String NEW_USER_STMT =
        "INSERT INTO users (gender, password, dob, email, firstname, lastname, edu_level, " +
        "hometown_city, hometown_state, hometown_country, " +
        "current_city, current_state, current_country) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SEARCH_USER_STMT =
        "SELECT firstname, lastname FROM users WHERE email = ?";

    private static final String GRAND_TOTAL_STMT =
        "SELECT results.email, SUM(results.total) as contribution " +
        "FROM ((SELECT u.email, COUNT(*) as total " +
                "FROM users u, pictures p, albums a " +
                "WHERE u.uid = a.ownid AND a.aid = p.myalbumid " +
                "GROUP BY u.email " +
                "ORDER BY total DESC) " +
                "UNION ALL " + "(SELECT u.email, COUNT(*) as total " +
                                "FROM users u, comments c " +
                                "WHERE u.uid = c.owner " +
                                "GROUP BY u.email " +
                                "ORDER BY total DESC)) as results " +
        "WHERE results.email != 'a' " +
        "GROUP BY results.email " +
        "ORDER BY contribution DESC " +
        "LIMIT 10";

    public String getUserName(int uid) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(GET_USER_NAME);
            stmt.setInt(1, uid);
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

    public int getUserID(String email) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(GET_USER_UID);
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            if (!rs.next()) return 0;
            else return rs.getInt(1);

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

    public List<String> topUsers() {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<String> emails = new ArrayList<String>();
        List<String> result = new ArrayList<String>();

        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(GRAND_TOTAL_STMT);
            rs = stmt.executeQuery();
            while (rs.next()) {
                emails.add(rs.getString(1));
            }

            for (String email : emails) {
                stmt = conn.prepareStatement(SEARCH_USER_STMT);
                stmt.setString(1, email);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    result.add(rs.getString(1) + " " + rs.getString(2));
                }
            }
            return result;

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

    public String search(String email) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(SEARCH_USER_STMT);
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            String firstname;
            String lastname;
            if (!rs.next()) return "NULL";
            else {
                firstname = rs.getString("firstname");
                lastname = rs.getString("lastname");
            }
            return firstname + " " + lastname;

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

    public int create(String email, String password, String first, String last,
                          String gender, String dob, String edu, String hometowncity,
                          String hometownstate, String hometowncountry, String currentcity,
                          String currentstate, String currentcountry) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(CHECK_EMAIL_STMT);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            rs.next();
            int result = rs.getInt(1);
            if (result > 0) {
                // This email is already in use
                return 0;
            }

            try { stmt.close(); }
            catch (Exception e) { }

            stmt = conn.prepareStatement(NEW_USER_STMT);
            stmt.setString(1, "M");
            stmt.setString(2, password);
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
            }
            catch(Exception e) {}
            stmt.setDate(3, new java.sql.Date(date.getTime()));
            stmt.setString(4, email);
            stmt.setString(5, first);
            stmt.setString(6, last);
            stmt.setString(7, edu);
            stmt.setString(8, hometowncity);
            stmt.setString(9, hometownstate);
            stmt.setString(10, hometowncountry);
            stmt.setString(11, currentcity);
            stmt.setString(12, currentstate);
            stmt.setString(13, currentcountry);
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
