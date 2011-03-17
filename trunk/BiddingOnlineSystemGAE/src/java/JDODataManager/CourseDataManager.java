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
public class CourseDataManager {


    private static List<Course> courseList = new ArrayList<Course>();

    public static void refresh(){
        courseList.clear(); //to clear previous Student objects from ArrayList<Student>
        readFromDataStore();
    }

    private static void readFromDataStore() {

        PersistenceManager pm = PMF.get().getPersistenceManager();
	String query = "select from " + Course.class.getName();
	courseList = (List<Course>) pm.newQuery(query).execute();

    }

    public static List<Course> retrieveAll() {
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

        PersistenceManager pm = PMF.get().getPersistenceManager();

        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            Course tmp = new Course(courseCode, school, title, description, examDate, examStart, examEnd);
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
