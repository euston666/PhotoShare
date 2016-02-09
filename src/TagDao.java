package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TagDao {

    private static final String NEW_TAG_STMT =
        "INSERT INTO tag (pid, word) VALUES (?, ?) ";

    private static final String REMOVE_TAG_STMT =
        "DELETE FROM tag WHERE word = ? AND pid = ?";

    private static final String PICTURE_TAGS_STMT =
        "SELECT word FROM tag WHERE pid = ?";

    private static final String ALL_PICTURES_OF_TAG =
        "SELECT pid FROM tag WHERE word = ?";

    private static final String COUNT_PICTURE_TAGS =
        "SELECT COUNT(*) FROM tag WHERE pid = ?";

    private static final String CHECK_CONTAINS_TAG =
        "SELECT COUNT(*) FROM tag WHERE pid = ? AND word = ?";

    private static final String MY_PICTURES_OF_TAG =
        "SELECT t.pid FROM tag t, pictures p, albums a, users u " +
        "WHERE t.pid = p.pid AND p.myalbumid = a.aid " +
        "AND a.ownid = u.uid AND u.uid = ? AND t.word = ?";

    private static final String MY_TOP_TAGS =
        "SELECT t.word, COUNT(*) as tag_count " +
        "FROM tag t, pictures p, albums a, users u " +
        "WHERE t.pid = p.pid AND p.myalbumid = a.aid AND a.ownid = u.uid AND u.uid = ? " +
        "GROUP BY t.word ORDER BY tag_count DESC LIMIT 5";

    private static final String MOST_POPULAR_TAGS =
        "SELECT word, COUNT(*) as tag_count " +
        "FROM tag GROUP BY word ORDER BY tag_count DESC LIMIT 5";

    private static final String CON_TAG_STMT =
        "SELECT pid FROM tag WHERE word = ? " +
        "INTERSECT SELECT pid FROM tag WHERE word = ?";

    private static final String FIVE_TAG_STMT =
        "SELECT pid FROM tag WHERE word = ? " +
        "INTERSECT " +
        "SELECT pid FROM tag WHERE word = ? " +
        "INTERSECT " +
        "SELECT pid FROM tag WHERE word = ? " +
        "INTERSECT " +
        "SELECT pid FROM tag WHERE word = ? " +
        "INTERSECT " +
        "SELECT pid FROM tag WHERE word = ?";

    private static final String FOUR_TAG_STMT =
        "SELECT pid FROM tag WHERE word = ? " +
        "INTERSECT " +
        "SELECT pid FROM tag WHERE word = ? " +
        "INTERSECT " +
        "SELECT pid FROM tag WHERE word = ? " +
        "INTERSECT " +
        "SELECT pid FROM tag WHERE word = ?";

    private static final String THREE_TAG_STMT =
        "SELECT pid FROM tag WHERE word = ? " +
        "INTERSECT " +
        "SELECT pid FROM tag WHERE word = ? " +
        "INTERSECT " +
        "SELECT pid FROM tag WHERE word = ?";

    private static final String TWO_TAG_STMT =
        "SELECT pid FROM tag WHERE word = ? " +
        "INTERSECT " +
        "SELECT pid FROM tag WHERE word = ?";

    private static final String ONE_TAG_STMT =
        "SELECT pid FROM tag WHERE word = ?";

    private static final String COUNT_TAG_STMT =
        "SELECT COUNT(*) FROM tag WHERE pid = ?";

    private static final String TAGS_OF_PICTURE =
        "SELECT word FROM tag WHERE pid = ?";

    private static final String COUNT_TAG_OCCURRENCE =
        "SELECT word, COUNT(*) as tag_count FROM tag " +
        "GROUP BY word ORDER BY tag_count DESC";

    public List<Integer> getMyPicsByTag(int uid, String word) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<Integer> pictureIDs = new ArrayList<Integer>();
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(MY_PICTURES_OF_TAG);
            stmt.setInt(1, uid);
            stmt.setString(2, word);
            rs = stmt.executeQuery();
            while (rs.next()) {
                pictureIDs.add(rs.getInt(1));
            }
            return pictureIDs;

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

    public List<Integer> getAllPicsByTag(String word) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<Integer> pictureIDs = new ArrayList<Integer>();
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(ALL_PICTURES_OF_TAG);
            stmt.setString(1, word);
            rs = stmt.executeQuery();
            while (rs.next()) {
                pictureIDs.add(rs.getInt(1));
            }
            return pictureIDs;

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

    public List<String> getPictureTags(int pid) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<String> tags = new ArrayList();
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(PICTURE_TAGS_STMT);
            stmt.setInt(1, pid);
            rs = stmt.executeQuery();
            while (rs.next()) {
                tags.add(rs.getString(1));
            }
            return tags;

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

    public List<String> recommendTags(String[] searchTags) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;

        List<Integer> pids = new ArrayList<Integer>();
        List<String> tags = new ArrayList<String>();
        List<String> tmpTags = new ArrayList<String>();
        List<String> recommends = new ArrayList<String>();
        HashMap<String, Integer> occurence = new HashMap<String, Integer>();

        try {
            conn = DbConnection.getConnection();

            // find all photos that contain the searchTags
            for (String tag : searchTags) {
                stmt = conn.prepareStatement(ALL_PICTURES_OF_TAG);
                stmt.setString(1, tag);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int pictureID = rs.getInt(1);
                    if (!pids.contains(pictureID))
                        pids.add(pictureID);
                }
            }

            // find all tags of photos with pids
            for (Integer pid : pids) {
                stmt = conn.prepareStatement(TAGS_OF_PICTURE);
                stmt.setInt(1, pid);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String tag = rs.getString(1);
                    if (!tags.contains(tag))
                        tags.add(tag);
                }
            }

            for (String tag : tags) {
                int count = 0;
                for (int pid : pids) {
                    stmt = conn.prepareStatement(CHECK_CONTAINS_TAG);
                    stmt.setInt(1, pid);
                    stmt.setString(2, tag);
                    rs = stmt.executeQuery();
                    if (rs.next() && rs.getInt(1) > 0) count++;
                }
                occurence.put(tag, count);
            }

            Map<String, Integer> orderedOcur = sortByValue(occurence);
            Set<String> set = orderedOcur.keySet();
            Iterator<String> iter = set.iterator();
            while (iter.hasNext()) {
                tmpTags.add(iter.next());
            }

            int size = tmpTags.size();
            for (int i = size - 1; i >= 0; i--) {
                recommends.add(tmpTags.get(i));
                if (recommends.size() > 5) break;
            }

            return recommends;

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

    public List<String> myTopTags(int uid) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<String> tags = new ArrayList<String>();

        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(MY_TOP_TAGS);
            stmt.setInt(1, uid);
            rs = stmt.executeQuery();
            while (rs.next()) {
                tags.add(rs.getString(1));
            }
            return tags;

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

    public List<Integer> disjunctiveQuery(List<String> tags) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;

        int tagNum = tags.size();
        List<Integer> pictures = new ArrayList<Integer>();
        List<Integer> tmp = new ArrayList<Integer>();
        List<Integer> fivetagPics = new ArrayList<Integer>();
        List<Integer> fourtagPics = new ArrayList<Integer>();
        List<Integer> threetagPics = new ArrayList<Integer>();
        List<Integer> twotagPics = new ArrayList<Integer>();
        List<Integer> onetagPics = new ArrayList<Integer>();

        try {
            conn = DbConnection.getConnection();

            /* Search photos with 5 tags */
            if (tagNum == 5) {
                stmt = conn.prepareStatement(FIVE_TAG_STMT);
                stmt.setString(1, tags.get(0));
                stmt.setString(2, tags.get(1));
                stmt.setString(3, tags.get(2));
                stmt.setString(4, tags.get(3));
                stmt.setString(5, tags.get(4));
                rs = stmt.executeQuery();
                while (rs.next()) {
                    fivetagPics.add(rs.getInt(1));
                }
                fivetagPics = reorderPics(fivetagPics);
            }

            /* Search photos with 4 tags */
            if (tagNum >= 4) {
                List<List<Integer>> pm = getPermutations(tagNum, 4);
                for (List<Integer> p : pm) {
                    stmt = conn.prepareStatement(FOUR_TAG_STMT);
                    stmt.setString(1, tags.get(p.get(0)));
                    stmt.setString(2, tags.get(p.get(1)));
                    stmt.setString(3, tags.get(p.get(2)));
                    stmt.setString(4, tags.get(p.get(3)));
                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        fourtagPics.add(rs.getInt(1));
                    }
                }
                fourtagPics = reorderPics(fourtagPics);
            }

            /* Search photos with 3 tags */
            if (tagNum >= 3) {
                List<List<Integer>> pm = getPermutations(tagNum, 3);
                for (List<Integer> p : pm) {
                    stmt = conn.prepareStatement(THREE_TAG_STMT);
                    stmt.setString(1, tags.get(p.get(0)));
                    stmt.setString(2, tags.get(p.get(1)));
                    stmt.setString(3, tags.get(p.get(2)));
                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        threetagPics.add(rs.getInt(1));
                    }
                }
                threetagPics = reorderPics(threetagPics);
            }

            /* Search photos with 2 tags */
            if (tagNum >= 2) {
                List<List<Integer>> pm = getPermutations(tagNum, 2);
                for (List<Integer> p : pm) {
                    stmt = conn.prepareStatement(TWO_TAG_STMT);
                    stmt.setString(1, tags.get(p.get(0)));
                    stmt.setString(2, tags.get(p.get(1)));
                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        twotagPics.add(rs.getInt(1));
                    }
                }
                twotagPics = reorderPics(twotagPics);
            }

            /* Search photos with 1 tags */
            if (tagNum >= 1) {
                List<List<Integer>> pm = getPermutations(tagNum, 1);
                for (List<Integer> p : pm) {
                    stmt = conn.prepareStatement(ONE_TAG_STMT);
                    stmt.setString(1, tags.get(p.get(0)));
                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        onetagPics.add(rs.getInt(1));
                    }
                }
                onetagPics = reorderPics(onetagPics);
            }

            tmp.addAll(fivetagPics);
            tmp.addAll(fourtagPics);
            tmp.addAll(threetagPics);
            tmp.addAll(twotagPics);
            tmp.addAll(onetagPics);
            // remove duplicates
            for (Integer pid : tmp) {
                if (!pictures.contains(pid)) pictures.add(pid);
            }

            return pictures;

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

    public List<List<Integer>> getPermutations(int totalLen, int genLen) {
        String[] elements = new String[totalLen];
        for (int i = 0; i < totalLen; i++) {
            elements[i] = Integer.toString(i);
        }
        String[] tmp = getAllLists(elements, genLen);
        List<List<Integer>> permutations = tail(tmp);
        return permutations;
    }

    private String[] getAllLists(String[] elements, int lengthOfList) {
        //initialize our returned list with the number of elements calculated above
        String[] allLists = new String[(int)Math.pow(elements.length, lengthOfList)];

        //lists of length 1 are just the original elements
        if(lengthOfList == 1) return elements;
        else {
            //the recursion--get all lists of length 3, length 2, all the way up to 1
            String[] allSublists = getAllLists(elements, lengthOfList - 1);

            //append the sublists to each element
            int arrayIndex = 0;

            for(int i = 0; i < elements.length; i++){
                for(int j = 0; j < allSublists.length; j++){
                    //add the newly appended combination to the list
                    allLists[arrayIndex] = elements[i] + allSublists[j];
                    arrayIndex++;
                }
            }
            return allLists;
        }
    }

    private List<List<Integer>> tail(String[] s) {
        // remove duplicates
        List<String> permutations = new ArrayList<String>();
        for (String p : s) {
            if (!permutations.contains(p)) permutations.add(p);
        }

        List<List<Integer>> intList = new ArrayList<List<Integer>>();
        for (String p : permutations) {
            int len = p.length();
            List<Integer> tmpList = new ArrayList<Integer>();
            for (int i = 0; i < p.length(); i++) {
                int digit =  Character.getNumericValue(p.charAt(i));
                if (!tmpList.contains(digit)) tmpList.add(digit);
            }
            if (tmpList.size() == len) intList.add(tmpList);
        }
        return intList;
    }

    private List<Integer> reorderPics(List<Integer> pictures) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<Integer> pids = new ArrayList<Integer>();
        List<Integer> tmpPics = new ArrayList<Integer>();
        HashMap<Integer, Integer> tagCount = new HashMap<Integer, Integer>();

        try {
            conn = DbConnection.getConnection();
            for (Integer pid: pictures) {
                stmt = conn.prepareStatement(COUNT_PICTURE_TAGS);
                stmt.setInt(1, pid);
                rs = stmt.executeQuery();
                if (rs.next()) tagCount.put(pid, rs.getInt(1));
            }

            Map<Integer, Integer> temp = sortByValue(tagCount);
            Set<Integer> set = temp.keySet();
            Iterator<Integer> iter = set.iterator();
            while (iter.hasNext()) {
                tmpPics.add(iter.next());
            }

            int size = tmpPics.size();
            for (int i = size - 1; i >= 0; i--) {
                pids.add(tmpPics.get(i));
            }
            return pids;

        } catch (Exception e) {
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

    public <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 ) {
                return (o1.getValue()).compareTo( o2.getValue() );
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public List<String> topTags() {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<String> tags = new ArrayList<String>();

        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(MOST_POPULAR_TAGS);
            rs = stmt.executeQuery();
            while (rs.next()) {
                tags.add(rs.getString(1));
            }
            return tags;

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

    public boolean createTag(String tag, int pid) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(NEW_TAG_STMT);
            stmt.setInt(1, pid);
            stmt.setString(2, tag);
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

    public boolean removeTag(String tag, int pid) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(REMOVE_TAG_STMT);
            stmt.setString(1, tag);
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
