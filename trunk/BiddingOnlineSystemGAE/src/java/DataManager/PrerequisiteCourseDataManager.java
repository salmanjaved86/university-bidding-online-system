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
public class PrerequisiteCourseDataManager {

    private static ArrayList<PrerequisiteCourse> prerequisiteCourseList = new ArrayList<PrerequisiteCourse>();

    public static void refresh(){
        prerequisiteCourseList.clear();
        readFromDataStore();
    }

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

    public static ArrayList<PrerequisiteCourse> retrieveAll() {
        return prerequisiteCourseList;
    }

    public static  ArrayList<PrerequisiteCourse> getPrerequisiteCourse(String courseCode) {
        ArrayList<PrerequisiteCourse> list = new ArrayList<PrerequisiteCourse>();
        for (PrerequisiteCourse prerequisiteCourse: prerequisiteCourseList) {
            if (prerequisiteCourse.getCourseCode().equals(courseCode)) {
                list.add(prerequisiteCourse);
            }
        }
        return list;
    }

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
