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
public class CourseDataManager {

    private static ArrayList<Course> courseList = new ArrayList<Course>();

    public static void refresh(){
        courseList.clear();
        readFromDataStore();
    }

    private static void readFromDataStore(){

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("SELECT * FROM COURSE");
            rs = (ResultSet) pstmt.executeQuery();

            while (rs.next()) {
                String courseCode = rs.getString(1);
                String school = rs.getString(2);
                String title = rs.getString(3);
                String description = rs.getString(4);
                String examDate = rs.getString(5);
                String examStart = rs.getString(6);
                String examEnd = rs.getString(7);
                Course course = new Course(courseCode, school, title, description, examDate, examStart, examEnd);

                courseList.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
    }

    public static ArrayList<Course> retrieveAll() {
        return courseList;
    }

    public static Course getCourse(String courseCode) {

        for(Course course: courseList){
            if(course.getCourseCode().equals(courseCode)){
                return course;
            }
        }
        return null;
    }

        public static void addCourse(String courseCode, String school, String title, String description, String examDate, String examStart, String examEnd){

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("INSERT INTO COURSE (COURSECODE, SCHOOL, TITLE, DESCRIPTION, EXAMDATE, EXAMSTART, EXAMEND) VALUES(?,?,?,?,?,?,?)");
            pstmt.setString(1, courseCode);
            pstmt.setString(2, school);
            pstmt.setString(3, title);
            pstmt.setString(4, description);
            pstmt.setString(5, examDate);
            pstmt.setString(6, examStart);
            pstmt.setString(7, examEnd);
            String courser = "Course Datamanager was called";
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
                ConnectionManager.close(conn, pstmt, rs);
        }
    }

}
