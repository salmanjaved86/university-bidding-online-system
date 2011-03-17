package bios;
import DataManager.*;
import model.*;
import java.util.*;

public class RoundControl {

    private static ArrayList<Bid> allBidsList;

    /**
     * This method refreshes the bid table
     */
    public static void refresh() {
        BidDataManager.refresh();
    }

    /**
     * This method clears the current round
     */
    public static void clearRound() {
        refresh();
        ArrayList<Bid> successfulBidsList = getSuccessfulBids();
        //Add each successful bid into the SQL server
        for (Bid successfulBid : successfulBidsList) {
            SuccessfulBidDataManager.addSuccessfulBid(successfulBid.getUserId(), successfulBid.getCourseCode(), successfulBid.getSectionCode(), successfulBid.getAmount());
        }

        //Makes a clone of allBidsList so as to avoid concurrent modification error
        ArrayList<Bid> cloneList = (ArrayList<Bid>) allBidsList.clone();
        //Clear all current bids after a successful round clearance.
        //By using the cloneList, we can delete the data in the SQL server and at the same time repopulate allBidsList
        for (Bid bid : cloneList) {
            int count = 0;
            for (Bid successfulBid : successfulBidsList) {
                if (bid == successfulBid) {
                    count++;
                }
            }
            //if a bid is unsuccessful, refund bid eDollars to the student
            if (count == 0) {
                Student student = StudentDataManager.getStudent(bid.getUserId());
                double bal = student.getEdollar() + bid.getAmount();
                StudentDataManager.editStudent(student, bal);
            }
            //drop the course bid by the particular student
            BidDataManager.deleteFromDataStore(bid.getUserId(), bid.getCourseCode());
            SystemMetaDataManager.addToDataStore(bid.getUserId(), bid.getCourseCode());
            BidDataManager.refresh();
        }

    }

    /**
     * This method retrieves all successful bids during round clearance
     * @return successfulBidsList the ArrayList that stores the successful bids
     **/
    public static ArrayList<Bid> getSuccessfulBids() {
        allBidsList = BidDataManager.retrieveAll();
        ArrayList<Bid> successfulBidsList = new ArrayList<Bid>();
        Iterator iterator = allBidsList.iterator();
        //use these 2 variables to check for the current coursecode and sectioncode in use
        String courseCode = " ";
        String sectionCode = " ";
        while(iterator.hasNext()) {
            Bid aBid = (Bid) iterator.next();
            //check if the bid has been handled before
            if (!(aBid.getCourseCode().equals(courseCode) && aBid.getSectionCode().equals(sectionCode))) {

                //if not handled before, set the new coursecode and sectioncode
                courseCode = aBid.getCourseCode();
                sectionCode = aBid.getSectionCode();
                //An ArrayList<Bid> object to hold the current bids for a particular section of a course
                ArrayList<Bid> bidListForSection = new ArrayList<Bid>();
                //for loop to populate an array bidListForSection, for bids with the same courseCode & sectionCode
                for (Bid bid : allBidsList) {
                    if (bid.getCourseCode().equals(courseCode) && bid.getSectionCode().equals(sectionCode)) {
                        bidListForSection.add(bid);
                    }
                }
                //section size - no. of successful bids to find the number of vacancy left
                int vacancy = SectionDataManager.getSectionVacancy(bidListForSection.get(0).getCourseCode(), bidListForSection.get(0).getSectionCode());

                if (Round.getRound() == 1) {

                    //arrange the ArrayList<Bid> from highest to lowest bid amount
                    Collections.sort(bidListForSection, new Bid.CompareAmount());
                    //if no. of bids < vacancy, we just add it as a successful bid
                    if (bidListForSection.size() <= vacancy) {
                        if (bidListForSection.size() != 1) {
                            //Round clearance for round 1 according to the clearing logic
                            double clearingPrice = bidListForSection.get(bidListForSection.size() - 1).getAmount();
                            int count = 0;
                            //count no. of bids with the same amount as clearing price
                            for (Bid bid : bidListForSection) {
                                if (bid.getAmount() == clearingPrice) {
                                    count++;
                                }
                            }

                            Iterator iter = bidListForSection.iterator();
                            while (iter.hasNext()) {
                                Bid bid = (Bid) iter.next();
                                //if more than 1 bid with the same amount as clearing price, remove all of them
                                if (count > 1) {
                                    if (bid.getAmount() == clearingPrice) {
                                        iter.remove();
                                    }
                                }
                            }
                        } else {

                        }
                    } else {
                        //Find the clearing price, using the Nth bid value (where N = section.size())
                        double clearingPrice = bidListForSection.get(SectionDataManager.getSection(bidListForSection.get(0).getCourseCode(), bidListForSection.get(0).getSectionCode()).getSize() - 1).getAmount();
                        int count = 0;
                        //count no. of bids with the same amount as clearing price
                        for (Bid bid : bidListForSection) {
                            if (bid.getAmount() == clearingPrice) {
                                count++;
                            }
                        }

                        Iterator iter = bidListForSection.iterator();
                        while (iter.hasNext()) {
                            Bid bid = (Bid) iter.next();
                            //remove all bids lower than clearing price
                            if (bid.getAmount() < clearingPrice) {
                                iter.remove();
                            }
                            //if more than 1 bid with the same amount as clearing price, remove all of them
                            if (count > 1) {
                                if (bid.getAmount() == clearingPrice) {
                                    iter.remove();
                                }
                            }
                        }
                    }

                } else {
                    checkRealTimeBid(bidListForSection.get(0).getCourseCode(), bidListForSection.get(0).getSectionCode());
                }

                //add the successful bids into an ArrayList<Bid> for return
                for (Bid bid : bidListForSection) {
                    successfulBidsList.add(bid);
                }
            }
        }
        return successfulBidsList;
    }

    /**
     * This method performs a number of checks for real time bidding and removes the bid that has the same
     * price as the clearing price
     */
    public static void checkRealTimeBid(String courseCode, String sectionCode) {
        ArrayList<Bid> bidListForSection = BidDataManager.getBidsByCourseAndSection(courseCode, sectionCode);
        Collections.sort(bidListForSection, new Bid.CompareAmount());
        //number of vacancies in round 2.
        int vacancy = SectionDataManager.getSectionVacancy(bidListForSection.get(0).getCourseCode(), bidListForSection.get(0).getSectionCode());
        //size of the section
        int size = SectionDataManager.getSection(courseCode, sectionCode).getSize();
        //number of successful bids for that section from round 1
        int numSuccessfulBids = SuccessfulBidDataManager.getSuccessfulBidsByCourseAndSection(courseCode, sectionCode).size();
        //total number of bids for that section, successful + current
        int totalBids = bidListForSection.size() + numSuccessfulBids;
        //if there are more bids than the vacancy of the section
        if (totalBids > size) {
            //retrieves clearing price
            double clearingPrice = bidListForSection.get(bidListForSection.size() - 1).getAmount();
            for (Bid bid : bidListForSection) {
                if (bid.getAmount() == clearingPrice) {
                    Student student = StudentDataManager.getStudent(bid.getUserId());
                    //removes bid that has the same price as the clearing price
                    BidDataManager.dropBid(student, courseCode, sectionCode, clearingPrice);
                }
            }
        }
    }
}


