package bios;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.*;
import java.util.*;
import model.*;
import org.apache.struts2.interceptor.SessionAware;

public class CourseSearchAction extends ActionSupport implements SessionAware {
    /**
     * Initializing private attributes
     */
    private String userId;
    private Map session;
    private String keyword;
    private ArrayList<Course> courseSearchList;

    /**
     * Set the session
     * @param session the session to set
     */
    public void setSession(Map session) {
        this.session = session;
    }

    /**
     * Return the current session
     * @return session
     */
    public Map getSession() {
        return session;
    }

    /**
     * Return the userId of the current student
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Set the userId
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    /**
     * Return the keyword entered
     * @return keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * Set the keyword
     * @param keyword the keyword to set
     */
    public void setKeyword(String keyword){
        this.keyword = keyword;
    }

    /**
     * Return the ArrayList of courses
     * @return the courseSearchList
     */
    public ArrayList<Course> getCourseSearchList() {
        return courseSearchList;
    }

    /**
     * Method executes the searchFromDataStore function
     * @return success
     */
    public String execute() throws Exception {
        searchFromDataStore();       
        return "SUCCESS";
    }

    /**
     * This method connects to the database and retrieves data that have the particular keyword
     * from the course table, and adds the courses to courseSearchList
     */
    private void searchFromDataStore() {
        courseSearchList = new ArrayList<Course>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("SELECT * FROM COURSE WHERE TITLE LIKE '%"+getKeyword()+"%'");
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
                //add course to courseSearchList
                courseSearchList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
    }
}
