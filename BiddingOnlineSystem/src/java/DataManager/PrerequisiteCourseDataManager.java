package DataManager;

import model.*;
import java.sql.*;
import java.util.*;

public class PrerequisiteCourseDataManager {

    private static ArrayList<PrerequisiteCourse> prerequisiteCourseList = new ArrayList<PrerequisiteCourse>();

    /**
     * This method clears the previous objects in prerequisiteCourseList
     */
    public static void refresh(){
        prerequisiteCourseList.clear();
        readFromDataStore();
    }

    /**
     * This method connects to the database and retrieves data that from the prerequisite table,
     * and adds courses into the prerequisiteCourseList
     */
    private static void readFromDataStore(){

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("SELECT * FROM PREREQUISITE");
            rs = (ResultSet) pstmt.executeQuery();

            while (rs.next()) {
                String courseCode = rs.getString(1);
                String prereqCode = rs.getString(2);

                PrerequisiteCourse prerequisiteCourse = new PrerequisiteCourse(courseCode,prereqCode);

                prerequisiteCourseList.add(prerequisiteCourse);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
    }

    /**
     * Method returns all the prerequisite courses
     * @return prerequisiteCourseList the ArrayList of the courses
     */
    public static ArrayList<PrerequisiteCourse> retrieveAll() {
        return prerequisiteCourseList;
    }

    /**
     * Method returns the prerequisite courses of the particular course code
     * @param courseCode
     * @return list the ArrayList of prerequisite courses
     */
    public static ArrayList<PrerequisiteCourse> getPrerequisiteCourse(String courseCode) {
        ArrayList<PrerequisiteCourse> list = new ArrayList<PrerequisiteCourse>();
        for (PrerequisiteCourse prerequisiteCourse: prerequisiteCourseList) {
            if (prerequisiteCourse.getCourseCode().equals(courseCode)) {
                list.add(prerequisiteCourse);
            }
        }
        return list;
    }

    /**
     * Method adds a prerequisite course into the prerequisite table
     * @param courseCode
     * @param school
     * @param title
     * @param description
     * @param examDate
     * @param examStart
     * @param examEnd
     */
    public static void addPrerequisiteCourse(String courseCode, String preReq_courseCode){

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("INSERT INTO PREREQUISITE (COURSECODE, PREREQ_COURSECODE) VALUES(?,?)");
            pstmt.setString(1, courseCode);
            pstmt.setString(2, preReq_courseCode);
            String prerequisiter = "Prerequisite Datamanager was called";
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
                ConnectionManager.close(conn, pstmt, rs);
        }
    }
}
