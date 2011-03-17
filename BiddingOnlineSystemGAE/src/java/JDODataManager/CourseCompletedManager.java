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
public class CourseCompletedManager {

    private static List<CourseCompleted> courseCompletedList = new ArrayList<CourseCompleted>();

    public static void refresh(){
        courseCompletedList.clear(); //to clear previous Student objects from ArrayList<Student>
        readFromDataStore();
    }

    private static void readFromDataStore() {

        PersistenceManager pm = PMF.get().getPersistenceManager();
	String query = "select from " + CourseCompleted.class.getName();
	courseCompletedList = (List<CourseCompleted>) pm.newQuery(query).execute();

    }

    public static List<CourseCompleted> retrieveAll() {
        return courseCompletedList;
    }

    public static List<CourseCompleted> getCourseCompleted(String userId) {

        ArrayList<CourseCompleted> list = new ArrayList<CourseCompleted>();
        for (CourseCompleted courseCompleted : courseCompletedList) {
            if (courseCompleted.getUserId().equals(userId)) {
                list.add(courseCompleted);
            }
        }
        return list;
    }

    public static void addCourseCompleted(String userId, String courseCode){

        PersistenceManager pm = PMF.get().getPersistenceManager();

        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            CourseCompleted tmp = new CourseCompleted(userId, courseCode);
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
