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
public class StudentDataManager {

    private static ArrayList<Student> studentList = new ArrayList<Student>();

    public static void refresh(){
        studentList.clear(); //to clear previous Student objects from ArrayList<Student>
        readFromDataStore();
    }

    private static void readFromDataStore() {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("SELECT * FROM STUDENT");
            rs = (ResultSet) pstmt.executeQuery();

            while (rs.next()) {
                String studentId = rs.getString(1);
                String name = rs.getString(2);
                String studentPassword = rs.getString(3);
                String school = rs.getString(4);
                double eDollar = rs.getDouble(5);
                Student student = new Student(studentId, studentPassword, name, school, eDollar);
                studentList.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
    }

    private static void updateToDataStore(Student student,double amt) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("UPDATE STUDENT SET EDOLLAR = "+amt+"WHERE USERID = '"+student.getUserId()+"'");

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
                ConnectionManager.close(conn, pstmt, rs);
        }

    }

    public static ArrayList<Student> retrieveAll() {
        return studentList;
    }

    public static Student getStudent(String studentId) {

        for (Student student : studentList) {
            if (student.getUserId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    public static void editStudent(Student student,double amt) {
        updateToDataStore(student,amt);
        refresh(); // to repopulate ArrayList<Student> with data from datastore.
    }

    public static void addStudent(String userid, String name, String password, String school, double eDollar){

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("INSERT INTO STUDENT (USERID, NAME, PASSWORD, SCHOOL, eDollar) VALUES(?,?,?,?,?)");
            pstmt.setString(1, userid);
            pstmt.setString(2, name);
            pstmt.setString(3, password);
            pstmt.setString(4, school);
            pstmt.setDouble(5, eDollar);
            String studenter = "Student Datamanager was called";
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
                ConnectionManager.close(conn, pstmt, rs);
        }
    }
}
