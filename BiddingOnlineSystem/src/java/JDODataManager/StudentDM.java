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
public class StudentDM {

    public void addStudent(String userid, String password, String name, String school, String edollar) {

        PersistenceManager pm = PMF.get().getPersistenceManager();

        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            Student tmp = new Student(userid, password, name, school, edollar);
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
