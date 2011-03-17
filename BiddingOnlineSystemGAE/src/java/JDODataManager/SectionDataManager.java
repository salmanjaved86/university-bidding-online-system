/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JDODataManager;

import JDOModel.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;
/**
 *
 * @author Administrator
 */
public class SectionDataManager {

    private static List<Section> sectionList = new ArrayList<Section>();

    public static void refresh(){
        sectionList.clear(); //to clear previous Student objects from ArrayList<Student>
        readFromDataStore();
    }

    private static void readFromDataStore() {

        PersistenceManager pm = PMF.get().getPersistenceManager();
	String query = "select from " + Section.class.getName();
	sectionList = (List<Section>) pm.newQuery(query).execute();

    }

    public static List<Section> retrieveAll() {
        return sectionList;
    }

    public static Section getSection(String courseCode, String sectionCode) {

        for (Section section : sectionList) {
            if (section.getCourseCode().equals(courseCode) && section.getSectionCode().equals(sectionCode)) {
                return section;
            }
        }
        return null;
    }

    public static List<Section> getSectionWithCourseCode(String courseCode) {

        List<Section> list = new ArrayList<Section>();

        for (Section section : sectionList) {
            if (section.getCourseCode().equals(courseCode)) {
                list.add(section);
            }
        }
        return list;
    }



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

    public static double getSectionMinBid(String courseCode, String sectionCode) {

        double min = 10.0;

        List<Bid> allBidsList = BidDataManager.retrieveAll();

        List<Bid> bidListForSection = new ArrayList<Bid>();

        for (Bid bid : allBidsList) {
            if (bid.getCourseCode().equals(courseCode) && bid.getSectionCode().equals(sectionCode)) {
                bidListForSection.add(bid);
            }
        }

        int vacancy = getSectionVacancy(courseCode, sectionCode);

        Collections.sort(bidListForSection, new Bid.CompareAmount());

        if (bidListForSection.size() >= vacancy) {
            double nthBid = bidListForSection.get(vacancy - 1).getAmount();
            nthBid++;
            min = nthBid;
         }
        return min;
    }

    
    public static int getSectionVacancy(String courseCode, String sectionCode) {
      /*  int vacancy = SectionDataManager.getSection(courseCode, sectionCode).getSize() - SuccessfulBidDataManager.getSuccessfulBidsByCourseAndSection(courseCode, sectionCode).size();*/
        return 0/*vacancy*/;
    }
    

}
