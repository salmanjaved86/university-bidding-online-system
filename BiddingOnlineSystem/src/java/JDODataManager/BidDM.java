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
public class BidDM {

    private static void addBid(String userId, String courseCode, String sectionCode, double amt) {

        PersistenceManager pm = PMF.get().getPersistenceManager();

        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            Bid tmp = new Bid(userId, courseCode, sectionCode, amt);
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
