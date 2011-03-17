package DataManager;
import model.*;
import java.sql.*;
import java.util.*;

public class CourseCompletedManager {
    private static ArrayList<CourseCompleted> courseCompletedList = new ArrayList<CourseCompleted>();

    /**
     * This method clears the previous objects in courseCompletedList
     */
    public static void refresh(){
        courseCompletedList.clear();
        readFromDataStore();
    }

    /**
     * This method connects to the database and retrieves data that from the course_completed table,
     * and adds courses into the courseCompletedList
     */
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

    /**
     * Method returns all the courses that are completed
     * @return courseCompletedList the ArrayList of all the courses completed
     */
    public static ArrayList<CourseCompleted> retrieveAll() {
        return courseCompletedList;
    }

    /**
     * Method retrieves the courses that are completed by the student
     * @param userId the student id
     * @return courseCompletedList the list of completed courses
     */
    public static ArrayList<CourseCompleted> getCourseCompleted(String userId) {

        ArrayList<CourseCompleted> list = new ArrayList<CourseCompleted>();
        for (CourseCompleted courseCompleted : courseCompletedList) {
            if (courseCompleted.getUserId().equals(userId)) {
                list.add(courseCompleted);

            }
        }
        return list;
    }

    /**
     * Method adds a userid and a course into the course_completed table
     * @param userId the student id
     * @param courseCode the course code to add
     */
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
