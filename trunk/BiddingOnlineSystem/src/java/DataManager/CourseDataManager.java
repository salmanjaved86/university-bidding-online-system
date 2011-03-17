package DataManager;
import model.*;
import java.sql.*;
import java.util.*;

public class CourseDataManager {

    private static ArrayList<Course> courseList = new ArrayList<Course>();

    /**
     * This method clears the previous objects in courseList
     */
    public static void refresh(){
        courseList.clear();
        readFromDataStore();
    }

    /**
     * This method connects to the database and retrieves data that from the course table,
     * and adds courses into the courseList
     */
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
    /**
     * Method returns all the courses
     * @return courseList the ArrayList of the courses
     */
    public static ArrayList<Course> retrieveAll() {
        return courseList;
    }

    /**
     * Method gets the course object
     * @param courseCode the course code
     * @return course
     */
    public static Course getCourse(String courseCode) {

        for(Course course: courseList){
            if(course.getCourseCode().equals(courseCode)){
                return course;
            }
        }
        return null;
    }

    /**
     * Method adds a course into the course table
     * @param courseCode
     * @param school
     * @param title
     * @param description
     * @param examDate
     * @param examStart
     * @param examEnd
     */
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
