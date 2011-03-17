package DataManager;
import model.*;
import java.sql.*;
import java.util.*;

public class SectionDataManager {

    private static ArrayList<Section> sectionList = new ArrayList<Section>();

    /**
     * This method clears the previous objects in sectionList
     */
    public static void refresh() {
        sectionList.clear();
        readFromDataStore();
    }

    /**
     * This method connects to the database and retrieves data that from the section table,
     * and adds sections into the sectionList
     */
    private static void readFromDataStore() {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("SELECT * FROM SECTION");
            rs = (ResultSet) pstmt.executeQuery();

            while (rs.next()) {
                String courseCode = rs.getString(1);
                String sectionCode = rs.getString(2);
                String day = rs.getString(3);
                String start = rs.getString(4);
                String end = rs.getString(5);
                String instructor = rs.getString(6);
                String venue = rs.getString(7);
                int size = rs.getInt(8);

                Section section = new Section(courseCode, sectionCode, day, start, end, instructor, venue, size);

                sectionList.add(section);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
    }

    /**
     * Method returns all the sections
     * @return sectionList the ArrayList of the sections
     */
    public static ArrayList<Section> retrieveAll() {
        return sectionList;
    }

    /**
     * Method returns the section object
     * @param courseCode
     * @param sectionCode
     * @return section the section object
     */
    public static Section getSection(String courseCode, String sectionCode) {

        for (Section section : sectionList) {
            if (section.getCourseCode().equals(courseCode) && section.getSectionCode().equals(sectionCode)) {
                return section;
            }
        }
        return null;
    }

    /**
     * Method returns the list of sections of a particular course
     * @param courseCode
     * @return list the ArrayList of sections
     */
    public static ArrayList<Section> getSectionWithCourseCode(String courseCode) {

        ArrayList<Section> list = new ArrayList<Section>();

        for (Section section : sectionList) {
            if (section.getCourseCode().equals(courseCode)) {
                list.add(section);
            }
        }
        return list;
    }

    /**
     * Method adds a section into the section table
     * @param courseCode
     * @param sectionCode
     * @param day
     * @param start
     * @param end
     * @param instructor
     * @param venue
     * @param size
     */
    public static void addSection(String courseCode, String sectionCode, String day, String start, String end, String instructor, String venue, int size) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("INSERT INTO SECTION (COURSECODE, SECTIONCODE, DAY, START, END, INSTRUCTOR, VENUE, SIZE) VALUES(?,?,?,?,?,?,?,?)");
            pstmt.setString(1, courseCode);
            pstmt.setString(2, sectionCode);
            pstmt.setString(3, day);
            pstmt.setString(4, start);
            pstmt.setString(5, end);
            pstmt.setString(6, instructor);
            pstmt.setString(7, venue);
            pstmt.setInt(8, size);
            String sectioner = "Section Datamanager was called";
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
    }

    /**
     * Method retrieves the minimum bid of a particular course's section
     * @param courseCode
     * @param sectionCode
     */
    public static void getSectionMinBid(String courseCode, String sectionCode) {

        // double min = 10.0;

        ArrayList<Bid> allBidsList = BidDataManager.retrieveAll();

        ArrayList<Bid> bidListForSection = new ArrayList<Bid>();

        for (Bid bid : allBidsList) {
            if (bid.getCourseCode().equals(courseCode) && bid.getSectionCode().equals(sectionCode)) {
                bidListForSection.add(bid);
            }
        }

        int vacancy = getSectionVacancy(courseCode, sectionCode);
        Collections.sort(bidListForSection,new Bid.CompareAmountHigh());
        
        for(int i = 0;i<bidListForSection.size();i++){
         System.out.println(i+""+bidListForSection.get(i).getUserId()+""+bidListForSection.get(i).getAmount());
        }

        if (vacancy == 0) {
            double nthBid = bidListForSection.get(bidListForSection.size()-1).getAmount();
            nthBid++;
            SectionDataManager.getSection(courseCode, sectionCode).setMinBid(nthBid);
        }
    }

    /**
     * Method retrieves the the vacancy in a particular section
     * @param courseCode
     * @param sectionCode
     * @return vacancy the amount of seats left in the section
     */
    public static int getSectionVacancy(String courseCode, String sectionCode) {
        int vacancy = SectionDataManager.getSection(courseCode, sectionCode).getSize() - SuccessfulBidDataManager.getSuccessfulBidsByCourseAndSection(courseCode, sectionCode).size();
        if (Round.getRound() == 2) {
            vacancy = vacancy - BidDataManager.getBidsByCourseAndSection(courseCode, sectionCode).size();
            if (vacancy < 0) {
                vacancy = 0;
            }
        }
        return vacancy;
    }
}
