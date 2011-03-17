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
public class BidDataManager {

    private static List<Bid> bidList = new ArrayList<Bid>();

    public static void refresh(){
        bidList.clear(); //to clear previous Student objects from ArrayList<Student>
        readFromDataStore();
    }

    private static void readFromDataStore() {

        PersistenceManager pm = PMF.get().getPersistenceManager();
	String query = "select from " + Bid.class.getName();
	bidList = (List<Bid>) pm.newQuery(query).execute();

    }

    public static void addToDataStore(String userId, String courseCode, String sectionCode, double amt) {

        PersistenceManager pm = PMF.get().getPersistenceManager();

        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            Bid tmp = new Bid(userId, courseCode, sectionCode, amt, false);
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
        PersistenceManager pm = PMF.get().getPersistenceManager();

        try {
            //deletes ALL BIDS by the student, very faulty
            Bid e = pm.getObjectById(Bid.class, getUniqueStudentBid(studentId, courseCode));
            pm.deletePersistent(e);
        } finally {
            pm.close();
        }

    }

    public static List<Bid> retrieveAll() {
        return bidList;
    }

    public static List<Bid> getStudentBids(String userId) {
        List<Bid> userBids = new ArrayList<Bid>();
        for (Bid bid : bidList) {
            if (bid.getUserId().equals(userId)) {
                userBids.add(bid);
            }
        }
        return userBids;
    }

    public static Bid getUniqueStudentBid(String userId, String courseCode) {
        Bid uniqueBid = null;
        for (Bid bid : bidList) {
            if (bid.getUserId().equals(userId) && bid.getCourseCode().equals(courseCode)) {
                uniqueBid = bid;
            }
        }
        return uniqueBid;
    }

    public static void addBid(Student student, String courseCode, String sectionCode, double amt) {

            student.setEdollar(student.getEdollar() - amt);
        StudentDataManager.editStudent(student, student.getEdollar());
        addToDataStore(student.getUserId(), courseCode, sectionCode, amt);
        //refresh();
        Section section = SectionDataManager.getSection(courseCode, sectionCode);
        int vacancy = SectionDataManager.getSectionVacancy(section.getCourseCode(), section.getSectionCode());
        section.setVacancy(vacancy);
        SectionDataManager.getSectionMinBid(section.getCourseCode(), section.getSectionCode());
        //refresh(); //to repopulate ArrayList<Bid> with data from datastore.

    }

    public static void editBid(Student student, String courseCode, String sectionCode, double amt) {
        deleteFromDataStore(student.getUserId(), courseCode);
        addBid(student, courseCode, sectionCode, amt);
        //refresh(); //to repopulate ArrayList<Bid> with data from datastore.
    }

    public static void dropBid(Student student, String courseCode, String sectionCode, double bidAmt) {
        deleteFromDataStore(student.getUserId(), courseCode);
        student.setEdollar(student.getEdollar() + bidAmt);
        StudentDataManager.editStudent(student, student.getEdollar());
        refresh();
        Section section = SectionDataManager.getSection(courseCode, sectionCode);
        int vacancy = SectionDataManager.getSectionVacancy(section.getCourseCode(), section.getSectionCode());
        section.setVacancy(vacancy);
        SectionDataManager.getSectionMinBid(section.getCourseCode(), section.getSectionCode());
        //refresh();
    }

  
}
