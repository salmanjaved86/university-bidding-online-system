/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JDODataManager;

import JDOModel.*;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

/**
 *
 * @author Administrator
 */
public class PrerequisiteCourseDM {

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
