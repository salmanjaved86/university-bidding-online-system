package DataManager;
import model.*;
import java.sql.*;
import java.util.*;

public class BidDataManager {
    /**
     * initializing private attributes
     */
    private static ArrayList<Bid> bidList = new ArrayList<Bid>();

    /**
     * This method clears the previous objects in the ArrayList<Bid>
     */
    public static void refresh() {
        bidList.clear();
        readFromDataStore();
    }

    /**
     * This method connects to the database and retrieves data that from the bid table,
     * and adds the bids into the bidList
     */
    private static void readFromDataStore() {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("SELECT * FROM BID ORDER BY COURSECODE");
            rs = (ResultSet) pstmt.executeQuery();

            while (rs.next()) {
                String userId = rs.getString(1);
                String courseCode = rs.getString(2);
                String sectionCode = rs.getString(3);
                double amount = rs.getDouble(4);

                Bid bid = new Bid(userId, courseCode, sectionCode, amount, false);

                bidList.add(bid);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
    }

    /**
     * Method adds bid data into database
     */
    private static void addToDataStore(String userId, String courseCode, String sectionCode, double amt) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("INSERT INTO BID (USERID, COURSECODE, SECTIONCODE, AMOUNT) VALUES(?,?,?,?)");

            pstmt.setString(1, userId);
            pstmt.setString(2, courseCode);
            pstmt.setString(3, sectionCode);
            pstmt.setDouble(4, amt);
            String bider = "Bid Datamanger was called";
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }

    }

    /**
     * Method delete bid data into database
     */
    public static void deleteFromDataStore(String studentId, String courseCode) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("DELETE FROM BID WHERE USERID = '" + studentId + "' AND COURSECODE = '" + courseCode + "'");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }

    }

    /**
     * Method retrieves all bid data
     * @return bidList the ArrayList of bids
     */
    public static ArrayList<Bid> retrieveAll() {
        return bidList;
    }

    /**
     * Method retrieves user's bid data
     * @return userBids the ArrayList of bids
     */
    public static ArrayList<Bid> getStudentBids(String userId) {
        ArrayList<Bid> userBids = new ArrayList<Bid>();
        for (Bid bid : bidList) {
            if (bid.getUserId().equals(userId)) {
                userBids.add(bid);
            }
        }
        return userBids;
    }

    /**
     * Method retrieves bids according to course and section
     * @param courseCode the course code
     * @param sectionCode the section code
     */
    public static ArrayList<Bid> getBidsByCourseAndSection(String courseCode, String sectionCode) {
        ArrayList<Bid> returnBid = new ArrayList<Bid>();
        for (Bid bid : bidList) {
            if (bid.getCourseCode().equals(courseCode) && bid.getSectionCode().equals(sectionCode)) {
                returnBid.add(bid);
            }
        }
        return returnBid;
    }

    /**
     * Method to add a new bid during bidding round.
     * @param student object
     * @param courseCode
     * @param sectionCode
     * @param amt
     */
    public static void addBid(Student student, String courseCode, String sectionCode, double amt) {

        student.setEdollar(student.getEdollar() - amt);
        StudentDataManager.editStudent(student, student.getEdollar());
        addToDataStore(student.getUserId(), courseCode, sectionCode, amt);
        refresh();
        Section section = SectionDataManager.getSection(courseCode, sectionCode);
        int vacancy = SectionDataManager.getSectionVacancy(section.getCourseCode(), section.getSectionCode());
        section.setVacancy(vacancy);
        SectionDataManager.getSectionMinBid(section.getCourseCode(), section.getSectionCode());
        refresh(); //to repopulate ArrayList<Bid> with data from datastore.

    }

    /**
     * Method to add a new bid during bootstrap.
     * @param userId
     * @param courseCode
     * @param sectionCode
     * @param amt
     */
    public static void addBid(String userId, String courseCode, String sectionCode, double amt) {
        Student student = StudentDataManager.getStudent(userId);
        student.setEdollar(student.getEdollar() - amt);
        StudentDataManager.editStudent(student, student.getEdollar());
        addToDataStore(userId, courseCode, sectionCode, amt);
        refresh(); //to repopulate ArrayList<Bid> with data from datastore.
    }

    /**
     * Method edits bid by deleting the current bid and adding a new bid with the new amount
     * @param student
     * @param courseCode
     * @param sectionCode
     * @param amt
     */
    public static void editBid(Student student, String courseCode, String sectionCode, double amt) {
        deleteFromDataStore(student.getUserId(), courseCode);
        addBid(student, courseCode, sectionCode, amt);
        refresh(); //to repopulate ArrayList<Bid> with data from datastore.
    }

    /**
     * Method drops bid in the database and updates the vacancy of the section
     * @param student
     * @param courseCode
     * @param sectionCode
     * @param bidAmt
     */
    public static void dropBid(Student student, String courseCode, String sectionCode, double bidAmt) {
        deleteFromDataStore(student.getUserId(), courseCode);
        student.setEdollar(student.getEdollar() + bidAmt);
        StudentDataManager.editStudent(student, student.getEdollar());
        refresh();
        Section section = SectionDataManager.getSection(courseCode, sectionCode);
        int vacancy = SectionDataManager.getSectionVacancy(section.getCourseCode(), section.getSectionCode());
        section.setVacancy(vacancy);
        SectionDataManager.getSectionMinBid(section.getCourseCode(), section.getSectionCode());
        refresh();
    }
}
