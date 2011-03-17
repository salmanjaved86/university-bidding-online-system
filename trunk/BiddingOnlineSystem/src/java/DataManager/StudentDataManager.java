package DataManager;
import model.*;
import java.sql.*;
import java.util.*;

public class StudentDataManager {

    private static ArrayList<Student> studentList = new ArrayList<Student>();

    /**
     * This method clears the previous objects in studentList
     */
    public static void refresh(){
        studentList.clear(); //to clear previous Student objects from ArrayList<Student>
        readFromDataStore();
    }

    /**
     * This method connects to the database and retrieves data that from the student table,
     * and adds students into the studentList
     */
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
            ConnectionManager.close(conn, pstmt,rs);
        }
    }

    /**
     * Method updates the database on the eDollar balance
     * @param student
     * @param amt
     */
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

    /**
     * Method returns all the students
     * @return studentList the ArrayList of students
     */
    public static ArrayList<Student> retrieveAll() {
        return studentList;
    }

    /**
     * Method returns a student object
     * @param studentId
     * @return student the Student object
     */
    public static Student getStudent(String studentId) {

        for (Student student : studentList) {
            if (student.getUserId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    /**
     * Method edits the student eDollar value
     * @param student
     * @param amt
     */
    public static void editStudent(Student student,double amt) {
        updateToDataStore(student,amt);
        refresh(); // to repopulate ArrayList<Student> with data from datastore.
    }

    /**
     * Method adds student object into the database student table
     * @return sectionList the ArrayList of the sections
     */
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
