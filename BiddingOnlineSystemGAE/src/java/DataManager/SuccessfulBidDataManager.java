/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataManager;

import model.*;
import java.sql.*;
import java.util.*;

/**
 *
 * @author yewwei.tay.2009
 */
public class SuccessfulBidDataManager {

    private static ArrayList<Bid> successfulBidList = new ArrayList<Bid>();

    public static void refresh() {
        successfulBidList.clear(); // to clear previous objects in ArrayList<Bid>
        readFromDataStore();
    }

    private static void readFromDataStore() {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("SELECT * FROM SUCCESSFULBID");
            rs = (ResultSet) pstmt.executeQuery();

            while (rs.next()) {
                String userId = rs.getString(1);
                String courseCode = rs.getString(2);
                String sectionCode = rs.getString(3);
                double amount = rs.getDouble(4);

                Bid bid = new Bid(userId, courseCode, sectionCode, amount);

                successfulBidList.add(bid);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
    }

    private static void addToDataStore(String userId, String courseCode, String sectionCode, double amt) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("INSERT INTO SUCCESSFULBID (USERID, COURSECODE, SECTIONCODE, AMOUNT) VALUES(?,?,?,?)");

            pstmt.setString(1, userId);
            pstmt.setString(2, courseCode);
            pstmt.setString(3, sectionCode);
            pstmt.setDouble(4, amt);
            String bider = "SuccessfulBid Datamanger was called";
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }

    }

    private static void deleteFromDataStore(String studentId, String courseCode) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("DELETE FROM SUCCESSFULBID WHERE USERID = '" + studentId + "' AND COURSECODE = '" + courseCode + "'");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }

    }

    public static ArrayList<Bid> retrieveAll() {
        return successfulBidList;
    }

    public static ArrayList<Bid> getStudentSuccessfulBids(String userId) {
        ArrayList<Bid> userBids = new ArrayList<Bid>();
        for (Bid bid : successfulBidList) {
            if (bid.getUserId().equals(userId)) {
                userBids.add(bid);
            }
        }
        return userBids;
    }

    public static ArrayList<Bid> getSuccessfulBidsByCourseAndSection(String courseCode,String sectionCode){

        ArrayList<Bid> returnBid = new ArrayList<Bid>();
        for(Bid bid: successfulBidList){
            if(bid.getCourseCode().equals(courseCode) && bid.getSectionCode().equals(sectionCode)){
                returnBid.add(bid);
            }
        }
        return returnBid;
    }

    public static void addSuccessfulBid(String userId, String courseCode, String sectionCode, double amt) {
        addToDataStore(userId, courseCode, sectionCode, amt);
        refresh(); //to repopulate ArrayList<Bid> with data from datastore.

    }

    public static void editSuccessfulBid(String studentId, String courseCode, String sectionCode, double amt) {

        deleteFromDataStore(studentId, courseCode);
        addToDataStore(studentId, courseCode, sectionCode, amt);
        refresh(); //to repopulate ArrayList<Bid> with data from datastore.
    }

    public static void dropSuccessfulBid(String studentId, String courseCode) {
        deleteFromDataStore(studentId, courseCode);
        refresh();
    }
}
