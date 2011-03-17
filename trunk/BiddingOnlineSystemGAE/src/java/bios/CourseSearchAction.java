package bios;

import com.opensymphony.xwork2.ActionSupport;
import java.sql.*;
import java.util.*;
import model.*;
import org.apache.struts2.interceptor.SessionAware;
import DataManager.*;

/**
 *
 * @author Two-three-one team; mostly contributed by Phylis Png and Varun Arora
 */
public class CourseSearchAction extends ActionSupport {
    private String userId;
    private Map session;
    private String keyword;
    private ArrayList<Course> courseSearchList;

    public void setSession(Map session) {
        this.session = session;
    }

    public Map getSession() {
        return session;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    /**
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword){
        this.keyword = keyword;
    }
   
    public ArrayList<Course> getCourseSearchList() {
        return courseSearchList;
    }

    public String execute() throws Exception {
        searchFromDataStore();       
        return "SUCCESS";
    }
    
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

                courseSearchList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
    }
}
