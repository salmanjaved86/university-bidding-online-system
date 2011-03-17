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
 * @author yewwei.tay.2009
 */
public class SuccessfulBidDataManager {

    private static List<SuccessfulBid> successfulBidList = new ArrayList<SuccessfulBid>();

    public static void refresh() {
        successfulBidList.clear(); // to clear previous objects in ArrayList<Bid>
        readFromDataStore();
    }

    private static void readFromDataStore() {

        PersistenceManager pm = PMF.get().getPersistenceManager();
	String query = "select from " + SuccessfulBid.class.getName();
	successfulBidList = (List<SuccessfulBid>) pm.newQuery(query).execute();

    }

    private static void addToDataStore(String userId, String courseCode, String sectionCode, double amt) {

        PersistenceManager pm = PMF.get().getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            SuccessfulBid tmp = new SuccessfulBid(userId, courseCode, sectionCode, amt);
            pm.makePersistent(tmp);
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }

    }

    private static void deleteFromDataStore(String studentId, String courseCode) {
/*
        PersistenceManager pm = PMF.get().getPersistenceManager();

        try {
            //deletes ALL BIDS by the student, very faulty
            SuccessfulBid e = pm.getObjectById(Bid.class, SuccessfulBid.class.getgetUniqueStudentBid(studentId, courseCode));
            pm.deletePersistent(e);
        } finally {
            pm.close();
        }
*/
    }

    public static List<SuccessfulBid> retrieveAll() {
        return successfulBidList;
    }

    public static List<SuccessfulBid> getStudentSuccessfulBids(String userId) {
        List<SuccessfulBid> userBids = new ArrayList<SuccessfulBid>();
        for (SuccessfulBid bid : successfulBidList) {
            if (bid.getUserId().equals(userId)) {
                userBids.add(bid);
            }
        }
        return userBids;
    }

    public static List<SuccessfulBid> getSuccessfulBidsByCourseAndSection(String courseCode, String sectionCode) {

        List<SuccessfulBid> returnBid = new ArrayList<SuccessfulBid>();
        for (SuccessfulBid bid : successfulBidList) {
            if (bid.getCourseCode().equals(courseCode) && bid.getSectionCode().equals(sectionCode)) {
                returnBid.add(bid);
            }
        }
        return returnBid;
    }

    public static void addSuccessfulBid(String userId, String courseCode, String sectionCode, double amt) {
        addToDataStore(userId, courseCode, sectionCode, amt);
        refresh(); //to repopulate ArrayList<Bid> with data from datastore.

    }

    public static void editSuccessfulBid(String studentId, String courseCode, String sectionCode, double amt) {

        deleteFromDataStore(studentId, courseCode);
        addToDataStore(studentId, courseCode, sectionCode, amt);
        refresh(); //to repopulate ArrayList<Bid> with data from datastore.
    }

    public static void dropSuccessfulBid(Student student, String courseCode, String sectionCode, double bidAmt) {
        deleteFromDataStore(student.getUserId(), courseCode);
        student.setEdollar(student.getEdollar() + bidAmt);
        StudentDataManager.editStudent(student, student.getEdollar());
        refresh();
        Section section = SectionDataManager.getSection(courseCode, sectionCode);
        int vacancy = SectionDataManager.getSectionVacancy(section.getCourseCode(), section.getSectionCode());
        section.setVacancy(vacancy);
        SectionDataManager.getSectionMinBid(section.getCourseCode(), section.getSectionCode());
        refresh();
    }
}
