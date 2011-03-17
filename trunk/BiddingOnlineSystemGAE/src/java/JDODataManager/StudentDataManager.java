/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JDODataManager;

import JDOModel.*;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

/**
 *
 * @author Administrator
 */
public class StudentDataManager {

    private static List<Student> studentList = new ArrayList<Student>();

    public static void refresh(){
        studentList.clear(); //to clear previous Student objects from ArrayList<Student>
        readFromDataStore();
    }

    private static void readFromDataStore() {

        PersistenceManager pm = PMF.get().getPersistenceManager();
	String query = "select from " + Student.class.getName();
	studentList = (List<Student>) pm.newQuery(query).execute();

    }

    private static void updateToDataStore(Student student,double amt) {

        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            Student e = pm.getObjectById(Student.class, student.getKey());
            e.setEdollar(amt);
        } finally {
            pm.close();
        }

    }

    public static List<Student> retrieveAll() {
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
        //refresh(); // to repopulate ArrayList<Student> with data from datastore.
    }

    public static void addStudent(String userid, String password, String name, String school, double edollar) {

        PersistenceManager pm = PMF.get().getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            Student tmp = new Student(userid, name, password, school, edollar);
            pm.makePersistent(tmp);
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }



    }
}
