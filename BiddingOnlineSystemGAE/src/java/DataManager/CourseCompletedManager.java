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
public class CourseCompletedManager {

    private static ArrayList<CourseCompleted> courseCompletedList = new ArrayList<CourseCompleted>();

    public static void refresh(){
        courseCompletedList.clear();
        readFromDataStore();
    }

    private static void readFromDataStore(){

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("SELECT * FROM COURSE_COMPLETED");
            rs = (ResultSet) pstmt.executeQuery();

            while (rs.next()) {
                String userId = rs.getString(1);
                String courseCode = rs.getString(2);

                CourseCompleted courseCompleted = new CourseCompleted(userId,courseCode);

                courseCompletedList.add(courseCompleted);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
    }

    public static ArrayList<CourseCompleted> retrieveAll() {
        return courseCompletedList;
    }

    public static ArrayList<CourseCompleted> getCourseCompleted(String userId) {

        ArrayList<CourseCompleted> list = new ArrayList<CourseCompleted>();
        for (CourseCompleted courseCompleted : courseCompletedList) {
            if (courseCompleted.getUserId().equals(userId)) {
                list.add(courseCompleted);

            }
        }
        return list;
    }

       public static void addCourseCompleted(String userId, String courseCode){

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("INSERT INTO COURSE_COMPLETED (USERID, COURSECODE) VALUES(?,?)");
            pstmt.setString(1, userId);
            pstmt.setString(2, courseCode);
            String coursecompleteder = "Course Completed Datamanger was called";
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
                ConnectionManager.close(conn, pstmt, rs);
        }
    }

}
