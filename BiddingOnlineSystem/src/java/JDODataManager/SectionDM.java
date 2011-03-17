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
public class SectionDM {

        public static void addSection(String courseCode, String sectionCode, String day, String start, String end, String instructor, String venue, int size) {

        PersistenceManager pm = PMF.get().getPersistenceManager();

        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            Section tmp = new Section(courseCode, sectionCode, day, start, end, instructor, venue, size);
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
