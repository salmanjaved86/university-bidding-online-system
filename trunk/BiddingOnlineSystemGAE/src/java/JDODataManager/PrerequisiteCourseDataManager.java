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
public class PrerequisiteCourseDataManager {

     private static List<PrerequisiteCourse> prerequisiteCourseList = new ArrayList<PrerequisiteCourse>();

     public static void refresh(){
        prerequisiteCourseList.clear();
        readFromDataStore();
    }

     private static void readFromDataStore() {

        PersistenceManager pm = PMF.get().getPersistenceManager();
	String query = "select from " + PrerequisiteCourse.class.getName();
	prerequisiteCourseList = (List<PrerequisiteCourse>) pm.newQuery(query).execute();

    }

     public static List<PrerequisiteCourse> retrieveAll() {
        return prerequisiteCourseList;
    }

     public static List<PrerequisiteCourse> getPrerequisiteCourse(String courseCode) {
        ArrayList<PrerequisiteCourse> list = new ArrayList<PrerequisiteCourse>();
        for (PrerequisiteCourse prerequisiteCourse: prerequisiteCourseList) {
            if (prerequisiteCourse.getCourseCode().equals(courseCode)) {
                list.add(prerequisiteCourse);
            }
        }
        return list;
    }

        public static void addPrerequisiteCourse(String courseCode, String preReq_courseCode){

        PersistenceManager pm = PMF.get().getPersistenceManager();

        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            PrerequisiteCourse tmp = new PrerequisiteCourse(courseCode, preReq_courseCode);
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
