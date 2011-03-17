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
public class SectionDataManager {

    private static ArrayList<Section> sectionList = new ArrayList<Section>();

    public static void refresh() {
        sectionList.clear();
        readFromDataStore();
    }

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

    public static ArrayList<Section> retrieveAll() {
        return sectionList;
    }

    public static Section getSection(String courseCode, String sectionCode) {

        for (Section section : sectionList) {
            if (section.getCourseCode().equals(courseCode) && section.getSectionCode().equals(sectionCode)) {
                return section;
            }
        }
        return null;
    }

    public static ArrayList<Section> getSectionWithCourseCode(String courseCode) {

        ArrayList<Section> list = new ArrayList<Section>();

        for (Section section : sectionList) {
            if (section.getCourseCode().equals(courseCode)) {
                list.add(section);
            }
        }
        return list;
    }

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

    public static double getSectionMinBid(String courseCode, String sectionCode) {
       
        double min = 10.0;

        ArrayList<Bid> allBidsList = BidDataManager.retrieveAll();

        ArrayList<Bid> bidListForSection = new ArrayList<Bid>();

        for (Bid bid : allBidsList) {
            if (bid.getCourseCode().equals(courseCode) && bid.getSectionCode().equals(sectionCode)) {
                bidListForSection.add(bid);
            }
        }

        int vacancy = getSectionVacancy(courseCode, sectionCode);

        Collections.sort(bidListForSection, new Bid.CompareAmount());

        if (bidListForSection.size() >= vacancy) {
            double nthBid = bidListForSection.get(vacancy - 1).getAmount();
            nthBid++;
            min = nthBid;
         }
        return min;
    }

    public static int getSectionVacancy(String courseCode, String sectionCode) {
        int vacancy = SectionDataManager.getSection(courseCode, sectionCode).getSize() - SuccessfulBidDataManager.getSuccessfulBidsByCourseAndSection(courseCode, sectionCode).size();
        return vacancy;
    }
}
