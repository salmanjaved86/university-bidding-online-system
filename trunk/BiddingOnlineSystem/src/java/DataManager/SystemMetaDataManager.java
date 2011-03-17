package DataManager;

import model.*;
import java.sql.*;
import java.util.*;

public class SystemMetaDataManager {

    private static ArrayList<Meta> metaList = new ArrayList<Meta>();

    /**
     * This method clears the previous objects in metaList
     */
    public static void refresh(){
        metaList.clear();
        //readFromDataStore();
    }

    /**
     * This method connects to the database and retrieves data that from the system_meta table,
     * and adds data into the metaList
     */
    public static ArrayList<String> readFromDataStore(String userId){

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<String> returnMsg = new ArrayList<String>();

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("SELECT * FROM SYSTEM_META WHERE PROPERTY ='" + userId + "'");
            rs = (ResultSet) pstmt.executeQuery();

            while (rs.next()) {
                String course = rs.getString(3);
                returnMsg.add("Your bid for "+course+" is unsuccessful.");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
        return returnMsg;
    }

    /**
     * Method retrieves all data from system_meta table
     * @return metaList
     */
    public static ArrayList<Meta> retrieveAll() {
        return metaList;
    }

    /**
     * Method retrieves meta data from system_meta table
     * @param property
     * @return Meta
     */
    public static Meta getMeta(String property) {

        for(Meta meta: metaList){
            if(meta.getProperty().equals(property)){
                return meta;
            }
        }
        return null;
    }

    /**
     * Method edits data in the system_meta
     * @param property
     * @param value
     */
      public static void editMeta(String property, String value) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("UPDATE SYSTEM_META SET VALUE = '" + value + "' WHERE PROPERTY = '" + property + "'");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }

    }
      /**
     * Method adds data to SystemMeta table
     */
    public static void addToDataStore(String userId, String courseCode) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("INSERT INTO SYSTEM_META (ID, PROPERTY, VALUE) VALUES(?,?,?)");

            pstmt.setInt(1, 0);
            pstmt.setString(2, userId);
            pstmt.setString(3, courseCode);
            String bider = "Bid Datamanger was called";
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }

    }

    /**
     * Method to delete meta data from database
     */
    public static void deleteFromDataStore(String studentId) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("DELETE FROM SYSTEM_META WHERE PROPERTY = '" + studentId + "'");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }

    }

}
